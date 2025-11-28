package View;

import Interface_Adapter.signup.SignupController;
import Interface_Adapter.signup.SignupState;
import Interface_Adapter.signup.SignupViewModel;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class SignupView extends JPanel implements ActionListener,PropertyChangeListener {
    private final String viewName = "Sign up";
    private final SignupViewModel siGnupViewModel;

    private final JButton signUp;
    private final JButton cancel;
    private SignupController signupControllers = null;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);

    public SignupView(SignupViewModel signupViewModel) {
        this.siGnupViewModel = signupViewModel;
        siGnupViewModel.addPropertyChangeListener(this);  // this means that this class now listens to
                                                          // changes to the State of Sign up
                                                          // Because it needs to know when an Error pops up
                                                          // The Presenter will reflect the error by Changing the state ( or
                                                          // The error parameter in the state ) and the View needs to be alerted to these changes

        final Dimension fieldSize = new Dimension(240, 30);
        usernameInputField.setPreferredSize(fieldSize);
        usernameInputField.setMaximumSize(fieldSize);
        passwordInputField.setPreferredSize(fieldSize);
        passwordInputField.setMaximumSize(fieldSize);
        usernameInputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordInputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(16, 20, 20, 20));

        final JLabel title = new JLabel("Sign Up");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel usernameInfo = new JPanel();
        usernameInfo.setLayout(new BoxLayout(usernameInfo, BoxLayout.Y_AXIS));
        usernameInfo.setOpaque(false);
        usernameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameInfo.add(usernameLabel);
        usernameInfo.add(Box.createVerticalStrut(6));
        usernameInfo.add(usernameInputField);

        final JPanel passwordInfo = new JPanel();
        passwordInfo.setLayout(new BoxLayout(passwordInfo, BoxLayout.Y_AXIS));
        passwordInfo.setOpaque(false);
        passwordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordInfo.add(passwordLabel);
        passwordInfo.add(Box.createVerticalStrut(6));
        passwordInfo.add(passwordInputField);

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setOpaque(false);
        signUp = new JButton("Sign up");
        signUp.setPreferredSize(new Dimension(110, 34));
        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(110, 34));
        buttons.add(signUp);
        buttons.add(cancel);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUp.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final SignupState currentState = signupViewModel.getState();
                        currentState.setUsername(usernameInputField.getText());
                        currentState.setPassword(new String(passwordInputField.getPassword()));;
                        signupViewModel.setState(currentState);

                        try {
                            signupControllers.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }


                    }
                }
        );

        cancel.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (signupControllers != null) {
                            usernameInputField.setText("");
                            passwordInputField.setText("");
                            signupControllers.switchToWelcomeView();
                        }
                    }
                }
        );

        final JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(22));
        content.add(usernameInfo);
        content.add(Box.createVerticalStrut(12));
        content.add(passwordInfo);
        content.add(Box.createVerticalStrut(18));
        content.add(buttons);

        add(Box.createVerticalGlue());
        add(content);
        add(Box.createVerticalGlue());

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getError() != null && !state.getError().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getError());
        }

        if (state.getSuccessMessage() != null && !state.getSuccessMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage());
            usernameInputField.setText("");
            passwordInputField.setText("");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public String getViewName() {
        return viewName;
    }
    public void setSignupController(SignupController controller) {
        this.signupControllers = controller;
    }

}
