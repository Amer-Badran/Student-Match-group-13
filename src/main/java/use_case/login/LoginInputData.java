package use_case.login;

public class LoginInputData {
    private String name;
    private String pass;
    public LoginInputData(String username,String password){
        this.name = username;
        this.pass = password;
    }
    public String getName(){
        return name;
    }
    public String getPass(){
        return pass;
    }

}
