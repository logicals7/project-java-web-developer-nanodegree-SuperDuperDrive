package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private UserService userService;
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(){
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model){
        String signupError = null;
        if(!userService.isUsernameAvailable(user.getUsername()))
            signupError = "Username already exists. Try another username.";

        if(signupError == null){
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 0)
                signupError = "Some error occurred while signing you up. Please re-try.";
        }

        if(signupError == null)
            model.addAttribute("signupSuccess", true);
        else
            model.addAttribute("signupError", signupError);

        return "signup";
    }
}
