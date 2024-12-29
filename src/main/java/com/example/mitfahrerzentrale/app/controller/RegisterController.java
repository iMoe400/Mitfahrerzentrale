package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Slf4j
@Controller
public class RegisterController {



    @PostMapping("/register/create")
    public String confirmRegisterForm(Model model, @RequestParam(value = "companyName", required = false) String companyName, @RequestParam(value = "usernamePrakti", required = false) String usernamePrakti, @RequestParam(value = "passwordPrakti", required = false) String passwordPrakti, @RequestParam(value = "emailPrakti", required = false) String emailPrakti, @RequestParam(value = "usernameUnternehmi", required = false) String usernameUnternehmi, @RequestParam(value = "passwordUnternehmi", required = false) String passwordUnternehmi, @RequestParam(value = "emailUnternehmi", required = false) String emailUnternehmi, @RequestParam(value = "descriptionUnternehmi", required = false) String description, @RequestParam(value = "lookingForIntern", required = false) String lookingForIntern, @RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam("birthday") LocalDate birthday, @RequestParam(value = "lookingForCompany", required = false) String lookingForCompany, @RequestParam(value = "decisionPrakti") Boolean decisionPrakti, @RequestParam(value = "decisionUnternehmi") Boolean decisionUnternehmi, RedirectAttributes redirectAttributes) {



        return "redirect:/login";
    }

    @PostMapping("/register")
    public String showRegisterForm() {

        return "register";
    }
}
