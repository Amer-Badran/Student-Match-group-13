package use_case.matchingstrategy;

import entity.Client;

public interface MatchingStrategy {
    double calculateScore(Client currentUser, Client otherUser);
}