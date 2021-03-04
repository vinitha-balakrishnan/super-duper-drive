package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {


    @Select("SELECT * FROM FILES WHERE  userId= #{userId}")
    List<File> getAllFile(Integer userId);


    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) VALUES (#{filename}, #{contenttype}, #{filesize}, #{filedata}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int insertFile(Object add);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    public int deleteFile(int fileid);


    @Select("SELECT * FROM FILES WHERE  userid= #{userId}")
    List<File> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFileById(int fileId);

    @Select("SELECT * FROM FILES WHERE  userid= #{userId} AND filename = #{filename}")
    File isDuplicateFile(int userId, String filename);
}
