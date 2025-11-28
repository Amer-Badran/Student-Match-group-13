package View;

import Interface_Adapter.Notification.NotificationController;
import Interface_Adapter.Notification.NotificationState;
import Interface_Adapter.Notification.NotificationViewModel;
import Interface_Adapter.profile.ProfileController;
import Interface_Adapter.signup.SignupState;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class NotificationView extends JPanel implements ActionListener, PropertyChangeListener {
    private JTextField textField;
    private JButton search;
    private JLabel infoLabel;
    private NotificationViewModel notificationViewModel;
    private NotificationController controller;
    JPanel notificationPanel = new JPanel();


    public NotificationView(NotificationViewModel notificationViewModels) throws IOException, ParseException {
        this.notificationViewModel = notificationViewModels;
        notificationViewModel.addPropertyChangeListener(this);
        JPanel interactions = new JPanel();
        controller = null;

        ArrayList<String> peopleNotification =   notificationViewModel.getState().getNotification();
        if(peopleNotification==null || peopleNotification.isEmpty() ){
            JLabel tempLabel = new JLabel("No new messages yet !");
            notificationPanel.add(tempLabel); }
        else{
        for (String person : peopleNotification) {
            JLabel tempLabel = new JLabel(person);
            notificationPanel.add(tempLabel);
        }}
        search = new JButton("search");
        textField = new JTextField(15);
        infoLabel = new JLabel("Who do you want to chat with ?");

        interactions.add(infoLabel);
        interactions.add(textField);
        interactions.add(search);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

//        this.add(title);
        this.add(notificationPanel);
        this.add(interactions);


        search.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String other = textField.getText();
                        String Sender = notificationViewModel.getState().getUsername();
                        try {
                            controller.execute(Sender,other);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }



                    }
                }
        );

        ;





    }







    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final NotificationState state = (NotificationState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            state.setError("");}

        // Clear old notifications
        notificationPanel.removeAll();

        // Add new notifications
        ArrayList<String> peopleNotification = state.getNotification();
        if (peopleNotification == null || peopleNotification.isEmpty()) {
            notificationPanel.add(new JLabel("No new messages yet !"));
        } else {
            for (String person : peopleNotification) {
                notificationPanel.add(new JLabel(person));
            }
        }

        // Refresh panel
        notificationPanel.revalidate();
        notificationPanel.repaint();


    }
    public void setNotificationController(NotificationController controller) {
        this.controller = controller;
    }

}
