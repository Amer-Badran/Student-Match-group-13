package test_use_case;

import entity.Client;
import entity.MatchingPreferences;
import entity.Profile;
import use_case.findmatches.FindMatchesDataAccessObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSONDAOSample implements FindMatchesDataAccessObject {

    private final File sampleClients;
    private final JSONParser parser = new JSONParser();

    public JSONDAOSample() {
        try {
            // Use classloader to load from resources
            java.net.URL url = getClass().getClassLoader().getResource("SampleClients.json");
            if (url == null) {
                throw new RuntimeException("SampleClients.json not found in resources!");
            }
            sampleClients = new File(url.getFile());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load SampleClients.json", e);
        }
    }

    private JSONArray readAll() {
        try (FileReader reader = new FileReader(sampleClients)) {
            Object obj = parser.parse(reader);
            return (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    @Override
    public Client findByUsername(String username) {
        JSONArray data = readAll();

        for (Object obj : data) {
            JSONObject user = (JSONObject) obj;

            if (username.equals(user.get("username"))) {
                try {
                    // Basic fields (match your JSON exactly)
                    String username2 = (String) user.get("username");
                    String password = (String) user.get("password");
                    String name = (String) user.get("name");
                    String bio = (String) user.get("Bio");
                    String countryOfOrigin = (String) user.get("nationality");
                    String email = (String) user.get("email");
                    String instagram = (String) user.get("instagram");
                    String phone = (String) user.get("phone");

                    // Courses
                    ArrayList<String> courses = new ArrayList<>();
                    JSONArray coursesArray = (JSONArray) user.get("courses");
                    for (Object c : coursesArray) courses.add(c.toString());

                    // Programs (object → map)
                    Map<String, String> programs = new HashMap<>();
                    JSONObject programsJSON = (JSONObject) user.get("programs");
                    for (Object key : programsJSON.keySet()) {
                        programs.put(key.toString(), programsJSON.get(key).toString());
                    }

                    // Year of study
                    int yearOfStudy = ((Long) user.get("yearsOfStudy")).intValue();


                    // Hobbies
                    ArrayList<String> hobbies = new ArrayList<>();
                    JSONArray hobbiesArray = (JSONArray) user.get("hobbies");
                    for (Object h : hobbiesArray) hobbies.add(h.toString());

                    // Languages
                    ArrayList<String> languages = new ArrayList<>();
                    JSONArray langArray = (JSONArray) user.get("languages");
                    for (Object l : langArray) languages.add(l.toString());

                    // Weights (real double values)
                    Map<String, Double> weights = new HashMap<>();
                    JSONObject weightsJSON = (JSONObject) user.get("weights");
                    for (Object key : weightsJSON.keySet()) {
                        weights.put(key.toString(), ((Number) weightsJSON.get(key)).doubleValue());
                    }

                    // Build profile + prefs
                    Profile profile = new Profile(username2, name, countryOfOrigin, bio, email, instagram, phone);
                    MatchingPreferences preferences = new MatchingPreferences(
                            courses, programs, yearOfStudy, hobbies, languages, weights
                    );

                    return new Client(username2, password, profile, preferences);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<Client> getAllUsers() {
        ArrayList<Client> allUsers = new ArrayList<>();
        JSONArray data = readAll();

        for (Object obj : data) {
            JSONObject userJSON = (JSONObject) obj;
            Client c = findByUsername(userJSON.get("username").toString());
            if (c != null) allUsers.add(c);
        }

        return allUsers;
    }

    @Override
    public Profile getProfile(String name) {
        Client c = findByUsername(name);
        return c != null ? c.getProfile() : null;
    }

    @Override
    public boolean UserExists(String name) {
        return findByUsername(name) != null;
    }

}