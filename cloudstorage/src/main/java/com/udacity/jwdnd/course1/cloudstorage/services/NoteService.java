package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

private  final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note addNote,int userId) {
        addNote.setUserId(userId);
        return noteMapper.insertNote(addNote);
    }

  public int updateNote(Note note) {
       return  noteMapper.updateNote(note);
    }


    public int deleteNote(int noteId){
        return this.noteMapper.deleteNote(noteId);
    }

    public List<Note> getAllUserNotes(int userId){
        return this.noteMapper.getAllUserNotes(userId);

    }



}
