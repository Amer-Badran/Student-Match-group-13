package View;

import Interface_Adapter.signup.SignupController;
import Interface_Adapter.signup.SignupState;
import Interface_Adapter.signup.SignupViewModel;
import org.json.simple.parser.ParseException;

import java.awt.*;
import javax.swing.*;
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

        final JPanel usernameInfo = new JPanel();
               usernameInfo.add(new JLabel("User name here : "));
               usernameInfo.add(usernameInputField);
        final JPanel passwordInfo = new JPanel();
                passwordInfo.add(new JLabel("Password here :"));
                passwordInfo.add(passwordInputField);

        final JPanel buttons = new JPanel();
        signUp = new JButton("Sign up");
        buttons.add(signUp);
        cancel = new JButton("cancel");
        buttons.add(cancel);
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

//        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
//        this.add(repeatPasswordInfo);
        this.add(buttons);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());

    }}

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
