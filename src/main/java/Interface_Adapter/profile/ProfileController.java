package Interface_Adapter.profile;

import Use_Case.profile.ProfileInputBoundary;
import Use_Case.profile.ProfileInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ProfileController {

    private final ProfileInputBoundary interactor;

    public ProfileController(ProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username,
                        String name,
                        String nationality,
                        String bio,
                        String languages,
                        String email,
                        String instagram,
                        String phone) throws IOException, ParseException {

        ProfileInputData inputData = new ProfileInputData(
                username, name, nationality, bio, languages, email, instagram, phone
        );
        interactor.execute(inputData);
    }
}
