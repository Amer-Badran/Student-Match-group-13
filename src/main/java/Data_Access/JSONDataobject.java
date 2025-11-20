package Data_Access;
import Entity.Client;
import Entity.MatchingPreferences;
import Use_Case.login.LoginDataAcessObject;
import Use_Case.signup.SignupDataAcessObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class JSONDataobject implements SignupDataAcessObject, LoginDataAcessObject {
    private final File fileJSON;
    private final File PrettyJSON;
    private final File CleanData;
    private final JSONParser parser = new JSONParser();

    public JSONDataobject() throws IOException {
        fileJSON = new File("ProjectData.json");
        PrettyJSON = new File("PrettyJSON.json");
        CleanData = new File("CleanData.json");
        System.out.println(!fileJSON.exists());
//        final FileWriter fileWriter = new FileWriter(fileJSON);

        if(!fileJSON.exists()){
            fileJSON.createNewFile();
            try (FileWriter writer = new FileWriter(fileJSON)) {
                writer.write("[]");  // initialize empty JSON array
            }}


            if(!CleanData.exists()){
                CleanData.createNewFile();
                try (FileWriter writers = new FileWriter(CleanData)) {
                    writers.write("[]");  // initialize empty JSON array
                }
        }

        if(!PrettyJSON.exists()){
            PrettyJSON.createNewFile();
        }

        Client user = new Client("User1","123456");
        save(user);
        Client user2 = new Client("User2","abcdef");
        save(user2);
    }

    @Override
    public boolean alreadyExists(String username) throws IOException, ParseException {

        JSONArray users = readAll();
        if(!users.isEmpty()){
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (username.equals(user.get("username"))) {
                return true;
            }
        }}
        return false;
    }
    public boolean alreadyExists(JSONArray array,String username) throws IOException, ParseException {

        JSONArray users = array;
        if(!users.isEmpty()){
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (username.equals(user.get("username"))) {
                return true;
            }
        }}
        return false;
    }

    public void save(Client client) {
        try {
            JSONArray usersy = readAll();

            // this is for adding matching preferences to file and later updating
            findAndRemoveUser(usersy, client.getUserName());

            JSONObject newUser = new JSONObject();
            newUser.put("username", client.getUserName());
            newUser.put("password", client.getPassword());
            newUser.put("classes", client.getClasses());
            newUser.put("hobbies", client.getHobbies());
            client.getMessages().put("User1","Hello user X, hope you are doing well!"); // This line seems to be for testing
            newUser.put("messages",client.getMessages());

            //
            MatchingPreferences prefs = client.getMatchPref();

            // Only add preferences if they exist (are not null)
            if (prefs != null) {
                // Creates a new JSON object just for the preferences
                JSONObject matchPrefJson = new JSONObject();

                matchPrefJson.put("courses", prefs.getCourseCodes());
                matchPrefJson.put("programs", prefs.getProgramNames());
                matchPrefJson.put("year", prefs.getYearOfStudy());

                // Add that new JSON object to the main user
                newUser.put("matchingPreferences", matchPrefJson);
            }

            // Adds the new/updated user to the array (because now can update)
            usersy.add(newUser);


            try (FileWriter writer = new FileWriter(fileJSON);
                 FileWriter pretty =new FileWriter(PrettyJSON);
                 FileWriter clean =new FileWriter(CleanData)) {
                writer.write(usersy.toJSONString());
                writer.flush();

                // making it readable to us humans :
                JSONArray checks = new JSONArray();
                pretty.write('[');
                for(Object obj:usersy){
                    JSONObject JsonObj = (JSONObject) obj;
                    if(alreadyExists(checks,(String)JsonObj.get("username"))){
                        continue;
                    }
                    else{
                        pretty.write(JsonObj.toJSONString());
                        pretty.write(',');
                        pretty.write('\n');
                        checks.add(JsonObj);}

                } pretty.write(']');

                // Cleaning up the Data for use later
                clean.write(checks.toJSONString());
                clean.flush();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to find and remove a user from a JSONArray by username.
     * @param users The JSONArray to search.
     * @param username The username to find and remove.
     */
    private void findAndRemoveUser(JSONArray users, String username) {
        for (int i = 0; i < users.size(); i++) {
            JSONObject oldUser = (JSONObject) users.get(i);
            String oldUsername = (String) oldUser.get("username");

            // If user is found it is removed and replaces with new user.
            if (oldUsername.equals(username)) {
                users.remove(i);
                break;
            }
        }
    }

    //testing formating stuff

    // end of testing foramtting stuff

    private JSONArray readAll() throws IOException, ParseException {
        //try (FileReader reader = new FileReader(fileJSON))
        try (FileReader reader = new FileReader(CleanData)) {
            Object obj = parser.parse(reader);
            return (JSONArray) obj;
        }
    }


    public ArrayList<String> getClasses(String username) throws IOException, ParseException {
        JSONArray data = readAll();
        for (Object obj : data) {
            JSONObject user = (JSONObject) obj;
            if (username.equals(user.get("username"))) {
                return (ArrayList<String>) user.get("classes");
            }
            else{
                continue;
            }
        }return null;
    }

    @Override
    public boolean userExists(String userName) throws IOException, ParseException {
        return this.alreadyExists(userName);
    }

    @Override
    public boolean correctPassword(String name, String pass) throws IOException, ParseException {
        JSONArray users = readAll();
        if(!users.isEmpty()){
            for (Object obj : users) {
                JSONObject user = (JSONObject) obj;
                 if (name.equals(user.get("username"))) {
                     if(pass.equals(user.get("password"))){
                         return true;
                     }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean checkNewUser(String userName) throws IOException, ParseException {
        JSONArray users = readAll();
        if(!users.isEmpty()){
            for (Object obj : users) {
                JSONObject user = (JSONObject) obj;
                if (userName.equals(user.get("username"))) {
                    ArrayList<String> toCheck = (ArrayList<String>) user.get("classes");
                    if(toCheck.isEmpty()){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
//


    }



