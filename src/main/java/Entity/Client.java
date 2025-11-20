package Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private final String userName;
    private final String passWord;
    private final ArrayList<String> classes = new ArrayList<>();
    private final ArrayList<String> hobbies = new ArrayList<>();
    private final Map<String,String> messages = new HashMap<>();
    private MatchingPreferences matchPref;


    public Client(String userName, String passWord /*,ArrayList<String> classes,ArrayList<String> hobbies*/) {
        this.userName = userName;
        this.passWord = passWord;
//        this.classes = classes;
//        this.hobbies = hobbies;
    }


    public String getUserName(){
        return userName ;
    }

    public String getPassword() {
        return passWord;
    }
    public ArrayList<String> getClasses(){
        return classes;
    }
    public ArrayList<String> getHobbies(){
        return hobbies;
    }
    public MatchingPreferences getMatchPref() { return this.matchPref; }

    public Map<String,String> getMessages(){
        return messages;
    }

    /**
     *  Setter method for the matchPref attribute
     *  provides a secure way for the matchPref to be updated.
     */
    public void setMatchPref(MatchingPreferences matchPref) {
        this.matchPref = matchPref;
    }

}

