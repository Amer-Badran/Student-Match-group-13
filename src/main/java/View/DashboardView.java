package View;

import Interface_Adapter.dashboard.DashboardController;
import Interface_Adapter.dashboard.DashboardViewModel;
import Interface_Adapter.welcome.WelcomeController;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DashboardView extends JPanel {
    private final JButton messages;
    private final JButton matches;
    private final JButton announcements;
    private DashboardViewModel dashboardViewModels;
    private DashboardController controller;


    public DashboardView(DashboardViewModel dashboardViewModel){
        this.dashboardViewModels = dashboardViewModel;
        this.controller  = null;
        messages = new JButton("messages");
        matches = new JButton("matches");
        announcements = new JButton("announcements");


        messages.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                }
        );





        JPanel buttons = new JPanel();
        buttons.add(messages);
        buttons.add(matches);
        buttons.add(announcements);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.CYAN);

        this.add(buttons);


    }

    public void setDashboardController(DashboardController controller) {
        this.controller = controller;
    }



}
