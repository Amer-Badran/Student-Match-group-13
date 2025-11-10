package Entity;

public class Client {
    private final String userName;
    private final String passWord;

    private Profile profile;
    private MatchingPreferences preferences;
//    private final ArrayList<String> classes;
//    private final ArrayList<String> hobbies;



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
//    public ArrayList<String> getClasses(){
//        return classes;
//    }
//    public ArrayList<String> getHobbies(){
//        return hobbies;
//    }
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public MatchingPreferences getPreferences() { return preferences; }
    public void setPreferences(MatchingPreferences preferences) { this.preferences = preferences; }


}

