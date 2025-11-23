package Use_Case.announcements;

import java.io.IOException;
import java.util.Map;

public class AnnouncementInteractor implements AnnouncementInputBoundary {

    private final AnnouncementDataAccessObject dao;
    private final AnnouncementOutputBoundary presenter;

    public AnnouncementInteractor(AnnouncementDataAccessObject dao, AnnouncementOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void execute(AnnouncementInputData input) {
        try {
            String user = input.getUser();
            String message = input.getMessage();

            // 1️⃣ validate
            if (message == null || message.trim().isEmpty()) {
                presenter.prepareFailView("Announcement cannot be empty.");
                return;
            }

            // load existing announcements
            Map<String, String> announcements = dao.loadAnnouncements();

            // add or update this user's announcement
            announcements.put(user, message);

            //  save back
            dao.saveAnnouncements(announcements);

            // notify presenter
            presenter.prepareSuccessView(new AnnouncementOutputData("Announcement posted."));

        } catch (IOException e) {
            presenter.prepareFailView("Error posting announcement: " + e.getMessage());
        }
    }
}