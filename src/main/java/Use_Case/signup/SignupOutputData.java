package Use_Case.signup;

import Entity.OldClient;

public class SignupOutputData {
    private OldClient oldClient;

    public SignupOutputData(OldClient clients){
        this.oldClient = clients;
    }
    public OldClient getClient(){
        return this.oldClient;
    }
}

