package Use_Case.announcements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnnouncementDataAccessObject {

    // store announcements in memory
    private final Map<String, String> announcements = new HashMap<>();

    public AnnouncementDataAccessObject() {
        // optionally, preload some announcements
        announcements.put("user1", "Welcome everyone!");
    }

    public Map<String, String> loadAnnouncements() throws IOException {
        // just return a copy
        return new HashMap<>(announcements);
    }

    public void saveAnnouncements(Map<String, String> newAnnouncements) throws IOException {
        announcements.clear();
        announcements.putAll(newAnnouncements);
    }
}