package Use_Case.signup;

import Entity.OldClient;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface SignupDataAcessObject {
    boolean alreadyExists(String username) throws IOException, ParseException;

    void save(OldClient oldClient);
    ArrayList<String> getClasses(String name) throws IOException, ParseException;
}
