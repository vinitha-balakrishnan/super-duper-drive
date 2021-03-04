package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;


@Mapper
@Repository
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Object addNote);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    public int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    public int deleteNote(int id);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllUserNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid=#{noteId}")
    Note getOneNote(int noteId);


}
