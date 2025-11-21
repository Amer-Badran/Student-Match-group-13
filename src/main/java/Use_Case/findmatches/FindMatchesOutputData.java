package Use_Case.findmatches;

import java.util.Map;

public class FindMatchesOutputData {

    // a map of username to score (already sorted highest to lowest(top 10))
    private final Map<String, Double> matches;

    public FindMatchesOutputData(Map<String, Double> matches) {
        this.matches = matches;
    }

    public Map<String, Double> getMatches() { return matches; }
}
