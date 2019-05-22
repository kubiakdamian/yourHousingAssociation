package pl.qbsapps.yourHousingAssociation.utils;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.service.FeeService;
import pl.qbsapps.yourHousingAssociation.service.UserService;

import java.util.ArrayList;
import java.util.Calendar;

@Component
@EnableScheduling
public class ScheduledRoutines {

    private final UserService userService;
    private final FeeService feeService;

    public ScheduledRoutines(UserService userService, FeeService feeService) {
        this.userService = userService;
        this.feeService = feeService;
    }

    @Scheduled(cron = "0 10 10 28-31 * ?")
    public void fillUnfilledFees() {
        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
            ArrayList<User> users = userService.getAllUsersWithUnfilledFee();

            for (User user : users) {
                feeService.addFees(user.getEmail(), 1, 2, 2, 4);
            }
        }
    }
}
