package Interface_Adapter.profile;

import Use_Case.profile.ProfileInputBoundary;
import Use_Case.profile.ProfileInputData;

public class ProfileController {

    private final ProfileInputBoundary interactor;

    public ProfileController(ProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username,
                        String firstName,
                        String lastName,
                        String countryOfOrigin,
                        String bio,
                        String email,
                        String phone) {

        ProfileInputData inputData = new ProfileInputData(
                username, firstName, lastName, countryOfOrigin, bio, email, phone
        );
        interactor.execute(inputData);
    }
}
