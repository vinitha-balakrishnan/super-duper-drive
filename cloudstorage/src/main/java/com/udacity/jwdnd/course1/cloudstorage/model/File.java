package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class File {

    private int fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private int userid;
    private byte[] filedata;


    private MultipartFile fileUpload;

    public File(int fileId,String filename,String contenttype,String filesize,int userid,byte[] filedata){
        this.fileId = fileId;
        this.filename= filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public File() {

    }
    public File(MultipartFile fileUpload, int userId) throws IOException {
        try{
            this.contenttype= fileUpload.getContentType();
            this.filedata = fileUpload.getBytes();
            this.filename = fileUpload.getOriginalFilename();
            this.userid = userId;
            this.filesize = Long.toString(fileUpload.getSize());
        }catch(IOException e){
            throw e;
        }
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }








}
