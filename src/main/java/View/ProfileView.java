package View;

import Interface_Adapter.profile.ProfileController;
import Interface_Adapter.profile.ProfileState;
import Interface_Adapter.profile.ProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProfileView extends JPanel implements ActionListener, PropertyChangeListener {

    private final ProfileController controller;
    private final ProfileViewModel viewModel;

    private final JTextField nameField = new JTextField(20);
    private final JTextField countryField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField instagramField = new JTextField(20);
    private final JTextField phoneField = new JTextField(20);
    private final JTextArea bioArea = new JTextArea(4, 20);

    private final JLabel errorLabel = new JLabel();
    private final JLabel infoLabel = new JLabel();

    private final JButton saveButton = new JButton("Save profile");

    public ProfileView(ProfileController controller, ProfileViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new GridLayout(0, 2));

        add(new JLabel("Full name:"));
        add(nameField);

        add(new JLabel("Country of origin:"));
        add(countryField);

        add(new JLabel("Email:"));
        add(emailField);

        add(new JLabel("Instagram:"));
        add(instagramField);

        add(new JLabel("Phone:"));
        add(phoneField);

        add(new JLabel("Bio:"));
        add(new JScrollPane(bioArea));

        add(saveButton);
        add(errorLabel);
        add(infoLabel);

        saveButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // username should be set in ProfileState after login
        String username = viewModel.getState().username;

        controller.execute(
                username,
                nameField.getText(),
                countryField.getText(),
                bioArea.getText(),
                emailField.getText(),
                instagramField.getText(),
                phoneField.getText()
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ProfileState state = (ProfileState) evt.getNewValue();

        errorLabel.setText(state.errorMessage);
        infoLabel.setText(state.infoMessage);

        nameField.setText(state.name);
        countryField.setText(state.countryOfOrigin);
        emailField.setText(state.email);
        instagramField.setText(state.instagram);
        phoneField.setText(state.phone);
        bioArea.setText(state.bio);
    }
}
