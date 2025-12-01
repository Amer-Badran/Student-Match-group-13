package interface_adapter.login;

import entity.OldClient;

public class LoginState {
    private  String username;
    private  String password = "";
    private  String Error = "";
    private OldClient oldClient;


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
    public void setClient(OldClient clients){
        this.oldClient = clients;
    }
}
