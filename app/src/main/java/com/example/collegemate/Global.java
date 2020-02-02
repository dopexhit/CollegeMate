package com.example.collegemate;

public class Global {
    static String webClientId = "709110177436-gv2u5bvti6a6v27ffdffkvmu15jlu42d.apps.googleusercontent.com";

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
    }
    static ModalClasses.UserInfoModal userInfo;



}
