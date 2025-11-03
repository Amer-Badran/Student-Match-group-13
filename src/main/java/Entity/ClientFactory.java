package Entity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClientFactory {

// uncomment those properties later, when things are working !
    public Client create(String name, String password /*, ArrayList<String> classes, ArrayList<String> hobbies*/) {
        return new Client(name, password /*,classes,hobbies*/);
    }
}
