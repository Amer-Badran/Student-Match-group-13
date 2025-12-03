package View;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class DashboardView extends JPanel implements PropertyChangeListener {
    private final JButton messages;
    private final JButton matches;
    private final JButton announcements;
    private final JButton logout;
    private DashboardViewModel dashboardViewModels;
    private final LogoutViewModel logoutViewModel;
    private DashboardController controller;
    private LogoutController logoutController;


    public DashboardView(DashboardViewModel dashboardViewModel, LogoutViewModel logoutViewModel){
        this.dashboardViewModels = dashboardViewModel;
        this.logoutViewModel = logoutViewModel;
        this.logoutViewModel.addPropertyChangeListener(this);
        this.controller  = null;
        messages = new JButton("messages");
        matches = new JButton("matches");
        announcements = new JButton("announcements");
        logout = new JButton("logout");


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

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    logoutController.execute(dashboardViewModels.getState().getUsername());
                } catch (IOException | ParseException ex) {
                    JOptionPane.showMessageDialog(DashboardView.this, "Unable to logout: " + ex.getMessage());
                }
            }
        });

        JPanel buttons = new JPanel();
        buttons.add(messages);
        buttons.add(matches);
        buttons.add(announcements);
        buttons.add(logout);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.CYAN);

        this.add(buttons);


    }

    public void setDashboardController(DashboardController controller) {
        this.controller = controller;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();
        if (newValue instanceof LogoutState) {
            LogoutState state = (LogoutState) newValue;
            if (state.isSuccess()) {
                JOptionPane.showMessageDialog(this, state.getMessage());
            } else if (!state.getErrorMessage().isEmpty()) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage());
            }
        }
    }



}
