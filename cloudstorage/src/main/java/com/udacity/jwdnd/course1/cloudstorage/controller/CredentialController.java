package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CredentialController {

    private  final CredentialService credentialService;

@Autowired
    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }


// add and update credential
    @PostMapping("/credential")
    public String addCredentialAndUpdate(Credential credential, Model model, HttpSession session){
        int userId = (int) session.getAttribute("userId");
       String credentialId = String.valueOf(credential.getCredentialId());
       if(credentialId == null||credentialId.equals("0")) {
               int addCredential = credentialService.addCredential(credential,userId);
               if (addCredential == 1) {
                   model.addAttribute("successResult", true);
               } else {
                   model.addAttribute("errorResult", true);
                   model.addAttribute("errorResultMessage", false);

               }
           }

        else {
           int updateCredential = credentialService.updateCredential(credential);
            if (updateCredential == 1) {
                model.addAttribute("successResult", true);
            } else {
                model.addAttribute("errorResult", true);
                model.addAttribute("errorResultMessage", false);

            }
        }
        return "result";
    }
// Delete credential
    @GetMapping("/credentialdelete")
    public String deleteCredential(@RequestParam(name="credentialId") int credentialId, Model model){
        int deletecredential = credentialService.deletecredential(credentialId);
        if(deletecredential == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
        }
        return "result";
    }

}
