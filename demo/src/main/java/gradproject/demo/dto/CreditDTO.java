package gradproject.demo.dto;

import gradproject.demo.entity.CreditResult;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreditDTO {

    private Date applicationDate;
    private Long userIdentityNumber;
    private float creditAmount;
    private CreditResult creditResult;

}
