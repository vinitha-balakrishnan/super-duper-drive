package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class FileController  implements HandlerExceptionResolver {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


// add file
    @PostMapping("/addFile")
    public String addFile(HttpSession session, File file, Model model) throws IOException {
        int userId = (int) session.getAttribute("userId");
        String errorMsgStr=" ";
        if (!this.fileService.isDupicateFile(userId, file.getFileUpload().getOriginalFilename())) {
            if (file.getFileUpload().getOriginalFilename() == null || file.getFileUpload().getOriginalFilename().equals("")) {
                errorMsgStr = "Please select a file!";
                model.addAttribute("errorResultMessage", errorMsgStr);
                return "result";
            }
            int addRow = 1;
            try {
                addRow = this.fileService.addFile(file.getFileUpload(), userId);
            } catch (Exception e) {

                addRow = -1;
            }
            if (addRow != 1) {

                model.addAttribute("errorResult", true);
                errorMsgStr = "New file failed to add";
                model.addAttribute("errorResultMessage", errorMsgStr);
            } else {

                model.addAttribute("successResult", true);
            }
        } else {

            model.addAttribute("errorResult", true);
            errorMsgStr = "This file already exist";
            model.addAttribute("errorResultMessage", errorMsgStr);
        }

        return  "result";

    }

// Delete file
    @GetMapping("/deleteFile")
    public String deleteNote(@RequestParam("fileId") int fileid, Model model) {
        if (fileid > 0) {
            int deleteFile = fileService.deleteFile(fileid);
            if (deleteFile == 1) {
                model.addAttribute("successResult", true);
            } else {
                model.addAttribute("errorResult", true);
            }
        }

        return "result";
    }

    // view file
    @GetMapping("/viewFile")
    public ResponseEntity<byte[]> viewFile(@RequestParam(name = "fileId") String fileId, Model model) {
        File file = fileService.getfile(Integer.parseInt(fileId));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentLength(file.getFiledata().length)
                .body(file.getFiledata());
    }
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView("result");
        if (e instanceof MaxUploadSizeExceededException) {
            modelAndView.getModel().put("errorResultMessage", "File size exceeds limit!");
        }
        return modelAndView;
    }
}






