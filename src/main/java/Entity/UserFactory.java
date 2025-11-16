package Entity;

public class UserFactory {

// uncomment those properties later, when things are working !
    public User create(String name, String password /*, ArrayList<String> classes, ArrayList<String> hobbies*/) {
        return new User(name, password /*,classes,hobbies*/);
    }
}
