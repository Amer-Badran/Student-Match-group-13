package Data_Access;

import Entity.Client;
import Use_Case.findmatches.FindMatchesDataAccessObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Logger;
import java.util.logging.Level;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonClientDAO implements FindMatchesDataAccessObject {

    private static final Logger logger = Logger.getLogger(JsonClientDAO.class.getName());
    private final List<Client> allClients;

    public JsonClientDAO(String jsonFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        List<Client> tempClients;
        try {
            // Deserialize JSON array into List<Client>
            tempClients = mapper.readValue(new File(jsonFilePath), new TypeReference<List<Client>>() {});
        } catch (IOException e) {
            logger.log(Level.SEVERE, e, () -> "Error loading file: " + jsonFilePath);
            tempClients = new ArrayList<>(); // fallback to empty list
        }
        this.allClients = tempClients;
    }

    @Override
    public Client findByUsername(String username) {
        for (Client client : allClients) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public List<Client> getAllUsers() {
        return new ArrayList<>(allClients); // return a copy to avoid modification
    }
}
