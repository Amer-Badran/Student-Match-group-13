package use_case.profile;

import entity.Profile;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ProfileDataAccessObject {

    Profile getProfileByUsername(String username) throws IOException, ParseException;
    void save(Profile profile) throws IOException, ParseException;
}
