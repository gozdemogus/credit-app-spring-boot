package gradproject.demo.service;

import gradproject.demo.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditScoreService {

    // The credit score service which is assumpted that has been developed before
    public Long PredefinedCreditScore2(User user) {

        Integer userSalary = user.getSalary();
        Long predefinedScore = 0L;

        if (userSalary >= 0 && userSalary < 5000) {
            predefinedScore = 0L;
        }

        if (userSalary >= 5000) {
            predefinedScore = Long.valueOf((int)Math.floor(Math.random()*(1500-500+1)+500));
         //   System.out.println(predefinedScore);
        }

        log.info("Received credit score:" + predefinedScore);
        return predefinedScore;

       /* Integer userSalary = user.getSalary();
        long predefinedScore = 0L;

        if (userSalary >= 0 && userSalary < 5000) {
            predefinedScore = 0L;
        }
        if (userSalary >= 5000 && userSalary < 10000) {
            predefinedScore = 500L;
        }
        if (userSalary >= 10000) {
            predefinedScore = 1000L;
        }*/

    }

}
