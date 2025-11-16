package Use_Case.findmatches;

import Entity.User;
import java.util.List;

public interface FindMatchesDataAccessObject {
    User findByUsername(String username);
    List<User> getAllUsers();
}
