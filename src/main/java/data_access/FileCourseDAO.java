package data_access;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class FileCourseDAO {

    private final File jsonFile;
    private final Map<String, String> courses = new HashMap<>();

    /**
     * Creates a new FileCourseDAO by reading JSON file.
     * @param jsonPath The path to the uoft_courses_storage.json file.
     */
    public FileCourseDAO(String jsonPath) {
        this.jsonFile = new File(jsonPath);

        // Calls helper method which reads the file and fills out the map
        // to keep constructor clean.
        loadCourses();
    }

    /** Creates a json parser, opens filereader, parses the file
     * loops through each item, gets code and title for each course,
     * puts them into the map.
     */
    private void loadCourses() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(jsonFile)) {
            Object obj = parser.parse(reader);

            // Cast the Object to a JSONArray
            JSONArray courseArray = (JSONArray) obj;

            for (Object courseObj : courseArray) {
                // Cast each item to a JSONObject
                JSONObject courseObject = (JSONObject) courseObj;

                // Get the string for "code" and "title"
                String code = (String) courseObject.get("code");
                String title = (String) courseObject.get("title");

                courses.put(code, title);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException("Could not read courses file: " + e.getMessage());
        }
    }

    /**
     * Returns a map of all available courses.
     * @return A Map<String, String> where keys are course codes and values are course titles.
     */
    public Map<String, String> getCourses() {
        // Returning a copy to prevent accidental modifications
        // by other parts of the program.
        return new HashMap<>(courses);
    }
}
