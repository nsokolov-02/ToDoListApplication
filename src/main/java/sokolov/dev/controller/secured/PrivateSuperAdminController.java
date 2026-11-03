package sokolov.dev.controller.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sokolov.dev.entity.User;
import sokolov.dev.entity.UserRole;
import sokolov.dev.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/super-admin")
public class PrivateSuperAdminController {

    private final UserService userService;

    @Autowired
    public PrivateSuperAdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/make-user-admin")
    public String makeUserAdmin(@RequestParam int id){
        Optional<User> userToBeUpgradedOptional = userService.findById(id);
        if (userToBeUpgradedOptional.isEmpty()){
            return "redirect:/admin";
        }

        User userToBeUpgraded = userToBeUpgradedOptional.get();
        if (userToBeUpgraded.isSimpleUser()){
            userService.updateRole(id, UserRole.ADMIN);
            return "redirect:/admin";
        }

        return "redirect:/admin";
    }
}
