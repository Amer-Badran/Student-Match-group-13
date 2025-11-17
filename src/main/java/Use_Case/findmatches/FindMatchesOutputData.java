package Use_Case.findmatches;

import java.util.Map;

public class FindMatchesOutputData {

    // username → score (already sorted highest → lowest)
    private final Map<String, Double> matches;

    public FindMatchesOutputData(Map<String, Double> matches) {
        this.matches = matches;
    }

    public Map<String, Double> getMatches() {
        return matches;
    }
}
