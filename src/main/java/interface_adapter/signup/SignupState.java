package interface_adapter.signup;


public class SignupState {
    private  String username;
    private  String password = "";
    private  String Error = "";


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.Error = usernameError;
    }

    public void setPassword(String pass){this.password = pass;}

    public String getUsername(){ return username;}
    public String getError(){return Error; }
    public String getPassword(){return password;}

    public String toString() {
        return "SignupState{}";
    }
}
