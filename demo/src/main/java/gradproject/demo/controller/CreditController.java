package gradproject.demo.controller;

import gradproject.demo.dto.CreditDTO;
import gradproject.demo.dto.CreditResponseDTO;
import gradproject.demo.dto.UserDTO;
import gradproject.demo.entity.Credit;
import gradproject.demo.entity.User;
import gradproject.demo.exception.CreditAlreadyExistsException;
import gradproject.demo.exception.RecordNotFoundException;
import gradproject.demo.mapper.CreditMapper;
import gradproject.demo.mapper.UserMapper;
import gradproject.demo.service.CreditCalculationService;
import gradproject.demo.service.CreditService;
import gradproject.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
@Tag(name="Credit", description="Credit application related operations")
public class CreditController {

    private final CreditService creditService;
    private final CreditCalculationService creditCalculationService;
    private final UserService userService;

    @GetMapping("/all")
    @Tag(name="All", description="Returns all type of credits.")
    public List<CreditDTO> findALlCredits() {
        List<Credit> creditList = creditService.findAllCredits();
        List<CreditDTO> creditDtoList = CreditMapper.INSTANCE.toCreditDTOList(creditList);

        return creditDtoList;
    }

    @GetMapping("/{identityNumber}")
    public ResponseEntity findAllByUserIdentityNumber(@PathVariable Long identityNumber) {

        Credit credit = creditService.findByUserIdentityNumber(identityNumber);
        if (credit == null) {
            User user = userService.findUserByIdentityNumber(identityNumber);
            if (user != null) {
                throw new RecordNotFoundException("No credit assigned to this user");
            } else {
                throw new RecordNotFoundException("Wrong user identity number.");
            }
        } else {
            CreditDTO creditDTO = CreditMapper.INSTANCE.toCreditDTO(credit);

            return ResponseEntity.status(HttpStatus.OK).body(creditDTO);
        }

    }

    //user should apply with salary info
    //calculated credit will return
    @PostMapping("/apply")
    public ResponseEntity setUserCredit(@RequestBody UserDTO userDto) {

        // Credit credit;
        Credit creditNew;
        User user = UserMapper.INSTANCE.toUser(userDto);
        if (user != null) {
            //girilen identity ile sorgulayarak eğer sonuc bulunamadıysa kredi basvurusunda bulunuyor.
            if (creditService.getCreditByIdentityNumber(user.getIdentityNumber()) == null) {
                //     credit = creditService.findByUserIdentityNumber(userDto.getIdentityNumber());
                //   if (credit == null) {
                creditNew = creditCalculationService.CreateUserCredit(user);
                //    }
            } else throw new CreditAlreadyExistsException("You already applied.");
        } else {
            throw new RecordNotFoundException("Check your information.");
        }
        // endpoint’ten onay durum bilgisi (red veya onay), limit bilgisi dönülür.
        CreditResponseDTO creditResponseDTO = new CreditResponseDTO();
        creditResponseDTO.setCreditAmount(creditNew.getCreditAmount());
        creditResponseDTO.setCreditResult(creditNew.getCreditResult());

        return ResponseEntity.status(HttpStatus.OK).body(creditResponseDTO);
    }


    //assign user to credit for ManyToOne relationship
    @PostMapping("/{creditId}/{userId}")
    public ResponseEntity enrollCreditToUser(@PathVariable Long creditId, @PathVariable Long userId) {
        Credit credit = creditService.findById(userId);
        User user = userService.findUserById(userId);
        credit.setUser(user);
        creditService.saveCredit(credit);

        return ResponseEntity.status(HttpStatus.OK).body(credit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        Credit credit = creditService.findById(id);
        if (credit != null) {
            creditService.deleteCredit(credit);
            return ResponseEntity.status(HttpStatus.OK).body("Credit application with id " + id + " deleted.");
        } else {
            throw new RecordNotFoundException("Credit application with id " + id + " does not exists.");
        }
    }

}