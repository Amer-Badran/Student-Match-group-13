package Interface_Adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// the importance of this class is that it Creates the concept of STATES
// It also sets up the Infrastructure to Notify other about changes to the STATE
// and makes the method that makes another object ( THE VIEW MANAGER ) into a Listener to these changes


public class ViewModel<T> {

    private final String viewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this); // the support object help with notifying objects about cha
                                                                                             // changes and puts the source as this view model

    // note that all the other view models have this exact same method. meaning that support object is the same one througout this program.

    private T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    public T getState() {
        return this.state;
    }

    public void setState(T state) {
        this.state = state;
    }

    /**
     * Fires a property changed event for the state of this ViewModel.
     */
    public void firePropertyChange() {
        this.support.firePropertyChange("state", null, this.state);
    }

    /**
     * Fires a property changed event for the state of this ViewModel, which
     * allows the user to specify a different propertyName. This can be useful
     * when a class is listening for multiple kinds of property changes.
     * <p/>
     * For example, the LoggedInView listens for two kinds of property changes;
     * it can use the property name to distinguish which property has changed.
     * @param propertyName the label for the property that was changed
     */
    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    /**
     * Adds a PropertyChangeListener to this ViewModel.
     * @param listener The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }


}
