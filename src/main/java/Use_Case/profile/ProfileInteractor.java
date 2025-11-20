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
                    input.getName(),
                    input.getNationality(),
                    input.getBio(),
                    input.getLanguages(),
                    input.getEmail(),
                    input.getInstagram(),
                    input.getPhone()
            );
        } else {
            profile.setName(input.getName());
            profile.setNationality(input.getNationality());
            profile.setBio(input.getBio());
            profile.setLanguages(input.getLanguages());
            profile.setEmail(input.getEmail());
            profile.setInstagram(input.getInstagram());
            profile.setPhone(input.getPhone());
        }

        try {
            profileDAO.save(profile);
        } catch (Exception e) {
            presenter.prepareFailView("Failed to save profile: " + e.getMessage());
            return;
        }

        ProfileOutputData output =
                new ProfileOutputData(profile.getUsername(), profile.getName(), "Profile saved.");
        presenter.prepareSuccessView(output);
    }
}
