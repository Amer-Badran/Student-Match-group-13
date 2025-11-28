package View;

import Interface_Adapter.profile.ProfileController;
import Interface_Adapter.profile.ProfileState;
import Interface_Adapter.profile.ProfileViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProfileView extends JPanel implements ActionListener, PropertyChangeListener {

    private ProfileController controller;
    private final ProfileViewModel viewModel;

    private final JTextField firstNameField = new JTextField(15);
    private final JTextField lastNameField = new JTextField(15);
    private final JTextField countryField = new JTextField(15);
    private final JTextField emailField = new JTextField(15);
    private final JTextField phoneField = new JTextField(15);
    private final JTextArea bioArea = new JTextArea(6, 15);

    private final JLabel errorLabel = new JLabel();

    private final JButton saveButton = new JButton("Next: Preferences");

    public ProfileView(ProfileViewModel viewModel) {
        this.controller = null;
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 24, 24, 24));

        final JLabel title = new JLabel("Profile Creation: General");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final Dimension fieldSize = new Dimension(260, 30);
        configureField(firstNameField, fieldSize);
        configureField(lastNameField, fieldSize);
        configureField(countryField, fieldSize);
        configureField(emailField, fieldSize);
        configureField(phoneField, fieldSize);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        bioArea.setPreferredSize(new Dimension(260, 120));
        bioArea.setMaximumSize(new Dimension(260, 160));

        add(title);
        add(Box.createVerticalStrut(24));
        add(labeledField("First Name", firstNameField));
        add(Box.createVerticalStrut(10));
        add(labeledField("Last Name", lastNameField));
        add(Box.createVerticalStrut(10));
        add(labeledField("Country of Origin", countryField));
        add(Box.createVerticalStrut(10));
        add(labeledField("Email", emailField));
        add(Box.createVerticalStrut(10));
        add(labeledField("Phone Number", phoneField));
        add(Box.createVerticalStrut(10));
        add(labeledArea("Bio", bioArea));
        add(Box.createVerticalStrut(16));

        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setPreferredSize(new Dimension(180, 36));
        saveButton.setMaximumSize(new Dimension(200, 40));
        saveButton.addActionListener(this);
        add(saveButton);

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(8));
        add(errorLabel);
    }

    private void configureField(JTextField field, Dimension size) {
        field.setPreferredSize(size);
        field.setMaximumSize(size);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel labeledField(String label, JTextField field) {
        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        final JLabel jLabel = new JLabel(label);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(jLabel);
        container.add(Box.createVerticalStrut(6));
        container.add(field);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        return container;
    }

    private JPanel labeledArea(String label, JTextArea area) {
        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        final JLabel jLabel = new JLabel(label);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(jLabel);
        container.add(Box.createVerticalStrut(6));
        final JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(260, 140));
        scrollPane.setMaximumSize(new Dimension(260, 180));
        container.add(scrollPane);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        return container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // username should be set in ProfileState after login
        String username = viewModel.getState().username;

        controller.execute(
                username,
                firstNameField.getText(),
                lastNameField.getText(),
                countryField.getText(),
                bioArea.getText(),
                emailField.getText(),
                phoneField.getText()
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ProfileState state = (ProfileState) evt.getNewValue();

        errorLabel.setText(state.errorMessage);

        firstNameField.setText(state.firstName);
        lastNameField.setText(state.lastName);
        countryField.setText(state.countryOfOrigin);
        emailField.setText(state.email);
        phoneField.setText(state.phone);
        bioArea.setText(state.bio);
    }
    public void setProfileController(ProfileController controller) {
        this.controller = controller;
    }
}
