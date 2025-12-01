package use_case.announcement;

public interface AnnouncementOutputBoundary {
    void prepareSuccessView(AnnouncementOutputData output);
    void prepareFailView(String error);
    void switchToHomeView();

}

