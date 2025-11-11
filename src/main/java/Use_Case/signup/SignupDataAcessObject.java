package Use_Case.signup;

import Entity.Client;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface SignupDataAcessObject {
    boolean alreadyExists(String username) throws IOException, ParseException;

    void save(Client client);
    ArrayList<String> getClasses(String name) throws IOException, ParseException;
}
