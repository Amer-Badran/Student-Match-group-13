package Interface_Adapter.findmatches;

import java.util.HashMap;
import java.util.Map;

public class FindMatchesState {
    private Map<String, Double> matches;
    private String errorMessage;

    public FindMatchesState() {
        this.matches = new HashMap<>();
        this.errorMessage = "";
    }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public Map<String, Double> getMatches() { return matches; }

    public void setMatches(Map<String, Double> matches) { this.matches = matches; }
}
