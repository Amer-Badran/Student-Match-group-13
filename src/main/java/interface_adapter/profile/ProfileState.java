package interface_adapter.profile;

public class ProfileState {
    public String username = "";
    public String name = "";
    public String countryOfOrigin = "";
    public String bio = "";
    public String email = "";
    public String instagram = "";
    public String phone = "";
    public String errorMessage = "";
    public String infoMessage = "";

public void setUsername(String name){
    username = name;
}

    public String getUsername() {
        return username;
    }
}
