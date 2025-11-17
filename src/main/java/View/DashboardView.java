package View;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

public class DashboardView {

    private static final String PROFILE_CARD = "Profiles";
    private static final String MATCHES_CARD = "Matches";
    private static final String ANNOUNCEMENTS_CARD = "Announcements";
    private static final String LOGOUT_CARD = "Logout";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardView::createAndShowUI);
    }

    private static void createAndShowUI() {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        CardLayout contentLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentLayout);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        contentPanel.add(makeContentPanel("Profile", "View and edit your profile."), PROFILE_CARD);
        contentPanel.add(makeContentPanel("Matches", "Search for new matches and manage current matches."), MATCHES_CARD);
        contentPanel.add(makeContentPanel("Announcements", "Read the latest announcements."), ANNOUNCEMENTS_CARD);
        contentPanel.add(makeContentPanel("Logout", "(This would trigger logout in the full app.)"), LOGOUT_CARD);

        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(230, 230, 230));
        navBar.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JButton profileButton = new JButton("Profiles");
        JButton matchesButton = new JButton("Matches");
        JButton announcementsButton = new JButton("Announcements");
        JButton logoutButton = new JButton("Logout");

        ActionListener switcher = e -> {
            String command = e.getActionCommand();
            if (PROFILE_CARD.equals(command)) {
                contentLayout.show(contentPanel, PROFILE_CARD);
            } else if (MATCHES_CARD.equals(command)) {
                contentLayout.show(contentPanel, MATCHES_CARD);
            } else if (ANNOUNCEMENTS_CARD.equals(command)) {
                contentLayout.show(contentPanel, ANNOUNCEMENTS_CARD);
            } else if (LOGOUT_CARD.equals(command)) {
                contentLayout.show(contentPanel, LOGOUT_CARD);
            }
        };

        profileButton.setActionCommand(PROFILE_CARD);
        matchesButton.setActionCommand(MATCHES_CARD);
        announcementsButton.setActionCommand(ANNOUNCEMENTS_CARD);
        logoutButton.setActionCommand(LOGOUT_CARD);

        profileButton.addActionListener(switcher);
        matchesButton.addActionListener(switcher);
        announcementsButton.addActionListener(switcher);
        logoutButton.addActionListener(switcher);

        Dimension tabSize = new Dimension(140, 32);
        profileButton.setPreferredSize(tabSize);
        matchesButton.setPreferredSize(tabSize);
        announcementsButton.setPreferredSize(tabSize);
        logoutButton.setPreferredSize(tabSize);

        navBar.add(profileButton);
        navBar.add(matchesButton);
        navBar.add(announcementsButton);
        navBar.add(logoutButton);

        frame.setLayout(new BorderLayout());
        frame.add(navBar, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static JPanel makeContentPanel(String title, String description) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(16f));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout(0, 8));
        textPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descriptionLabel, BorderLayout.CENTER);

        panel.add(textPanel, BorderLayout.NORTH);
        return panel;
    }
}