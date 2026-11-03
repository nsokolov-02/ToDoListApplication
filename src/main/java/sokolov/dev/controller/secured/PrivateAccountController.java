package sokolov.dev.controller.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sokolov.dev.entity.RecordStatus;
import sokolov.dev.entity.User;
import sokolov.dev.entity.dto.RecordsContainerDto;
import sokolov.dev.service.RecordService;
import sokolov.dev.service.UserService;

@Controller
@RequestMapping("/account")
public class PrivateAccountController {

    private final UserService userService;
    private final RecordService recordService;

    @Autowired
    public PrivateAccountController(RecordService recordService, UserService userService) {
        this.recordService = recordService;
        this.userService = userService;
    }

    @GetMapping
    public String getMainPage(Model model, @RequestParam(name = "filter", required = false) String filterMode){
        User user = userService.getCurrentUser();
        RecordsContainerDto container = recordService.findAllRecords(filterMode);
        model.addAttribute("userName", container.getUserName());
        model.addAttribute("records", container.getRecords());
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords() );
        return "private/account-page";
    }

    @PostMapping(value = "/add-record")
    public String addRecord(@RequestParam String title){
        recordService.saveRecord(title);
        return "redirect:/account";
    }

    @PostMapping(value = "/make-record-done")
    public String makeRecordDone(@RequestParam int id,
                                 @RequestParam(name = "filter", required = false) String filterMode){
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }

    @PostMapping(value = "/delete-record")
    public String deleteRecord(@RequestParam int id,
                               @RequestParam(name = "filter", required = false) String filterMode){
        recordService.deleteRecord(id);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }
}
