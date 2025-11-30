package entity;

public class ClientFactory {

// uncomment those properties later, when things are working !
    public OldClient create(String name, String password /*, ArrayList<String> classes, ArrayList<String> hobbies*/) {
        return new OldClient(name, password /*,classes,hobbies*/);
    }
}
