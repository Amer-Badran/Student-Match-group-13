package View;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class LoginView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Login";
    private final LoginViewModel loginViewModels;
    private LoginController loginControllers = null;
    private final JButton login;
    private final JButton cancel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    public LoginView(LoginViewModel loginViewModel){
        this.loginViewModels = loginViewModel;
        loginViewModels.addPropertyChangeListener(this);

        JPanel userNameInfo = new JPanel();
            userNameInfo.add(new JLabel("Login user name : "));
            userNameInfo.add(usernameInputField);
        final JPanel passwordInfo = new JPanel();
        passwordInfo.add(new JLabel("Login Password here :"));
        passwordInfo.add(passwordInputField);
        final JPanel buttons = new JPanel();
        login = new JButton("Login");
        buttons.add(login);
        cancel = new JButton("cancel");
        buttons.add(cancel);
        login.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final LoginState currentState = loginViewModels.getState();
                        currentState.setUsername(usernameInputField.getText());
                        currentState.setPassword(new String(passwordInputField.getPassword()));;
                        loginViewModels.setState(currentState);

                        try {
                            loginControllers.execute(
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
        this.add(userNameInfo);
        this.add(passwordInfo);
//        this.add(repeatPasswordInfo);
        this.add(buttons);

    }





    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
    }}
    public String getViewName() {
        return viewName;}
    public void setLoginController(LoginController controller) {
        this.loginControllers = controller;
    }
}
