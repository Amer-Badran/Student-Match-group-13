package Use_Case.profile;

import Entity.Profile;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ProfileDataAcessObject {

    Profile getProfileByUserId(String userId) throws IOException, ParseException;

    void save(Profile profile) throws IOException, ParseException;
}
