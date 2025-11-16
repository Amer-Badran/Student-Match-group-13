package Use_Case.login;

import Interface_Adapter.login.LoginState;

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
