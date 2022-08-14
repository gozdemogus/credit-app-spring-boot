package gradproject.demo.controller.ui;

import gradproject.demo.entity.Credit;
import gradproject.demo.entity.User;
import gradproject.demo.service.CreditCalculationService;
import gradproject.demo.service.CreditService;
import gradproject.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credit/ui")
public class CreditUIController {

    private final CreditService creditService;
    private final CreditCalculationService creditCalculationService;

    //provides the query page for credit query UI
    @GetMapping("/result")
    public String viewLoginPage() {
        return "/credit/result";
    }

    @GetMapping("/query")
    public String hello(@ModelAttribute("formData") Credit credit, BindingResult bindingResult,
                        Model model) {
        Credit credit1 = creditService.getCreditByIdentityNumber(credit.getUserIdentityNumber());
        if (credit.getUserIdentityNumber() != null){
            if (credit1 == null) {
                model.addAttribute("credit", "No credit record related this identity number found.");
            } else
                model.addAttribute("credit", credit1.getCreditResult() + ": " + credit1.getCreditAmount() + " TRY is available.");
        }
        else model.addAttribute("credit", "");

        return "/credit/query";
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String createUser() {
        return "redirect:/credit/ui/login";
    }

    // credit application area
    @GetMapping("/apply")
    public String apply() {
        return "/credit/apply";
    }

    @GetMapping("/calculate")
    public String application(@ModelAttribute("form") User user, BindingResult bindingResult,
                              Model model) {
        // User user = UserMapper.INSTANCE.convertUserDtoToUser(userDTO);
        Credit creditNew;
        //daha once bi kayit yapilmadiysa
        if (creditService.getCreditByIdentityNumber(user.getIdentityNumber()) == null) {
            creditNew = creditCalculationService.CreateUserCredit(user);
            if (creditNew != null) {
                model.addAttribute("credit", "Application received. \n Check your phone or use our inquiry page here.");
            } else model.addAttribute("credit", "An error occured.");

        } else {
            model.addAttribute("credit", "Customer with id: " + user.getIdentityNumber().toString() + " already applied.");
        }

        return "/credit/applied";
    }

    @GetMapping("/main")
    public String main() {
        return "/credit/main";
    }



}
