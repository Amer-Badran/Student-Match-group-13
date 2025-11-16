package Use_Case.findmatches;

import Entity.User;
import java.util.List;

public class FindMatchesOutputData {
    private final User currentUser;
    private final List<User> matchedUsers;

    public FindMatchesOutputData(User currentUser, List<User> matchedUsers) {
        this.currentUser = currentUser;
        this.matchedUsers = matchedUsers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getMatchedUsers() {
        return matchedUsers;
    }
}
