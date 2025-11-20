package View;

import Entity.Profile;
import Interface_Adapter.EnterInfo.EnterInfoController;
import Interface_Adapter.EnterInfo.EnterInfoViewModel;
import Interface_Adapter.login.LoginController;
import Interface_Adapter.profile.ProfileController;
import Interface_Adapter.profile.ProfileState;
import Interface_Adapter.profile.ProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterInfoView extends JPanel implements ActionListener, PropertyChangeListener {

    private EnterInfoController controller;
    private final EnterInfoViewModel viewModel;

    private final JTextField nameField = new JTextField(20);


    private final JLabel errorLabel = new JLabel();
    private final JLabel infoLabel = new JLabel();

    private final JButton saveButton = new JButton("Save profile");

    public EnterInfoView(EnterInfoViewModel viewModel) {
        this.controller = null;
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new GridLayout(0, 2));

        add(new JLabel("Full name:"));
        add(nameField);

        add(saveButton);
        add(errorLabel);
        add(infoLabel);

        saveButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // username should be set in EnterInfo after Profile
        //String username = viewModel.getState().username;

        controller.execute(
                nameField.getText(), // EXAMPLE : getting the Name from the name field E
                new ArrayList<String>(),
                new ArrayList<String>(),
                new Integer(0),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new HashMap<String,Double>()

        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ProfileState state = (ProfileState) evt.getNewValue();

        errorLabel.setText(state.errorMessage);
        infoLabel.setText(state.infoMessage);

        nameField.setText(state.name);
//        countryField.setText(state.countryOfOrigin);
//        emailField.setText(state.email);
//        instagramField.setText(state.instagram);
//        phoneField.setText(state.phone);
//        bioArea.setText(state.bio);
    }
    public void setEnterInfoController(EnterInfoController controller) {
        this.controller = controller;
    }
}
