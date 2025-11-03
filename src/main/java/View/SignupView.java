package View;

import Interface_Adapter.signup.SignupViewModel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener,PropertyChangeListener {
    private final String viewName = "Sign up";
    private final SignupViewModel siGnupViewModel;

    private final JButton signUp;
    private final JButton cancel;
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

//        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
//        this.add(repeatPasswordInfo);
        this.add(buttons);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public String getViewName() {
        return viewName;
    }

}
