package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private EncryptionService encryptionService;
    private CredentialsService credentialsService;
    private UserMapper userMapper;

    public HomeController(FileService fileService, NoteService noteService, CredentialsService credentialsService, UserMapper userMapper, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
        this.credentialsService = credentialsService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        String loggedInUserName = authentication.getPrincipal().toString();
        User user = userMapper.getUser(loggedInUserName);

        model.addAttribute("files", fileService.getUploadedFiles(user.getUserid()));
        model.addAttribute("notes", noteService.getNotes(user.getUserid()));
        model.addAttribute("credentials", credentialsService.getCredentials(user.getUserid()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @GetMapping("/result")
    public String result(){
        return "result";
    }
}