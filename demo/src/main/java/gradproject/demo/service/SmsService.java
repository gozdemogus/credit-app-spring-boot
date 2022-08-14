package gradproject.demo.service;

import gradproject.demo.entity.Sms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class SmsService {

    public String sendSms(Sms smsBody) {
        // assuming that this method is sending SMS
        String report;
        if (smsBody.getPhone() != null) {
            report = "SMS Content: " + smsBody.getMessage() + "has been sent to number: " + smsBody.getPhone();
            log.info(report);
        } else {
            report = "Could not sens SMS. Phone number not found.";
            log.warn(report);
        }
        return report;
    }

}
