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

    public void execute(String userId,
                        String name,
                        String nationality,
                        String bio,
                        String languages,
                        String email,
                        String instagram,
                        String phone) throws IOException, ParseException {

        ProfileInputData inputData = new ProfileInputData(
                userId, name, nationality, bio, languages, email, instagram, phone
        );
        interactor.execute(inputData);
    }
}
