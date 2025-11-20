package Interface_Adapter.profile;

import Entity.Client;

public class ProfileState {
    public String userName = "";
    public String name = "";
    public String nationality = "";
    public String bio = "";
    public String languages = "";
    public String email = "";
    public String instagram = "";
    public String phone = "";
    public String errorMessage = "";
    public String infoMessage = "";

    public void setUserName(String name){
        this.userName = name;
    }
}
