package Data_Access;

import Entity.Client;
import Entity.ClientFactory;
import Use_Case.signup.SignupDataAcessObject;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataAcessObject implements SignupDataAcessObject {

    private static final String HEADER = "username,password";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, Client> accounts = new HashMap<>();

    private String currentUsername;


    public DataAcessObject(String csvPath, ClientFactory clientFactory) {

    csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);

        if (csvFile.length() == 0) {
        save();
    }
        else {

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            final String header = reader.readLine();

            if (!header.equals(HEADER)) {
                throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
            }

            String row;
            while ((row = reader.readLine()) != null) {
                final String[] col = row.split(",");
                final String username = String.valueOf(col[headers.get("username")]);
                final String password = String.valueOf(col[headers.get("password")]);

                // modify this method later on
                final Client user = clientFactory.create(username, password);
                accounts.put(username, user);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

private void save() {
    final BufferedWriter writer;
    try {
        writer = new BufferedWriter(new FileWriter(csvFile));
        writer.write(String.join(",", headers.keySet()));
        writer.newLine();

        for (Client client : accounts.values()) {
            final String line = String.format("%s,%s",
                    client.getUserName(), client.getPassword());
            writer.write(line);
            writer.newLine();
        }

        writer.close();

    }
    catch (IOException ex) {
        throw new RuntimeException(ex);
    }
}

@Override
public void save(Client client) {
    accounts.put(client.getUserName(), client);
    this.save();
}

@Override
public boolean alreadyExists(String identifier) {
    return accounts.containsKey(identifier);
}




}

