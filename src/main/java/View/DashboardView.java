package View;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardViewModel;
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
                        controller.prepareNotificationView();
                    }
                }
        );

        matches.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.prepareFindMatchesView();
                    }
                }
        );

        announcements.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            controller.prepareAnnouncementView(dashboardViewModel.getState().getUsername());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
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
