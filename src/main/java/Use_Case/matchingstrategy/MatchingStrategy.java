package Use_Case.matchingstrategy;

import Entity.User;

public interface MatchingStrategy {
    double calculateScore(User currentUser, User otherUser);
}