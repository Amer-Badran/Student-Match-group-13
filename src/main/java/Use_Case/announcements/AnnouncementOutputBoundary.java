package Use_Case.announcements;

public interface AnnouncementOutputBoundary {
    void prepareSuccessView(AnnouncementOutputData outputData);
    void prepareFailView(String errorMessage);
}