package com.example.collegemate;

import android.net.Uri;

import java.util.ArrayList;

public class File {
    public static ArrayList<File> arr_file=new ArrayList<>();
    String path="";
    Uri data;
    long id;
    public File(){}
    public File(Uri data){
        path=data.getPath();
        this.data=data;
        id=System.currentTimeMillis();
    }
    public File(String id,String path){
        this.id=Long.valueOf(id);
        this.path=path;
    }
    public void add_file(){
        File.arr_file.add(this);
    }
}
