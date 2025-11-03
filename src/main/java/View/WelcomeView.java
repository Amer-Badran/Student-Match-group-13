package View;

import Interface_Adapter.welcome.WelcomeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WelcomeView extends JPanel {
    private final String viewName = "welcome";
    private WelcomeController welcomeController = null;
    private final JButton TosignUp;
    private final JButton cancel;
    private final JButton ToLogin;


    public void setWelcomeControllerController(WelcomeController controller) {
        this.welcomeController = controller;
    }

    public String getViewName(){
        return viewName;
    }

    public WelcomeView(){

        final JLabel title = new JLabel("Hello dear Student");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel buttons = new JPanel();
        buttons.setBackground(Color.CYAN);
        ToLogin = new JButton("I have an account");
        ToLogin.setForeground(Color.GREEN);
        buttons.add(ToLogin);
        TosignUp = new JButton("New Account");
        TosignUp.setForeground(Color.RED);
        buttons.add(TosignUp);
        cancel = new JButton("Cancel now!");
        cancel.setForeground(Color.BLACK);
        buttons.add(cancel);

        ToLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        welcomeController.switchToLoginView();}}  // to be implemented later
        );
        TosignUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        welcomeController.switchToSignupView();}}
        );


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.CYAN);

        this.add(title);
        this.add(buttons);
    }


//    @Override
//    public void actionPerformed(ActionEvent evt) {
//        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
//    }
}
