package Data_Access;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileProgramDAO {

    private final File jsonFile;
    private final List<String> programs = new ArrayList<>();

    public FileProgramDAO(String jsonPath) {
        this.jsonFile = new File(jsonPath);
        loadPrograms();
    }

    private void loadPrograms() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(jsonFile)) {
            Object obj = parser.parse(reader);
            JSONArray programArray = (JSONArray) obj;

            for (Object programObj : programArray) {
                JSONObject programObject = (JSONObject) programObj;
                String name = (String) programObject.get("name");
                JSONArray typesArray = (JSONArray) programObject.get("types");

                for (Object programTypeObj : typesArray) {

                    // Combines the program name and type ot create unique ID
                    String type = (String) programTypeObj;
                    String displayName = name + " - " + type;
                    programs.add(displayName);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Could not read programs file: " + e.getMessage());
        }
    }

    /**
     * Returns a list of all available program names (with types).
     * @return A List<String> of user-friendly program names.
     */
    public List<String> getPrograms() {
        return new ArrayList<>(programs); // Returns a copy
    }
}