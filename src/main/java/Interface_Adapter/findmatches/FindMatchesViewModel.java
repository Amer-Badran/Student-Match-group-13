package Interface_Adapter.findmatches;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

public class FindMatchesViewModel {

    private Map<String, Double> matches;
    private String errorMessage;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void setMatches(Map<String, Double> matches) {
        this.matches = matches;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Double> getMatches() {
        return matches;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("viewModel", null, this);
    }
}