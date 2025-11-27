package Use_Case.announcements;

import Entity.Announcement;
import java.util.List;

public interface AnnouncementDataAccessObject {
    void saveAnnouncement(Announcement announcement); // save a new announcement
    List<Announcement> getRecentAnnouncements(int limit);
    // fetch top N announcements, this should show a limited # of recent announcements
}
