package gradproject.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private Long identityNumber;
    private String phoneNumber;
    private Integer salary;
    private Date dateOfBirth;

}