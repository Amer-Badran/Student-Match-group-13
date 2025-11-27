package Interface_Adapter.signup;


public class SignupState {
    private  String username;
    private  String password = "";
    private  String Error = "";
    private String successMessage = "";


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.Error = usernameError;
    }

    public void setPassword(String pass){this.password = pass;}

    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }

    public String getUsername(){ return username;}
    public String getError(){return Error; }
    public String getPassword(){return password;}
    public String getSuccessMessage() { return successMessage; }

    public String toString() {
        return "SignupState{}";
    }
}
