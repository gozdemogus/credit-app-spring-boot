package gradproject.demo.service;

import gradproject.demo.entity.Credit;
import gradproject.demo.entity.CreditResult;
import gradproject.demo.entity.Sms;
import gradproject.demo.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditCalculationService {

    private final CreditService creditService;
    private final SmsService smsService;
    private final CreditScoreService creditScoreService;

    public Credit CreateUserCredit(User user) {

        float userLimit = 0;
        float userSalary = user.getSalary();
        int creditMultiplier = 4;
        CreditResult creditResult = null;

        //varsayılan servise gidiyor & kredi skoru sonucunu alıyor
        Long predefinedScore = creditScoreService.PredefinedCreditScore(user);

        //Kredi skoru 500’ün altında ise kullanıcı reddedilir.(Kredi sonucu: Red)
        if (predefinedScore < 500) {
            creditResult = CreditResult.REJECTED;
        }
        // Kredi skoru 500 puan ile 1000 puan arasında ise
        else if (predefinedScore >= 500 && predefinedScore < 1000) {
            // ve aylık geliri 5000 TL’nin altında ise
            // kullanıcının kredi başvurusu onaylanır ve kullanıcıya 10.000 TL limit atanır.
            // (Kredi Sonucu: Onay)
            //not: 5000 esitlik durumu belirtilmediği icin buraya eklendi.
            if (userSalary <= 5000) {
                userLimit = 10000;
                creditResult = CreditResult.CONFIRMED;
            }
            // ve aylık geliri 5000 TL’nin üstünde ise
            // kullanıcının kredi başvurusu onaylanır ve kullanıcıya 20.000 TL limit atanır.
            // (Kredi Sonucu: Onay)
            if (userSalary > 5000) {
                userLimit = 20000;
                creditResult = CreditResult.CONFIRMED;
            }
        }
        // Kredi skoru 1000 puana eşit veya üzerinde ise kullanıcıya
        //  AYLIK GELİR BİLGİSİ * KREDİ LİMİT ÇARPANI kadar limit atanır.
        //  (Kredi Sonucu: Onay)
        else if (predefinedScore >= 1000) {
            userLimit = userSalary * creditMultiplier;
            creditResult = CreditResult.CONFIRMED;
        }

        Credit credit = new Credit();
        credit.setApplicationDate(new Date());
        credit.setUserIdentityNumber(user.getIdentityNumber());
        credit.setCreditAmount(userLimit);
        credit.setCreditResult(creditResult.toString());

        creditService.saveCredit(credit);

        if (creditResult != null) {
            log.info("Credit calculated: " + credit.getCreditResult() + " " + String.valueOf(credit.getCreditAmount()));
            Sms smsBody = new Sms();
            smsBody.setMessage("The application belongs to the customer with identity number: "
                    + user.getIdentityNumber() + " has been finalized with status: " + creditResult.toString());
            smsBody.setPhone(user.getPhoneNumber());
            smsService.sendSms(smsBody);
            log.info("SMS has been sent. " + smsBody.getMessage() + " Receiver Phone: " + smsBody.getPhone());
        }
        return credit;
    }

    //In general scenario,
    //the credit score returns a random value if the user's salary is above 5000 TRY and returns 0 if the salary is below than 5000 TRY.
    //But in test scenarios we need to obtain desired credit score in order to apply test scenarios.
    //So I overloaded this method just to get the desired credit score.
    public Credit CreateUserCredit(User user, Long creditScore) {

        float userLimit = 0;
        float userSalary = user.getSalary();
        int creditMultiplier = 4;
        CreditResult creditResult = null;

        //varsayılan servise gidiyor & kredi skoru sonucunu alıyor
        Long predefinedScore = creditScore;

        //Kredi skoru 500’ün altında ise kullanıcı reddedilir.(Kredi sonucu: Red)
        if (predefinedScore < 500) {
            creditResult = CreditResult.REJECTED;
        }
        // Kredi skoru 500 puan ile 1000 puan arasında ise
        else if (predefinedScore >= 500 && predefinedScore < 1000) {
            // ve aylık geliri 5000 TL’nin altında ise
            // kullanıcının kredi başvurusu onaylanır ve kullanıcıya 10.000 TL limit atanır.
            // (Kredi Sonucu: Onay)
            //not: 5000 esitlik durumu belirtilmediği icin buraya eklendi.
            if (userSalary <= 5000) {
                userLimit = 10000;
                creditResult = CreditResult.CONFIRMED;
            }
            // ve aylık geliri 5000 TL’nin üstünde ise
            // kullanıcının kredi başvurusu onaylanır ve kullanıcıya 20.000 TL limit atanır.
            // (Kredi Sonucu: Onay)
            if (userSalary > 5000) {
                userLimit = 20000;
                creditResult = CreditResult.CONFIRMED;
            }
        }
        // Kredi skoru 1000 puana eşit veya üzerinde ise kullanıcıya
        //  AYLIK GELİR BİLGİSİ * KREDİ LİMİT ÇARPANI kadar limit atanır.
        //  (Kredi Sonucu: Onay)
        else if (predefinedScore >= 1000) {
            userLimit = userSalary * creditMultiplier;
            creditResult = CreditResult.CONFIRMED;
        }

        Credit credit = new Credit();
        credit.setApplicationDate(new Date());
        credit.setUserIdentityNumber(user.getIdentityNumber());
        credit.setCreditAmount(userLimit);
        credit.setCreditResult(creditResult.toString());

        creditService.saveCredit(credit);

        if (creditResult != null) {
            log.info("Credit calculated: " + credit.getCreditResult() + " " + String.valueOf(credit.getCreditAmount()));
            Sms smsBody = new Sms();
            smsBody.setMessage("The application belongs to the customer " + user.getName() +  " " + user.getSurname() + " with identity number: "
                    + user.getIdentityNumber() + " has been finalized with status: " + creditResult.toString());
            smsBody.setPhone(user.getPhoneNumber());
            smsService.sendSms(smsBody);
            log.info("SMS has been sent. " + smsBody.getMessage() + " Receiver Phone: " + smsBody.getPhone());
        }
        return credit;
    }
}