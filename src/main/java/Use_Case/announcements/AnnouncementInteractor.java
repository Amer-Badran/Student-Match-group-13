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
        // nothing implemented yet
        // later:
        // 1. create Announcement entity
        // 2. save via DAO
        // 3. notify presenter
    }

    public void switchToAnnouncementsView() {
        // nothing implemented yet
        // later, will notify presenter/view to switch to announcement tab
        // presenter.switchToAnnouncementsView();
    }

}