package gradproject.demo.controller.ui;

import gradproject.demo.entity.User;
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
@RequestMapping("/user/ui")
public class UserAdminUIController {

    private final UserService userService;

    @GetMapping("main")
    public String userMain() {
        return "/user-admin/usermain";
    }

    @GetMapping("signup")
    public String showSignUpForm(User user) {
        return "/user-admin/add-user";
        // return "index";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/user-admin/index";
    }

    @PostMapping("add")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/user-admin/add-user";
        }

        userService.saveUser(user);
        return "redirect:list";
    }

    @GetMapping("edit/{identityNumber}")
    public String showUpdateForm(@PathVariable("identityNumber") long identityNumber, Model model) {
        User user = userService.findUserByIdentityNumber(identityNumber);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" +));
        model.addAttribute("user", user);
        return "/user-admin/update-user";
    }

    @PostMapping("update/{identityNumber}")
    public String updateUser(@PathVariable("identityNumber") long identityNumber, @Valid User user, BindingResult result,
                                Model model) {

        User userNew = userService.findUserByIdentityNumber(identityNumber);
        userNew.setName(user.getName());
        userNew.setSurname(user.getSurname());
        userNew.setPhoneNumber(user.getPhoneNumber());
        userNew.setSalary(user.getSalary());
            userService.saveUser(userNew);

        model.addAttribute("users", userService.getAllUsers());
        return "/user-admin/index";
    }

    @GetMapping("delete/{identityNumber}")
    public String deleteUser(@PathVariable("identityNumber") long identityNumber, Model model) {

      if (Objects.isNull(identityNumber)){
          model.addAttribute("errorMessage", "Login failed");
      }

        User user = userService.findUserByIdentityNumber(identityNumber);
        // .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if (user != null) {
            userService.deleteUserByIdentity(identityNumber);
            model.addAttribute("users", userService.getAllUsers());
        } else {
            model.addAttribute("errorMessage", "Login failed");
        }
        return "/user-admin/index";
    }
}
