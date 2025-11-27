package Use_Case.announcements;

import Entity.Announcement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDataAccessObject {

    private final File announcementFile;
    private final JSONParser parser = new JSONParser();

    public AnnouncementDataAccessObject() throws IOException {
        this("Announcements.json");
    }

    public AnnouncementDataAccessObject(String filePath) throws IOException {
        announcementFile = new File(filePath);
        if (!announcementFile.exists()) {
            announcementFile.createNewFile();
            try (FileWriter writer = new FileWriter(announcementFile)) {
                writer.write("[]");
            }
        }
    }

    public List<Announcement> loadAnnouncements() throws IOException {
        try (FileReader reader = new FileReader(announcementFile)) {
            Object parsed = parser.parse(reader);
            JSONArray array = (JSONArray) parsed;
            List<Announcement> announcements = new ArrayList<>();
            for (Object obj : array) {
                JSONObject item = (JSONObject) obj;
                String user = (String) item.getOrDefault("user", "");
                String message = (String) item.getOrDefault("message", "");
                announcements.add(new Announcement(user, message));
            }
            return announcements;
        } catch (ParseException e) {
            throw new IOException("Failed to parse announcements", e);
        }
    }

    public void saveAnnouncements(List<Announcement> newAnnouncements) throws IOException {
        JSONArray array = new JSONArray();
        for (Announcement announcement : newAnnouncements) {
            JSONObject item = new JSONObject();
            item.put("user", announcement.getUser());
            item.put("message", announcement.getMessage());
            array.add(item);
        }

        try (FileWriter writer = new FileWriter(announcementFile)) {
            writer.write(array.toJSONString());
        }
    }
}