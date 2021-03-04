package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {


    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;


    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping(value = {"/", "/home"})
    public ModelAndView homePage(Authentication authentication, Model model, HttpSession session) throws Exception {
        Integer userId = null;
        if(session.getAttribute("userId")==null){
            String username = authentication.getName();
            userId = userService.getUser(username).getUserId();
            session.setAttribute("userId", userId);
        }else{
            userId = Integer.valueOf((int)session.getAttribute("userId"));
        }
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("notes", noteService.getAllUserNotes(userId.intValue()));
        modelAndView.addObject("credentials" ,credentialService.getAllUserCredentials(userId.intValue()));
        modelAndView.addObject("files",fileService.getAllFiles(userId.intValue()));
        modelAndView.addObject("encryptionService",encryptionService);


        return modelAndView;
    }
}