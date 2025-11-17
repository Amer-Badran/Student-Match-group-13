package Use_Case.announcements;

import Entity.Announcement;

public class AnnouncementInteractor implements AnnouncementInputBoundary {

    private final AnnouncementDataAccessInterface dataAccess;
    private final AnnouncementOutputBoundary presenter;

    public AnnouncementInteractor(AnnouncementDataAccessInterface dataAccess,
                                  AnnouncementOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }


    @Override
    public void execute(AnnouncementInputData inputData) {
        Announcement announcement = dataAccess.load(inputData.announcementId());

        AnnouncementOutputData outputData = new AnnouncementOutputData(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getAuthor()
        );

        presenter.prepareSuccessView(outputData);
    }
}
