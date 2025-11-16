package Use_Case.signup;

import Entity.User;

public interface SignupDataAcessObject {
    boolean alreadyExists(String username);

    void save(User client);
}
