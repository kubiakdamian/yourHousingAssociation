package pl.qbsapps.yourHousingAssociation.service.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.exception.FeeAlreadyAddedException;
import pl.qbsapps.yourHousingAssociation.exception.FeeAlreadyPaidException;
import pl.qbsapps.yourHousingAssociation.exception.FeeNotFoundException;
import pl.qbsapps.yourHousingAssociation.exception.InvalidCardNumberException;
import pl.qbsapps.yourHousingAssociation.exception.PermissionDeniedException;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.Fee;
import pl.qbsapps.yourHousingAssociation.model.Role;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.PaymentRequest;
import pl.qbsapps.yourHousingAssociation.model.response.FeeStatusResponse;
import pl.qbsapps.yourHousingAssociation.repository.FeeRepository;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.FeeService;
import pl.qbsapps.yourHousingAssociation.utils.Prices;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;
    private final UserRepository userRepository;

    @Autowired
    public FeeServiceImpl(FeeRepository feeRepository, UserRepository userRepository) {
        this.feeRepository = feeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addFees(String userName, double gas, double coldWater, double hotWater, double sewage) {
        User user = userRepository.findByEmail(userName).orElseThrow(UserNotFoundException::new);
        double apartmentSize = user.getAddress().getApartmentSize();

        if (checkIfFeeIsAlreadyAdded(user.getId())) {
            throw new FeeAlreadyAddedException();
        }

        Fee fee = new Fee();

        fee.setHotWaterUsage(hotWater);
        fee.setColdWaterUsage(coldWater);
        fee.setGasUsage(gas);
        fee.setSewageUsage(sewage);

        fee.setGas(Prices.GAS.getPrice().multiply(BigDecimal.valueOf(gas)));
        fee.setColdWater(Prices.COLD_WATER.getPrice().multiply(BigDecimal.valueOf(coldWater)));
        fee.setHotWater(Prices.HOT_WATER.getPrice().multiply(BigDecimal.valueOf(hotWater)));
        fee.setSewage(Prices.SEWAGE.getPrice().multiply(BigDecimal.valueOf(sewage)));
        fee.setHeating(Prices.HEATING.getPrice().multiply(BigDecimal.valueOf(apartmentSize)));
        fee.setRepairFund(Prices.REPAIR_FUND.getPrice().multiply(BigDecimal.valueOf(apartmentSize)));
        fee.setAmountToPay(fee.getColdWater().add(fee.getHotWater()).add(fee.getSewage()).add(fee.getGas()).add(fee.getHeating()).add(fee.getRepairFund()));
        fee.setUser(user);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        fee.setPassingDate(dateFormat.format(date));

        fee.setPaidMonth(getActualMonth() + 1);

        feeRepository.save(fee);
    }

    @Override
    public boolean isFeeFulfilled(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        return checkIfFeeIsAlreadyAdded(user.getId());
    }

    @Override
    public FeeStatusResponse getFeeStatus(String username) {
        Fee fee = findNewestFee(username);

        FeeStatusResponse feeStatusResponse = new FeeStatusResponse();
        feeStatusResponse.setPaid(fee.isPaid());
        feeStatusResponse.setVerified(fee.isVerified());
        feeStatusResponse.setAmount(fee.getAmountToPay());

        return feeStatusResponse;
    }

    @Override
    @Transactional
    public void payFee(String username, PaymentRequest paymentRequest) {

        if(!checkCardNumberCorrectness(paymentRequest.getCardNumber())){
            throw new InvalidCardNumberException();
        }

        Fee fee = findNewestFee(username);
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        if (!user.getRole().equals(Role.TENANT)) {
            throw new PermissionDeniedException();
        }

        if (fee.isPaid()) {
            throw new FeeAlreadyPaidException();
        }

        fee.setPaid(true);
    }

    @Override
    @Transactional
    public ArrayList<Fee> getNotAcceptedManagedFees(String username) {
        List<Fee> fees = feeRepository.findAll();
        User manager = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        if (!manager.getRole().equals(Role.MANAGER)) {
            throw new PermissionDeniedException();
        }

        fees = fees.stream().filter(f -> f.getPaidMonth() == getActualMonth() + 1 &&
                manager.getBlocksNumbers().contains(f.getUser().getAddress().getBlockNumber()) && !f.isVerified()).collect(Collectors.toList());

        return (ArrayList<Fee>) fees;
    }

    @Override
    @Transactional
    public void acceptManagedFee(String username, Long feeId) {
        User manager = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        if (!manager.getRole().equals(Role.MANAGER)) {
            throw new PermissionDeniedException();
        }

        Fee fee = feeRepository.findById(feeId).orElseThrow(FeeNotFoundException::new);

        fee.setVerified(true);
    }

    @Override
    @Transactional
    public void declineManagedFee(String username, Long feeId) {
        User manager = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        if (!manager.getRole().equals(Role.MANAGER)) {
            throw new PermissionDeniedException();
        }

        Fee fee = feeRepository.findById(feeId).orElseThrow(FeeNotFoundException::new);

        feeRepository.delete(fee);
    }

    @Override
    public ByteArrayInputStream generatePDF(String username) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfPTable table = new PdfPTable(5);
        addTableHeader(table);
        addRows(table, username);

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Nazwa stawki", "J.M", "Ilosc", "Cena (pln)", "Razem (pln)")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, String username) {
        Fee fee = findNewestFee(username);
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
        double userApartmentSize = user.getAddress().getApartmentSize();

        table.addCell("Ciepla woda");
        table.addCell("m3");
        table.addCell(String.valueOf(fee.getHotWaterUsage()));
        table.addCell(String.format("%.2f", Prices.HOT_WATER.getPrice()));
        table.addCell(String.format("%.2f", Prices.HOT_WATER.getPrice().multiply(BigDecimal.valueOf(fee.getHotWaterUsage()))));

        table.addCell("Zimna woda");
        table.addCell("m3");
        table.addCell(String.valueOf(fee.getColdWaterUsage()));
        table.addCell(String.format("%.2f", Prices.COLD_WATER.getPrice()));
        table.addCell(String.format("%.2f", Prices.COLD_WATER.getPrice().multiply(BigDecimal.valueOf(fee.getColdWaterUsage()))));

        table.addCell("Gaz");
        table.addCell("m3");
        table.addCell(String.valueOf(fee.getGasUsage()));
        table.addCell(String.format("%.2f", Prices.GAS.getPrice()));
        table.addCell(String.format("%.2f", Prices.GAS.getPrice().multiply(BigDecimal.valueOf(fee.getGasUsage()))));

        table.addCell("Scieki");
        table.addCell("m3");
        table.addCell(String.valueOf(fee.getSewageUsage()));
        table.addCell(String.format("%.2f", Prices.SEWAGE.getPrice()));
        table.addCell(String.format("%.2f", Prices.SEWAGE.getPrice().multiply(BigDecimal.valueOf(fee.getSewageUsage()))));

        table.addCell("Ogrzewanie");
        table.addCell("m2");
        table.addCell(String.valueOf(userApartmentSize));
        table.addCell(String.format("%.2f", Prices.HEATING.getPrice()));
        table.addCell(String.format("%.2f", Prices.HEATING.getPrice().multiply(BigDecimal.valueOf(userApartmentSize))));

        table.addCell("Fundusz rem.");
        table.addCell("m2");
        table.addCell(String.valueOf(userApartmentSize));
        table.addCell(String.format("%.2f", Prices.REPAIR_FUND.getPrice()));
        table.addCell(String.format("%.2f", Prices.REPAIR_FUND.getPrice().multiply(BigDecimal.valueOf(userApartmentSize))));

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell(String.format("%.2f", fee.getAmountToPay()));
    }

    private boolean checkIfFeeIsAlreadyAdded(Long userId) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        for (Fee fee : feeRepository.findAllByUserId(userId)) {
            if (fee.getPassingDate().substring(3, 5).equals(dateFormat.format(date).substring(3, 5))) {
                return true;
            }
        }

        return false;
    }

    private int getActualMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return Calendar.MONTH + 1;
    }

    private Fee findNewestFee(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
        ArrayList<Fee> allUserFees = (ArrayList<Fee>) feeRepository.findAllByUserId(user.getId());

        Collections.reverse(allUserFees);

        return allUserFees.get(0);
    }

    private static boolean checkCardNumberCorrectness(String ccNumber)
    {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
