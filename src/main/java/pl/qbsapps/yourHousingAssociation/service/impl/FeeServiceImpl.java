package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.exception.FeeAlreadyAddedException;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.Fee;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.repository.FeeRepository;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.FeeService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        if(checkIfFeeIsAlreadyAdded(user.getId())){
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

        feeRepository.save(fee);
    }

    private boolean checkIfFeeIsAlreadyAdded(Long userId){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        for(Fee fee : feeRepository.findAllByUserId(userId)){
            if(fee.getPassingDate().substring(3,5).equals(dateFormat.format(date).substring(3,5))){
                return true;
            }
        }

        return false;
    }
}
