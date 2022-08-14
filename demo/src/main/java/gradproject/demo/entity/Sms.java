package gradproject.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms {
    @NotBlank
    private String message;
    @NotBlank
    private String phone;
}
