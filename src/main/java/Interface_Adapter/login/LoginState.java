package Interface_Adapter.login;

import Entity.Client;

public class LoginState {
    private  String username;
    private  String password = "";
    private  String Error = "";
    private Client client;


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
    public void setClient(Client clients){
        this.client = clients;
    }
}
