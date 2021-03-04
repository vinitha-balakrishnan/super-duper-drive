package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;

    }
// add and update note
   @PostMapping("/addnotes")
    public String addAndUpdatesNote(Note note,Model model, HttpSession session){
        int userId = (int) session.getAttribute("userId");
       String noteId = String.valueOf(note.getNoteId());
       if(noteId == null||noteId.equals("0")) {
           int addNote= noteService.addNote(note,userId);
           if (addNote == 1) {
               model.addAttribute("successResult", true);
           } else {
               model.addAttribute("errorResult", true);
               model.addAttribute("errorResultMessage", false);
           }
       }
        else {
           int updateNote = noteService.updateNote(note);
           if(updateNote == 1){
               model.addAttribute("successResult", true);
           }else{
               model.addAttribute("errorResult", true);
               model.addAttribute("errorResultMessage",false);
           }
       }
        return "result";
    }

//delete note
    @GetMapping("/deletenote")
    public String deleteNote(@RequestParam(name="noteId") int noteId, Model model){
        int deleteNote = noteService.deleteNote(noteId);
        if(deleteNote == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
        }
        return "result";
    }






}
