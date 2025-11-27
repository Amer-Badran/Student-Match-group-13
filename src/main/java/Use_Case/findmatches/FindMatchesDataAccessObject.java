package Use_Case.findmatches;

import Entity.Client;
import java.util.List;

public interface FindMatchesDataAccessObject {
    Client findByUsername(String username);
    List<Client> getAllUsers();
}
