package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {


    private final FileMapper fileMapper;

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    public List<File> getFile(Integer userId) {
        return fileMapper.getAllFile(userId);
    }


   /* public int addFile(MultipartFile fileupload, int userId) throws IOException {
        File file = new File();
        file.setFiledata(fileupload.getBytes());
        file.setContenttype(fileupload.getContentType());
        file.setFilename(fileupload.getName());
        file.setFilesize(Long.toString(fileupload.getSize()));
       return  fileMapper.insertFile(file,userId);
    }*/


    public int deleteFile(int fileid) {
        return fileMapper.deleteFile(fileid);
    }

    public List<File> getAllFiles(int userId) {
        return fileMapper.getAllFiles(userId);


    }

    public File getfile(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    public boolean isDupicateFile(int userId, String filename) {
        return (fileMapper.isDuplicateFile(userId, filename)!= null);
    }

    public int addFile( MultipartFile fileUpload, int userId) throws IOException {
        File file = new File();
        file.setFiledata(fileUpload.getBytes());
        file.setContenttype(fileUpload.getContentType());
        file.setFilename(fileUpload.getName());
        file.setFilesize(Long.toString(fileUpload.getSize()));
        file.setUserid(userId);
        return  fileMapper.insertFile(file);
    }

}
