package com.example.collegemate;

import android.net.Uri;

import java.util.ArrayList;

public class File {
    public static ArrayList<File> arr_file=new ArrayList<>();
    public String path="";
    public long id;
    public File(){}

    public File(Long id ,String path){
        this.id=id ;
        this.path=path;
    }
    public void add_file(){
        File.arr_file.add(this);
        Global.documentData.savedFile.add(this);
    }
}
