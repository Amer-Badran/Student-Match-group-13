package use_case.signup;

import entity.OldClient;

public class SignupOutputData {
    private OldClient oldClient;

    public SignupOutputData(OldClient clients){
        this.oldClient = clients;
    }
    public OldClient getClient(){
        return this.oldClient;
    }
}

