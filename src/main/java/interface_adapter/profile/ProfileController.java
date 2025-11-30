package interface_adapter.profile;

import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInputData;

public class ProfileController {

    private final ProfileInputBoundary interactor;

    public ProfileController(ProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username,
                        String name,
                        String countryOfOrigin,
                        String bio,
                        String email,
                        String instagram,
                        String phone) {

        ProfileInputData inputData = new ProfileInputData(
                username, name, countryOfOrigin, bio, email, instagram, phone
        );
        interactor.execute(inputData);
    }
}
