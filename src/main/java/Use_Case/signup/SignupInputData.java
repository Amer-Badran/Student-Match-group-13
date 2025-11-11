package Use_Case.signup;

// This class is populated by the ((((  Controller )))). The controller gets raw Data, it puts it in
// class called signupInputData and sends it to the interactor. 

import java.util.ArrayList;

public class SignupInputData {
    private final String userName;
    private final String passWord;
//    private final String repeatPassWord;
//    private final ArrayList<String> classes;
//    private final ArrayList<String> hobbies;


    public SignupInputData(String userName, String passWord/*,
                           ArrayList<String> classes, ArrayList<String> hobbies, String repeatPassWord*/){
        this.userName = userName;
        this.passWord = passWord;
//        this.hobbies = hobbies;
//        this.classes = classes;
//        this.repeatPassWord = repeatPassWord;

    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

//    public String getRepeatPassWord() {
//        return repeatPassWord;
//    }
//
//    public ArrayList<String> getClasses() {
//        return classes;
//    }
//
//    public ArrayList<String> getHobbies() {
//        return hobbies;
//    }
}
