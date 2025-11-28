package View;

import Interface_Adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View Manager for the program. It listens for property change events
 * in the ViewManagerModel and updates which View should be visible.
 */
public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;
    private final ViewManagerModel viewManagerModel;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.views = views;
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final String viewModelName = (String) evt.getNewValue();
            cardLayout.show(views, viewModelName);
            resizeForView(viewModelName);
        }
    }

    private void resizeForView(String viewModelName) {
        final Window window = SwingUtilities.getWindowAncestor(views);
        if (window instanceof JFrame) {
            final Dimension targetSize;
            if ("Login".equals(viewModelName) || "Sign up".equals(viewModelName)) {
                targetSize = new Dimension(360, 520);
            } else if ("profile".equals(viewModelName)) {
                targetSize = new Dimension(420, 640);
            } else {
                targetSize = new Dimension(480, 720);
            }
            window.setSize(targetSize);
            window.setLocationRelativeTo(null);
        }
    }
}