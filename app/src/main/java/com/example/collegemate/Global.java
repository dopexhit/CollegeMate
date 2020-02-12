package com.example.collegemate;

import android.util.Pair;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Global {
    static String webClientId = "709110177436-gv2u5bvti6a6v27ffdffkvmu15jlu42d.apps.googleusercontent.com";
    static UserData documentData;



    static class ModalClasses{
        static class UserInfoModal{
            public String phone;
            public String uid;
            public  String password;
            public String name;

            public UserInfoModal(String phone, String uid, String password, String name) {
                this.phone = phone;
                this.uid = uid;
                this.password = password;
                this.name = name;
            }

            public UserInfoModal(){
                //No Argument Constructor
            }

        }

        static class TimeTableModal{
            public TimeTableModal(String subname, String venue, int day, int time) {
                this.subname =subname;
                this.venue = venue;
                this.day = day;
            }

            public TimeTableModal(){

            }

            public String subname;
            public String venue;
            public int day;
            public int time;
        }

        static class AttendanceModal{
            public Integer attended;
            public Integer missed;

            public AttendanceModal(){

            }

            public AttendanceModal(Integer attended, Integer missed) {
                this.attended = attended;
                this.missed = missed;
            }
        }
    }

    //Class to store all the data from UserDocument from Firebase
    static class UserData{
        public Map<String,List<ModalClasses.TimeTableModal>> timetable;
        public ModalClasses.UserInfoModal userinfo;
        public List<String> subjects;
        public Map<String,ModalClasses.AttendanceModal> attendance;

        public UserData(){

        }

        public UserData(Map<String, List<ModalClasses.TimeTableModal>> timetable, ModalClasses.UserInfoModal userinfo,List<String> subjects) {
            this.timetable = timetable;
            this.userinfo = userinfo;
            this.subjects = subjects;
        }

    }
    static DocumentReference userRef;
    static int NUMBER_OF_CLASSES = 56;



}
