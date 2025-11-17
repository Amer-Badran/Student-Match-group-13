package Use_Case.profile;

import Entity.Profile;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ProfileDataAcessObject {

    // Load a profile for this user; return null if none exists yet
    Profile getProfileByUserId(String userId) throws IOException, ParseException;

    // Create or update profile in storage
    void save(Profile profile) throws IOException, ParseException;
}
