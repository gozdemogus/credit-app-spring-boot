package gradproject.demo.controller.ui;

import gradproject.demo.entity.Credit;
import gradproject.demo.entity.User;
import gradproject.demo.repository.CreditRepository;
import gradproject.demo.repository.UserRepository;
import gradproject.demo.service.CreditCalculationService;
import gradproject.demo.service.CreditService;
import gradproject.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credit-admin/ui")
public class CreditAdminUIController {

    private final CreditCalculationService creditCalculationService;

    private final CreditService creditService;

    @GetMapping("main")
    public String userMain() {
        return "/admin-main";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("credits", creditService.findAllCredits());
        return "/credit-admin/credit-admin-index";
    }

    @GetMapping("signup")
    public String showSignUpForm(User user) {
        return "/credit-admin/add-credit-admin";
        // return "index";
    }

    @PostMapping("add")
    public String addUser(@Valid User user, BindingResult result, Model model) {
       /* if (result.hasErrors()) {
            return "/credit-admin/add-credit-admin";
        }

        Credit creditNew = creditCalculationService.CreateUserCredit(user);*/

        Credit creditNew = creditCalculationService.CreateUserCredit(user);

        return "redirect:list";
    }

    @GetMapping("edit/{userIdentityNumber}")
    public String showUpdateForm(@PathVariable("userIdentityNumber") long userIdentityNumber, Model model) {
        Credit credit = creditService.findByUserIdentityNumber(userIdentityNumber);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" +));
        model.addAttribute("credit", credit);
        return "/credit-admin/update-credit-admin";
    }

    @PostMapping("update/{userIdentityNumber}")
    public String updateStudent(@PathVariable("userIdentityNumber") long userIdentityNumber, @Valid Credit credit, BindingResult result,
                                Model model) {
/*        if (result.hasErrors()) {
            credit.setUserIdentityNumber(userIdentityNumber);
            return "update-credit-admin";
        }*/
       Credit credit1 = creditService.getCreditByIdentityNumber(credit.getUserIdentityNumber());
     credit1.setCreditAmount(credit.getCreditAmount());
     credit1.setCreditResult(credit.getCreditResult());
     creditService.saveCredit(credit1);

        model.addAttribute("credits", creditService.findAllCredits());
        return "/credit-admin/credit-admin-index";
    }

    @GetMapping("delete/{identityNumber}")
    public String deleteStudent(@PathVariable("identityNumber") long identityNumber, Model model) {

        if (Objects.isNull(identityNumber)){
            model.addAttribute("errorMessage", "Login failed");
        }

        Credit credit = creditService.findByUserIdentityNumber(identityNumber);
        // .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if (credit != null) {
            creditService.deleteCredit(credit);
            model.addAttribute("credits", creditService.findAllCredits());
        } else {
            model.addAttribute("errorMessage", "Failed.");
        }
       return "/credit-admin/credit-admin-index";
    }

}
