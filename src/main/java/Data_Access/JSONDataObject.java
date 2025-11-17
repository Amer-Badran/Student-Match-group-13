package Data_Access;
import Entity.Client;
import Use_Case.login.LoginDataAcessObject;
import Use_Case.signup.SignupDataAcessObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class JSONDataObject implements SignupDataAcessObject, LoginDataAcessObject {
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

            JSONObject newUser = new JSONObject();
            newUser.put("username", client.getUserName());
            newUser.put("password", client.getPassword());
            newUser.put("classes", client.getClasses());
            newUser.put("hobbies", client.getHobbies());
            client.getMessages().put("User1","Hello user X, hope you are doing well!");
            newUser.put("messages",client.getMessages());
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



