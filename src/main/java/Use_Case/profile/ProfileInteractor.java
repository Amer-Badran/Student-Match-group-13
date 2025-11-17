package Use_Case.profile;

import Entity.Profile;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ProfileInteractor implements ProfileInputBoundary {

    private final ProfileDataAcessObject profileDAO;
    private final ProfileOutputBoundary presenter;

    public ProfileInteractor(ProfileDataAcessObject profileDAO,
                             ProfileOutputBoundary presenter) {
        this.profileDAO = profileDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(ProfileInputData input) throws IOException, ParseException {
        if (input.getName() == null || input.getName().isEmpty()) {
            presenter.prepareFailView("Name is required.");
            return;
        }
        if (input.getBio() != null && input.getBio().length() > 20) {
            presenter.prepareFailView("Bio must be at most 20 characters.");
            return;
        }

        Profile profile = profileDAO.getProfileByUserId(input.getUserId());
        if (profile == null) {
            profile = new Profile(
                    input.getUserId(),
                    input.getName(),
                    input.getNationality(),
                    input.getBio(),
                    input.getLanguages(),
                    input.getEmail(),
                    input.getInstagram(),
                    input.getPhone()
            );
        } else {
            // update fields
            profile.setName(input.getName());
            profile.setNationality(input.getNationality());
            profile.setBio(input.getBio());
            profile.setLanguages(input.getLanguages());
            profile.setEmail(input.getEmail());
            profile.setInstagram(input.getInstagram());
            profile.setPhone(input.getPhone());
        }

        profileDAO.save(profile);

        ProfileOutputData output =
                new ProfileOutputData(profile.getUserId(), profile.getName(), "Profile saved.");
        presenter.prepareSuccessView(output);
    }
}
