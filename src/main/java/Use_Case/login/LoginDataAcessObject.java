package Use_Case.login;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface LoginDataAcessObject {
    boolean userExists(String userName) throws IOException, ParseException;
    boolean correctPassword(String name,String pass) throws IOException, ParseException;
    boolean checkNewUser(String userName) throws IOException, ParseException;
    ArrayList<String> getNotification(String username) throws IOException, ParseException;

}
