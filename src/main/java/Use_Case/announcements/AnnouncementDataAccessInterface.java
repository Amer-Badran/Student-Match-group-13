package Use_Case.announcements;

import Entity.Announcement;

public interface AnnouncementDataAccessInterface {
    Announcement load(String id);
}
