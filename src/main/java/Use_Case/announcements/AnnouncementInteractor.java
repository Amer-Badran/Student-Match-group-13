package Use_Case.announcements;

import Entity.Announcement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            if (message == null || message.trim().isEmpty()) {
                presenter.prepareFailView("Announcement cannot be empty.");
                return;
            }

            List<Announcement> announcements = new ArrayList<>(dao.loadAnnouncements());
            announcements.add(new Announcement(user, message.trim()));
            dao.saveAnnouncements(announcements);

            presenter.prepareSuccessView(new AnnouncementOutputData("Announcement posted.", announcements));

        } catch (IOException e) {
            presenter.prepareFailView("Error posting announcement: " + e.getMessage());
        }
    }
}