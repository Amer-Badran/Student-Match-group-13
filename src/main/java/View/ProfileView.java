package View;

import Interface_Adapter.profile.ProfileController;
import Interface_Adapter.profile.ProfileViewModel;
import Interface_Adapter.profile.ProfileState;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class ProfileView extends JPanel implements ActionListener, PropertyChangeListener {

    private final ProfileController controller;
    private final ProfileViewModel viewModel;

    private final JTextField nameField = new JTextField(20);
    private final JTextField nationalityField = new JTextField(20);
    private final JTextField languagesField = new JTextField(20);
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

        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("Nationality:"));
        add(nationalityField);

        add(new JLabel("Languages:"));
        add(languagesField);

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
        String userId = viewModel.getState().userId;

        try {
            controller.execute(
                    userId,
                    nameField.getText(),
                    nationalityField.getText(),
                    bioArea.getText(),
                    languagesField.getText(),
                    emailField.getText(),
                    instagramField.getText(),
                    phoneField.getText()
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ProfileState state = (ProfileState) evt.getNewValue();

        errorLabel.setText(state.errorMessage);
        infoLabel.setText(state.infoMessage);

        nameField.setText(state.name);
        nationalityField.setText(state.nationality);
        languagesField.setText(state.languages);
        emailField.setText(state.email);
        instagramField.setText(state.instagram);
        phoneField.setText(state.phone);
        bioArea.setText(state.bio);
    }
}
