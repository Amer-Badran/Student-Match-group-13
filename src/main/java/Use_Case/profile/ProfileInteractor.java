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

        Profile profile = new Profile();
        if (profile == null) {
            profile = new Profile();
            profile.setUserName(input.getUserId());
            profile.setBio(input.getBio());
            profile.setName(input.getName());
            profile.setEmail(input.getEmail());
            profile.setNationality(input.getNationality());
            profile.setLanguages(input.getLanguages());
            profile.setPhone(input.getPhone());
            profile.setInstagram(input.getInstagram());
            profileDAO.save(profile);

        } else {
            // update fields
            profile.setUserName(input.getUserId());
            profile.setBio(input.getBio());
            profile.setName(input.getName());
            profile.setEmail(input.getEmail());
            profile.setNationality(input.getNationality());
            profile.setLanguages(input.getLanguages());
            profile.setPhone(input.getPhone());
            profile.setInstagram(input.getInstagram());
            profileDAO.save(profile);
        }

        profileDAO.save(profile);

        ProfileOutputData output =
                new ProfileOutputData(profile.getUserName(), profile.getName(), "Profile saved.");
        presenter.prepareSuccessView(output);
    }
}
