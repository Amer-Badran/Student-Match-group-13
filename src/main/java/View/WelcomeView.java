package View;

import Interface_Adapter.welcome.WelcomeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WelcomeView extends JPanel {
    private final String viewName = "welcome";
    private WelcomeController welcomeController = null;
    private final JButton signupButton;
    private final JButton loginButton;


    public void setWelcomeControllerController(WelcomeController controller) {
        this.welcomeController = controller;
    }

    public String getViewName(){
        return viewName;
    }

    public WelcomeView(){

        final JLabel title = new JLabel("UofT Matcher");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 40f));

        loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFont(loginButton.getFont().deriveFont(Font.PLAIN, 26f));

        signupButton = new JButton("Sign up");
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setFont(signupButton.getFont().deriveFont(Font.PLAIN, 26f));

        final Dimension buttonSize = new Dimension(360, 180);
        loginButton.setPreferredSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        signupButton.setPreferredSize(buttonSize);
        signupButton.setMaximumSize(buttonSize);

        loginButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        welcomeController.switchToLoginView();}}
        );

        signupButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        welcomeController.switchToSignupView();}}
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(500, 900));
        this.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        this.add(title);
        this.add(Box.createVerticalStrut(80));
        this.add(loginButton);
        this.add(Box.createVerticalStrut(50));
        this.add(signupButton);
        this.add(Box.createVerticalGlue());
    }


//    @Override
//    public void actionPerformed(ActionEvent evt) {
//        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
//    }
}
