package Use_Case.announcements;

import Entity.Announcement;
import java.util.HashMap;
import java.util.Map;

public class AnnouncementDataAccessObject implements AnnouncementDataAccessInterface {

    private final Map<String, Announcement> announcements = new HashMap<>();

    public AnnouncementDataAccessObject() {
        // TEMP FAKE DATA — FOR TESTING ONLY
        announcements.put("1", new Announcement(
                "1",
                "Welcome Back!",
                "We are excited to start the semester!",
                "Admin"
        ));

        announcements.put("2", new Announcement(
                "2",
                "Clubs Fair",
                "Come check out clubs this Friday!",
                "Student Council"
        ));
    }

    @Override
    public Announcement load(String id) {
        return announcements.get(id);
    }
}
