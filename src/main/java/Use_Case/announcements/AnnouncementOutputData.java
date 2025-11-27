package Use_Case.announcements;

import Entity.Announcement;

import java.util.List;

public class AnnouncementOutputData {
    private final String resultMessage;
    private final List<Announcement> announcements;

    public AnnouncementOutputData(String resultMessage, List<Announcement> announcements) {
        this.resultMessage = resultMessage;
        this.announcements = announcements;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }
}