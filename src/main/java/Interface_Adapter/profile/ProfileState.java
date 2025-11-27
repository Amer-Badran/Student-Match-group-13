package Interface_Adapter.profile;

public class ProfileState {
    public String username = "";
    public String firstName = "";
    public String lastName = "";
    public String countryOfOrigin = "";
    public String bio = "";
    public String email = "";
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
