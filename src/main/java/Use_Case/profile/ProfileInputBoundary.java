package Use_Case.profile;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ProfileInputBoundary {
    void execute(ProfileInputData inputData) throws IOException, ParseException;   // create/edit profile
}
