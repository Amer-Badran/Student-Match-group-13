package Use_Case.signup;

import Entity.Client;

public class SignupOutputData {
    private Client client;

    public SignupOutputData(Client clients){
        this.client = clients;
    }
    public Client getClient(){
        return this.client;
    }
}

