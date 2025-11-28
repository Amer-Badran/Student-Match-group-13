package Use_Case.matchingstrategy;

import Entity.Client;

public interface MatchingStrategy {
    double calculateScore(Client currentUser, Client otherUser);
}