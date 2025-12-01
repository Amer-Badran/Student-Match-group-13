package use_case.login;

public class LoginOutputData {
    private String username;

    public LoginOutputData(String name){
        this.username = name;
    }

    public String getUsername() {
        return username;
    }
}
