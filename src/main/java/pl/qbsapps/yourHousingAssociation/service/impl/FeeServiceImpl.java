package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.exception.FeeAlreadyAddedException;
import pl.qbsapps.yourHousingAssociation.exception.FeeNotFoundException;
import pl.qbsapps.yourHousingAssociation.exception.PermissionDeniedException;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.Fee;
import pl.qbsapps.yourHousingAssociation.model.Role;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.response.FeeStatusResponse;
import pl.qbsapps.yourHousingAssociation.repository.FeeRepository;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.FeeService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public void addFees(String userName, float gas, float coldWater, float hotWater, float sewage, float heating, int repairFund) {
        User user = userRepository.findByEmail(userName).orElseThrow(UserNotFoundException::new);

        if (checkIfFeeIsAlreadyAdded(user.getId())) {
            throw new FeeAlreadyAddedException();
        }

        Fee fee = new Fee();

        fee.setGas(gas);
        fee.setColdWater(coldWater);
        fee.setHotWater(hotWater);
        fee.setSewage(sewage);
        fee.setHeating(heating);
        fee.setRepairFund(repairFund);
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
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
        Fee fee = feeRepository.findByUserId(user.getId()).orElseThrow(FeeNotFoundException::new);

        FeeStatusResponse feeStatusResponse = new FeeStatusResponse();
        feeStatusResponse.setPaid(fee.isPaid());
        feeStatusResponse.setVerified(fee.isVerified());

        return feeStatusResponse;
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
}
