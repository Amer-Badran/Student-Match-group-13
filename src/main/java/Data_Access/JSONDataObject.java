package Data_Access;
import Entity.Client;
import Entity.MatchingPreferences;
import Entity.OldClient;
import Entity.Profile;
import Use_Case.EnterInfo.MatchingPreferencesDataAccessObject;
import Use_Case.login.LoginDataAcessObject;
import Use_Case.profile.ProfileDataAccessObject;
import Use_Case.signup.SignupDataAcessObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONDataObject implements SignupDataAcessObject,
        LoginDataAcessObject, ProfileDataAccessObject , MatchingPreferencesDataAccessObject {
    private final File fileJSON;
    private final File PrettyJSON;
    private final File CleanData;
    private final JSONParser parser = new JSONParser();

    public JSONDataObject() throws IOException {
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

        OldClient user = new OldClient("User1","123456");
        save(user);
        OldClient user2 = new OldClient("User2","abcdef");
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

    public void save(OldClient client) {
        try {

            JSONArray usersy = readAll();

            JSONObject newUser = new JSONObject();
            newUser.put("username", client.getUserName());
            newUser.put("password", client.getPassword());
            newUser.put("courses",new ArrayList<>());
            newUser.put("Bio","");
            newUser.put("nationality","");
            newUser.put("email","");
            newUser.put("phone","");
            newUser.put("instagram","");
            client.getMessages().put("User1","Hello user X, hope you are doing well!");
            newUser.put("messages",client.getMessages());
            newUser.put("programs",new HashMap<String, String>());
            newUser.put("yearOfStudy", 0);
            newUser.put("hobbies", new ArrayList<String>());
            newUser.put("languages", new ArrayList<String>());
            newUser.put("weights", new HashMap<String,Double>());
            usersy.add(newUser);

            // rewrite file

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
                    ArrayList<String> toCheck = (ArrayList<String>) user.get("courses");
                    if(toCheck.isEmpty()){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }


    @Override
    public Profile getProfileByUsername(String username) throws IOException, ParseException {
        return null;
    }

    public Client getClientByUsername(String username) throws IOException, ParseException {
        JSONArray data = readAll();
        for (Object obj : data) {
            JSONObject user = (JSONObject) obj;
            if (username.equals(user.get("username"))) {
                String username2 = (String) user.get("username");
                String password = (String) user.get("password");
                ArrayList<String> courses = (ArrayList<String>) user.get("courses");
                String Name = (String) user.get("name");
                String Bio = (String) user.get("Bio");
                String nationality = (String) user.get("nationality");
                String email = (String) user.get("email");
                String phone = (String) user.get("phone");
                String insta = (String) user.get("instagram");
                Map<String, String> programs = (HashMap<String, String>) user.get("programs");
                int years = (Integer) user.get("yearOfStudy");
                ArrayList<String> hobbies2 = (ArrayList<String>) user.get("hobbies");
                ArrayList<String> languages = (ArrayList<String>) user.get("languages");
                HashMap<String,Double> weights =(HashMap<String, Double>) user.get("weights");

                Profile profile = new Profile(username2,Name,nationality,Bio,email,insta,phone);
                MatchingPreferences preferences = new MatchingPreferences(courses,programs,years,hobbies2,languages,weights);
                Client man = new Client(username2,password,profile,preferences);
                return man;
            }
            else{
                continue;
            }
        }return null;
    }

    public ArrayList<Client> AllClients() throws IOException, ParseException {
        ArrayList<Client> theUsers = new ArrayList<Client>();
        JSONArray data = readAll();
        for (Object obj : data) {
            JSONObject JSONuser = (JSONObject) obj;
            Client currentUser = getClientByUsername((String) JSONuser.get("username"));
            theUsers.add(currentUser);
        }
        return theUsers;
    }


    @Override
    public void save(Profile profile) throws IOException, ParseException {
        JSONArray users = readAll();
        if(!users.isEmpty()){
            for (Object obj : users) {
                JSONObject user = (JSONObject) obj;
                if (profile.getUsername().equals(user.get("username"))) {
                    user.put("name",profile.getName());
                    user.put("Bio",profile.getBio());
                    user.put("nationality",profile.getCountryOfOrigin());
                    user.put("email",profile.getEmail());
                    user.put("phone",profile.getPhone());
                    user.put("instagram",profile.getInstagram());


                    // rewrite file

                    try (FileWriter writer = new FileWriter(fileJSON);
                         FileWriter pretty =new FileWriter(PrettyJSON);
                         FileWriter clean =new FileWriter(CleanData)) {
                        writer.write(users.toJSONString());
                        writer.flush();

// making it readable to us humans :
                        JSONArray checks = new JSONArray();
                        pretty.write('[');
                        for(Object objs:users){
                            JSONObject JsonObj = (JSONObject) objs;
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
                }
            }
        }
//


    }


    @Override
    public OldClient getClient(String username) throws IOException, ParseException {
        return null;
    }

    @Override
    public void save(String username,MatchingPreferences mp) throws IOException, java.text.ParseException, ParseException {
        JSONArray users = readAll();
        if(!users.isEmpty()){
            for (Object obj : users) {
                JSONObject user = (JSONObject) obj;
                if (username.equals(user.get("username"))) {
                    user.put("courses",mp.getCourses());
                    user.put("programs",mp.getPrograms());
                    user.put("yearOfStudy", mp.getYearOfStudy());
                    user.put("hobbies", mp.getHobbies());
                    user.put("languages", mp.getLanguages());
                    user.put("weights", mp.getWeights());


                    // rewrite file

                    try (FileWriter writer = new FileWriter(fileJSON);
                         FileWriter pretty =new FileWriter(PrettyJSON);
                         FileWriter clean =new FileWriter(CleanData)) {
                        writer.write(users.toJSONString());
                        writer.flush();

// making it readable to us humans :
                        JSONArray checks = new JSONArray();
                        pretty.write('[');
                        for(Object objs:users){
                            JSONObject JsonObj = (JSONObject) objs;
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



                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
//
    }
}



