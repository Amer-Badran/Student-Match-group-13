package Data_Access;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;


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
        try (FileReader reader = new FileReader(jsonFile)) {

            JSONTokener token = new JSONTokener(reader);
            JSONArray courseArray = new JSONArray(token);

            for (int i = 0; i < courseArray.length(); i++) {
                JSONObject courseObject = courseArray.getJSONObject(i);
                String code = courseObject.getString("code");
                String title = courseObject.getString("title");
                courses.put(code, title);
            }

        } catch (IOException | JSONException e) {
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
