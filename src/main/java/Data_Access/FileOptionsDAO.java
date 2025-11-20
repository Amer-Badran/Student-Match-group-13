package Data_Access;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileOptionsDAO {

    private List<String> options;

    /**
     * Constructor that initializes the DAO and parses the specified file.
     *
     * @param filePath The path to the txt file containing options for hobbies/languages.
     */
    public FileOptionsDAO(String filePath) {
        this.options = new ArrayList<>();
        loadOptionsFromFile(filePath);
    }

    /**
     * Reads the file line by line and populates the hobbies/languages list using modern Java utility methods.
     */
    private void loadOptionsFromFile(String filePath) {
        Path path = Paths.get(filePath);

        // Check if file exists before trying to read
        if (!Files.exists(path)) {
            System.err.println("Error: File not found at " + filePath);
            return;
        }

        try {
            // Use Files.readAllLines() to read all lines into a List<String>.
            // Requires the input file to be clean! (I think I cleaned it up).
            this.options = Files.readAllLines(path, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the list of hobbies/languages parsed from the file.
     *
     * @return A List<String> of hobbies/languages.
     */
    public List<String> getOptions() {
        return this.options;
    }
}