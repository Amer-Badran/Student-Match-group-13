package Use_Case.signup;

import Entity.Client;

public interface SignupDataAcessObject {
    boolean alreadyExists(String username);

    void save(Client client);
}
