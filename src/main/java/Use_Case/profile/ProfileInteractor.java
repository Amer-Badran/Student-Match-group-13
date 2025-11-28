package Use_Case.profile;

import Entity.Profile;

public class ProfileInteractor implements ProfileInputBoundary {

    private final ProfileDataAccessObject profileDAO;
    private final ProfileOutputBoundary presenter;

    public ProfileInteractor(ProfileDataAccessObject profileDAO,
                             ProfileOutputBoundary presenter) {
        this.profileDAO = profileDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(ProfileInputData input) {

        if (isBlank(input.getFirstName()) ||
                isBlank(input.getLastName()) ||
                isBlank(input.getCountryOfOrigin()) ||
                isBlank(input.getBio()) ||
                isBlank(input.getEmail()) ||
                isBlank(input.getPhone())) {

            presenter.prepareFailView("All fields are required.");
            return;
        }

        if (input.getBio().length() > 200) {
            presenter.prepareFailView("Bio must be at most 200 characters.");
            return;
        }

        Profile profile;
        try {
            profile = profileDAO.getProfileByUsername(input.getUsername());
        } catch (Exception e) {
            presenter.prepareFailView("Failed to load profile: " + e.getMessage());
            return;
        }

        if (profile == null) {
            profile = new Profile(
                    input.getUsername(),
                    input.getFirstName(),
                    input.getLastName(),
                    input.getCountryOfOrigin(),
                    input.getBio(),
                    input.getEmail(),
                    input.getPhone()
            );
        } else {
            profile.setFirstName(input.getFirstName());
            profile.setLastName(input.getLastName());
            profile.setCountryOfOrigin(input.getCountryOfOrigin());
            profile.setBio(input.getBio());
            profile.setEmail(input.getEmail());
            profile.setPhone(input.getPhone());
        }

        try {
            profileDAO.save(profile);
        } catch (Exception e) {
            presenter.prepareFailView("Failed to save profile: " + e.getMessage());
            return;
        }

        ProfileOutputData output =
                new ProfileOutputData(profile.getUsername(), profile.getFirstName(), profile.getLastName(), "Profile saved.");
        presenter.prepareSuccessView(output);
    }

    private boolean isBlank(String s) {
        return s == null || s.isEmpty();
    }
}
