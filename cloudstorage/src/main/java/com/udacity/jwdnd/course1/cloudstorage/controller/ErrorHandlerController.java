package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ErrorHandlerController  implements ErrorController {
    private static final String PATH = "/error";
    public final static String TAG_ = "ErrorHandlerController";

    @RequestMapping("/error")
    public String errorHandle(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String pageError = "error";
        String errorPage = "error";
        String errorMsg = "";
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                pageError = "Page Not Found";
                errorMsg = "404 Error --- Page Can't be Found";
                errorPage = "error";
            }

            else{
                pageError = "Error";
                errorMsg = String.valueOf(statusCode) + " Error";
                errorPage = "error";

            }
        }
        model.addAttribute("pageError", pageError);
        model.addAttribute("errorMsg", errorMsg);

        return errorPage;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
