package View;

import Interface_Adapter.login.LoginController;
import Interface_Adapter.login.LoginState;
import Interface_Adapter.login.LoginViewModel;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

        final Dimension fieldSize = new Dimension(240, 30);
        usernameInputField.setPreferredSize(fieldSize);
        usernameInputField.setMaximumSize(fieldSize);
        passwordInputField.setPreferredSize(fieldSize);
        passwordInputField.setMaximumSize(fieldSize);
        usernameInputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordInputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(16, 20, 20, 20));

        final JLabel title = new JLabel("Login");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel userNameInfo = new JPanel();
        userNameInfo.setLayout(new BoxLayout(userNameInfo, BoxLayout.Y_AXIS));
        userNameInfo.setOpaque(false);
        userNameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameInfo.add(usernameLabel);
        userNameInfo.add(Box.createVerticalStrut(6));
        userNameInfo.add(usernameInputField);

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
        login = new JButton("Login");
        login.setPreferredSize(new Dimension(110, 34));
        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(110, 34));
        buttons.add(login);
        buttons.add(cancel);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        cancel.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (loginControllers != null) {
                            usernameInputField.setText("");
                            passwordInputField.setText("");
                            loginControllers.backToWelcomeView();
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
        content.add(userNameInfo);
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
        final LoginState state = (LoginState) evt.getNewValue();
        if (state.getError() != null && !state.getError().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
    }
    public String getViewName() {
        return viewName;}
    public void setLoginController(LoginController controller) {
        this.loginControllers = controller;
    }
}
