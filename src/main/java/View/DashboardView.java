package View;

import Entity.Announcement;
import Use_Case.announcements.AnnouncementDataAccessObject;
import Use_Case.announcements.AnnouncementInputData;
import Use_Case.announcements.AnnouncementInteractor;
import Interface_Adapter.Chat.ChatController;
import Interface_Adapter.Chat.ChatPresenter;
import Interface_Adapter.Chat.ChatState;
import Interface_Adapter.Chat.ChatViewModel;
import Interface_Adapter.Notification.NotificationController;
import Interface_Adapter.Notification.NotificationPresenter;
import Interface_Adapter.Notification.NotificationState;
import Interface_Adapter.Notification.NotificationViewModel;
import Interface_Adapter.ViewManagerModel;
import Use_Case.Chat.ChatInputBoundary;
import Use_Case.Chat.ChatInteractor;
import Use_Case.Notification.NotificationInputBoundary;
import Use_Case.Notification.NotificationInteractor;
import Use_Case.announcements.AnnouncementOutputBoundary;
import Use_Case.announcements.AnnouncementOutputData;
import Use_Case.findmatches.FindMatchesDataAccessObject;
import Use_Case.findmatches.FindMatchesInputBoundary;
import Use_Case.findmatches.FindMatchesInputData;
import Use_Case.findmatches.FindMatchesInteractor;
import Use_Case.findmatches.FindMatchesOutputBoundary;
import Use_Case.findmatches.FindMatchesOutputData;
import Use_Case.matchingstrategy.WeightedMatchingAlgorithm;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.text.DecimalFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.AppBuilder;
import Data_Access.JSONDataObject;
import Entity.Client;
import Entity.MatchingPreferences;
import Entity.Profile;

public class DashboardView {

    private static final String PROFILE_CARD = "Profiles";
    private static final String MATCHES_CARD = "Matches";
    private static final String ANNOUNCEMENTS_CARD = "Announcements";
    private static final String SEARCH_CARD = "Search";
    private static final String DEFAULT_USER = "Guest";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowUI(DEFAULT_USER));
    }

    public static void showDashboard() {
        showDashboard(DEFAULT_USER);
    }

    public static void showDashboard(String username) {
        SwingUtilities.invokeLater(() -> createAndShowUI(username == null || username.isEmpty() ? DEFAULT_USER : username));
    }

    private static void createAndShowUI(String username) {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setLocationRelativeTo(null);

        CardLayout contentLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentLayout);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        MessagesPanel messagesPanel = makeMessagesPanel(username, contentLayout, contentPanel);

        contentPanel.add(makeProfilePanel(username, frame), PROFILE_CARD);
        contentPanel.add(messagesPanel, MATCHES_CARD);
        contentPanel.add(makeSearchPanel(username, messagesPanel, contentLayout, contentPanel), SEARCH_CARD);
        contentPanel.add(makeAnnouncementsPanel(username), ANNOUNCEMENTS_CARD);

        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(230, 230, 230));
        navBar.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JButton profileButton = new JButton("Profiles");
        JButton matchesButton = new JButton("Messages");
        JButton searchButton = new JButton("Search");
        JButton announcementsButton = new JButton("Announcements");
        JButton logoutButton = new JButton("Logout");

        ActionListener switcher = e -> {
            String command = e.getActionCommand();
            if (PROFILE_CARD.equals(command)) {
                contentLayout.show(contentPanel, PROFILE_CARD);
            } else if (MATCHES_CARD.equals(command)) {
                contentLayout.show(contentPanel, MATCHES_CARD);
            } else if (SEARCH_CARD.equals(command)) {
                contentLayout.show(contentPanel, SEARCH_CARD);
            } else if (ANNOUNCEMENTS_CARD.equals(command)) {
                contentLayout.show(contentPanel, ANNOUNCEMENTS_CARD);
            }
        };

        profileButton.setActionCommand(PROFILE_CARD);
        matchesButton.setActionCommand(MATCHES_CARD);
        searchButton.setActionCommand(SEARCH_CARD);
        announcementsButton.setActionCommand(ANNOUNCEMENTS_CARD);
        profileButton.addActionListener(switcher);
        matchesButton.addActionListener(switcher);
        searchButton.addActionListener(switcher);
        announcementsButton.addActionListener(switcher);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout(frame);
            }
        });

        Dimension tabSize = new Dimension(140, 32);
        profileButton.setPreferredSize(tabSize);
        matchesButton.setPreferredSize(tabSize);
        searchButton.setPreferredSize(tabSize);
        announcementsButton.setPreferredSize(tabSize);
        logoutButton.setPreferredSize(tabSize);

        navBar.add(profileButton);
        navBar.add(matchesButton);
        navBar.add(searchButton);
        navBar.add(announcementsButton);
        navBar.add(logoutButton);

        frame.setLayout(new BorderLayout());
        frame.add(navBar, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static JPanel makeProfilePanel(String username, JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Profile");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));

        JLabel descriptionLabel = new JLabel("View and edit your profile.");
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(16f));

        JPanel textPanel = new JPanel(new BorderLayout(0, 8));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descriptionLabel, BorderLayout.CENTER);

        JButton editButton = new JButton("Edit Profile");
        JButton deleteButton = new JButton("Delete Account");

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.add(editButton);
        buttons.add(Box.createVerticalStrut(8));
        buttons.add(deleteButton);

        editButton.addActionListener(e -> openEditProfileDialog(frame, username));
        deleteButton.addActionListener(e -> handleDeleteAccount(frame, username));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        header.setOpaque(false);
        header.add(textPanel, BorderLayout.WEST);
        header.add(buttons, BorderLayout.EAST);

        ProfileDisplayData displayData = loadProfileDisplayData(username);

        JPanel columns = new JPanel(new GridLayout(1, 2, 12, 0));
        columns.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        columns.setOpaque(false);
        columns.add(buildGeneralInfoPanel(displayData));
        columns.add(buildPreferencesPanel(displayData));

        panel.add(header, BorderLayout.NORTH);
        panel.add(columns, BorderLayout.CENTER);

        if (displayData.errorMessage != null && !displayData.errorMessage.isEmpty()) {
            JLabel errorLabel = new JLabel(displayData.errorMessage);
            errorLabel.setForeground(Color.RED.darker());
            errorLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
            panel.add(errorLabel, BorderLayout.SOUTH);
        }

        return panel;
    }

    private static JPanel buildGeneralInfoPanel(ProfileDisplayData data) {
        JPanel section = createSectionPanel("General Information");

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addStackedValue(grid, gbc, "First Name:", data.firstName);
        addStackedValue(grid, gbc, "Last Name:", data.lastName);
        addStackedValue(grid, gbc, "Email:", data.email);
        addStackedValue(grid, gbc, "Phone Number:", data.phone);
        addStackedValue(grid, gbc, "Country of Origin:", data.countryOfOrigin);

        JLabel bioLabel = new JLabel("Bio:");
        bioLabel.setFont(bioLabel.getFont().deriveFont(Font.BOLD, 14f));
        gbc.gridx = 0;
        gbc.gridy++;
        grid.add(bioLabel, gbc);

        JTextArea bioArea = new JTextArea(data.bio.isEmpty() ? "—" : data.bio);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setEditable(false);
        bioArea.setFont(bioArea.getFont().deriveFont(14f));
        bioArea.setOpaque(false);
        bioArea.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        bioArea.setRows(5);
        bioArea.setColumns(28);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        grid.add(bioArea, gbc);

        section.add(grid, BorderLayout.CENTER);
        return section;
    }

    private static JPanel buildPreferencesPanel(ProfileDisplayData data) {
        JPanel section = createSectionPanel("Matching Preferences");

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addStackedValue(grid, gbc, "Year of Study:", data.yearOfStudy);
        addProgramsList(grid, gbc, "Programs:", data.programLines);
        addStackedValue(grid, gbc, "Courses:", data.courses);
        addStackedValue(grid, gbc, "Languages:", data.languages);
        addStackedValue(grid, gbc, "Hobbies:", data.hobbies);

        section.add(grid, BorderLayout.CENTER);
        return section;
    }

    private static void addStackedValue(JPanel grid, GridBagConstraints gbc, String labelText, String valueText) {
        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        grid.add(label, gbc);

        JLabel valueLabel = new JLabel(valueText.isEmpty() ? "—" : valueText);
        valueLabel.setFont(valueLabel.getFont().deriveFont(14f));
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        grid.add(valueLabel, gbc);

        gbc.gridy++;
    }

    private static void addProgramsList(JPanel grid, GridBagConstraints gbc, String labelText, List<String> programs) {
        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        grid.add(label, gbc);

        if (programs == null || programs.isEmpty()) {
            JLabel placeholder = new JLabel("—");
            placeholder.setFont(placeholder.getFont().deriveFont(14f));
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            grid.add(placeholder, gbc);
            gbc.gridy++;
            return;
        }

        for (String program : programs) {
            JLabel programLabel = new JLabel(program);
            programLabel.setFont(programLabel.getFont().deriveFont(14f));
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            grid.add(programLabel, gbc);
        }

        gbc.gridy++;
    }

    private static JPanel createSectionPanel(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JLabel header = new JLabel(title, JLabel.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        section.add(header, BorderLayout.NORTH);
        return section;
    }

    private static ProfileDisplayData loadProfileDisplayData(String username) {
        ProfileDisplayData data = new ProfileDisplayData();
        try {
            JSONDataObject dao = new JSONDataObject();
            Client user = dao.getUserByUsername(username);
            if (user == null) {
                data.errorMessage = "Unable to load your profile details.";
                return data;
            }

            Profile profile = user.getProfile();
            if (profile != null) {
                data.firstName = safeValue(profile.getFirstName());
                data.lastName = safeValue(profile.getLastName());
                data.email = safeValue(profile.getEmail());
                data.phone = safeValue(profile.getPhone());
                data.countryOfOrigin = safeValue(profile.getCountryOfOrigin());
                data.bio = safeValue(profile.getBio());
            }

            MatchingPreferences preferences = user.getPreferences();
            if (preferences != null) {
                data.yearOfStudy = preferences.getYearOfStudy() > 0 ? String.valueOf(preferences.getYearOfStudy()) : "—";
                data.programLines = formatPrograms(preferences.getPrograms());
                data.courses = formatList(preferences.getCourses());
                data.languages = formatList(preferences.getLanguages());
                data.hobbies = formatList(preferences.getHobbies());
            }
        } catch (Exception e) {
            data.errorMessage = "Unable to load your profile details.";
        }
        return data;
    }

    private static String safeValue(String value) {
        return value == null ? "" : value;
    }

    private static String formatList(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "—";
        }
        return String.join(", ", values);
    }

    private static List<String> formatPrograms(Map<String, String> programs) {
        if (programs == null || programs.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> formatted = new ArrayList<>();
        for (Map.Entry<String, String> entry : programs.entrySet()) {
            formatted.add(entry.getKey() + " (" + entry.getValue() + ")");
        }
        Collections.sort(formatted);
        return formatted;
    }

    private static class ProfileDisplayData {
        String firstName = "";
        String lastName = "";
        String email = "";
        String phone = "";
        String countryOfOrigin = "";
        String bio = "";
        String yearOfStudy = "—";
        List<String> programLines = Collections.emptyList();
        String courses = "—";
        String languages = "—";
        String hobbies = "—";
        String errorMessage;
    }

    private static JPanel makeAnnouncementsPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Announcements");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));

        JLabel descriptionLabel = new JLabel("Read the latest announcements or post one.");
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(16f));

        JPanel header = new JPanel(new BorderLayout(0, 6));
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        header.setOpaque(false);
        header.add(titleLabel, BorderLayout.NORTH);
        header.add(descriptionLabel, BorderLayout.CENTER);

        JTextArea announcementsArea = new JTextArea();
        announcementsArea.setEditable(false);
        announcementsArea.setLineWrap(true);
        announcementsArea.setWrapStyleWord(true);
        announcementsArea.setFont(announcementsArea.getFont().deriveFont(15f));

        JScrollPane scrollPane = new JScrollPane(announcementsArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(230, 230, 230))));

        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Post");

        JPanel inputRow = new JPanel(new BorderLayout(8, 0));
        inputRow.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        inputRow.add(inputField, BorderLayout.CENTER);
        inputRow.add(sendButton, BorderLayout.EAST);

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputRow, BorderLayout.SOUTH);

        final AnnouncementInteractor[] interactorHolder = new AnnouncementInteractor[1];

        try {
            AnnouncementDataAccessObject dao = new AnnouncementDataAccessObject();
            AnnouncementOutputBoundary presenter = new AnnouncementOutputBoundary() {
                @Override
                public void prepareSuccessView(AnnouncementOutputData outputData) {
                    SwingUtilities.invokeLater(() -> {
                        inputField.setText("");
                        renderAnnouncements(announcementsArea, outputData.getAnnouncements());
                    });
                }

                @Override
                public void prepareFailView(String errorMessage) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(panel,
                            errorMessage,
                            "Announcements",
                            JOptionPane.ERROR_MESSAGE));
                }
            };

            interactorHolder[0] = new AnnouncementInteractor(dao, presenter);
            renderAnnouncements(announcementsArea, dao.loadAnnouncements());
        } catch (IOException e) {
            sendButton.setEnabled(false);
            inputField.setEnabled(false);
            JOptionPane.showMessageDialog(panel,
                    "Unable to load announcements: " + e.getMessage(),
                    "Announcements",
                    JOptionPane.ERROR_MESSAGE);
        }

        ActionListener sender = e -> {
            if (interactorHolder[0] != null) {
                interactorHolder[0].execute(new AnnouncementInputData(username, inputField.getText()));
            }
        };

        sendButton.addActionListener(sender);
        inputField.addActionListener(sender);

        return panel;
    }

    private static void handleLogout(JFrame frame) {
        if (frame != null) {
            frame.dispose();
        }

        try {
            AppBuilder appBuilder = new AppBuilder();
            JFrame application = appBuilder
                    .addWelcomeView()
                    .addWelcomeUseCase()
                    .addSignupView()
                    .addSignupUseCase()
                    .addLoginView()
                    .addLoginUseCase()
                    .addProfileView()
                    .addProfileUseCase()
                    .addEnterInfoView()
                    .addEnterInfoUseCase()
                    .build();
            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Unable to return to the welcome screen.",
                    "Logout failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void handleDeleteAccount(JFrame frame, String username) {
        Object[] options = {"Delete Account", "Cancel"};
        int choice = JOptionPane.showOptionDialog(frame,
                "Are you sure? This action is irreversible.",
                "Delete Account",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[1]);

        if (choice != 0) {
            return;
        }

        try {
            JSONDataObject dataObject = new JSONDataObject();
            boolean removed = dataObject.deleteUser(username);

            if (!removed) {
                JOptionPane.showMessageDialog(frame,
                        "Unable to delete account. Please try again.",
                        "Delete failed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame,
                    "Your account has been deleted.",
                    "Account deleted",
                    JOptionPane.INFORMATION_MESSAGE);
            handleLogout(frame);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame,
                    "Unable to delete account. Please try again.",
                    "Delete failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void renderAnnouncements(JTextArea target, List<Announcement> announcements) {
        StringBuilder builder = new StringBuilder();
        for (Announcement announcement : announcements) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(announcement.getUser()).append(": ").append(announcement.getMessage());
        }
        target.setText(builder.toString());
        target.setCaretPosition(target.getDocument().getLength());
    }

    private static JPanel makeSearchPanel(String username, MessagesPanel messagesPanel, CardLayout layout, JPanel contentPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Search");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));

        JLabel descriptionLabel = new JLabel("Find matches based on your preferences.");
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(16f));

        JPanel textPanel = new JPanel(new BorderLayout(0, 8));
        textPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descriptionLabel, BorderLayout.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));

        JPanel rectangle = new JPanel(new BorderLayout());
        rectangle.setOpaque(true);
        rectangle.setBackground(new Color(248, 248, 248));
        rectangle.setPreferredSize(new Dimension(0, 420));
        rectangle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JPanel boxes = new JPanel(new GridLayout(1, 2, 12, 0));
        boxes.setOpaque(false);

        JPanel resultsBox = new JPanel(new BorderLayout());
        resultsBox.setPreferredSize(new Dimension(320, 420));
        resultsBox.setBackground(Color.WHITE);
        resultsBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JLabel resultsLabel = new JLabel("Matches", JLabel.CENTER);
        resultsLabel.setFont(resultsLabel.getFont().deriveFont(Font.BOLD, 16f));
        resultsBox.add(resultsLabel, BorderLayout.NORTH);

        JPanel resultsList = new JPanel();
        resultsList.setOpaque(false);
        resultsList.setLayout(new BoxLayout(resultsList, BoxLayout.Y_AXIS));

        JScrollPane resultsScroll = new JScrollPane(resultsList);
        resultsScroll.setBorder(BorderFactory.createEmptyBorder());
        resultsScroll.getVerticalScrollBar().setUnitIncrement(16);
        resultsBox.add(resultsScroll, BorderLayout.CENTER);

        JLabel profileHeader = new JLabel("Profile", JLabel.CENTER);
        profileHeader.setFont(profileHeader.getFont().deriveFont(Font.BOLD, 16f));

        JPanel profileContent = new JPanel();
        profileContent.setLayout(new BoxLayout(profileContent, BoxLayout.Y_AXIS));
        profileContent.setOpaque(false);
        JLabel profilePlaceholder = new JLabel("Match profiles will be shown here.");
        profilePlaceholder.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileContent.add(Box.createVerticalGlue());
        profileContent.add(profilePlaceholder);
        profileContent.add(Box.createVerticalGlue());

        JScrollPane profileScroll = new JScrollPane(profileContent);
        profileScroll.setBorder(BorderFactory.createEmptyBorder());
        profileScroll.getVerticalScrollBar().setUnitIncrement(14);

        JPanel boxTwo = new JPanel(new BorderLayout());
        boxTwo.setPreferredSize(new Dimension(320, 260));
        boxTwo.setBackground(Color.WHITE);
        boxTwo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        boxTwo.add(profileHeader, BorderLayout.NORTH);
        boxTwo.add(profileScroll, BorderLayout.CENTER);

        boxes.add(resultsBox);
        boxes.add(boxTwo);

        JPanel controls = new JPanel(new GridBagLayout());
        controls.setOpaque(false);
        controls.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        JCheckBox defaultWeights = new JCheckBox("Use Default Weights");
        defaultWeights.setSelected(true);

        JButton searchButton = new JButton("Search");
        JButton changeWeightsButton = new JButton("Change Weights");
        changeWeightsButton.setVisible(false);

        JPanel buttonsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonsRow.setOpaque(false);
        buttonsRow.add(searchButton);
        buttonsRow.add(changeWeightsButton);

        JPanel rightSpacer = new JPanel();
        rightSpacer.setOpaque(false);
        rightSpacer.setPreferredSize(defaultWeights.getPreferredSize());

        defaultWeights.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean useDefault = e.getStateChange() == ItemEvent.SELECTED;
                changeWeightsButton.setVisible(!useDefault);
                if (useDefault) {
                    applyDefaultWeights(username, panel);
                }
                controls.revalidate();
                controls.repaint();
            }
        });

        changeWeightsButton.addActionListener(e -> openWeightsDialog(username, panel));

        GridBagConstraints left = new GridBagConstraints();
        left.gridx = 0;
        left.gridy = 0;
        left.insets = new Insets(0, 12, 0, 12);
        left.anchor = GridBagConstraints.WEST;

        GridBagConstraints center = new GridBagConstraints();
        center.gridx = 1;
        center.gridy = 0;
        center.weightx = 1.0;
        center.anchor = GridBagConstraints.CENTER;

        GridBagConstraints right = new GridBagConstraints();
        right.gridx = 2;
        right.gridy = 0;
        right.insets = new Insets(0, 12, 0, 12);

        controls.add(defaultWeights, left);
        controls.add(buttonsRow, center);
        controls.add(rightSpacer, right);

        rectangle.add(boxes, BorderLayout.CENTER);
        rectangle.add(controls, BorderLayout.SOUTH);

        container.add(rectangle, BorderLayout.CENTER);

        panel.add(textPanel, BorderLayout.NORTH);
        panel.add(container, BorderLayout.CENTER);

        searchButton.addActionListener(e -> performSearch(username, resultsList, profileHeader, profileContent, panel, messagesPanel, layout, contentPanel));

        return panel;
    }

    private static Map<String, Double> defaultWeights() {
        Map<String, Double> defaults = new LinkedHashMap<>();
        defaults.put("Courses", 0.35);
        defaults.put("Programs", 0.25);
        defaults.put("YearOfStudy", 0.20);
        defaults.put("Languages", 0.12);
        defaults.put("Hobbies", 0.08);
        return defaults;
    }

    private static void applyDefaultWeights(String username, Component parent) {
        persistWeights(username, defaultWeights(), parent, false);
    }

    private static void openWeightsDialog(String username, Component parent) {
        Map<String, Double> currentWeights = loadUserWeights(username, parent);
        if (currentWeights == null) {
            return;
        }

        Window owner = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(owner, "Adjust Weights", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout(12, 12));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);

        JLabel title = new JLabel("Adjust Weights", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        dialog.add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        Map<String, JTextField> fields = new LinkedHashMap<>();
        DecimalFormat df = new DecimalFormat("0.00");
        for (Map.Entry<String, Double> entry : currentWeights.entrySet()) {
            JLabel label = new JLabel(entry.getKey() + ":");
            JTextField field = new JTextField(df.format(entry.getValue()), 6);
            fields.put(entry.getKey(), field);

            gbc.gridx = 0;
            content.add(label, gbc);
            gbc.gridx = 1;
            content.add(field, gbc);
            gbc.gridy++;
        }

        JButton resetButton = new JButton("Reset to Default");
        resetButton.addActionListener(e -> {
            Map<String, Double> defaults = defaultWeights();
            for (Map.Entry<String, JTextField> entry : fields.entrySet()) {
                Double value = defaults.get(entry.getKey());
                entry.getValue().setText(df.format(value));
            }
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            Map<String, Double> newWeights = new LinkedHashMap<>();
            double total = 0;

            for (Map.Entry<String, JTextField> entry : fields.entrySet()) {
                String key = entry.getKey();
                String text = entry.getValue().getText().trim();
                try {
                    double value = Double.parseDouble(text);
                    newWeights.put(key, value);
                    total += value;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter valid numbers for all weights.",
                            "Invalid weight",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (Math.abs(total - 1.0) > 0.001) {
                JOptionPane.showMessageDialog(dialog,
                        "Total must add up to 1.00. It is currently " + df.format(total) + ".",
                        "Invalid total",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (persistWeights(username, newWeights, dialog, true)) {
                dialog.dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        buttonRow.add(resetButton);
        buttonRow.add(confirmButton);
        buttonRow.add(cancelButton);

        dialog.add(content, BorderLayout.CENTER);
        dialog.add(buttonRow, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private static Map<String, Double> loadUserWeights(String username, Component parent) {
        try {
            JSONDataObject dao = new JSONDataObject();
            Client user = dao.getUserByUsername(username);
            if (user == null || user.getPreferences() == null) {
                JOptionPane.showMessageDialog(parent,
                        "Set your preferences before adjusting weights.",
                        "Weights",
                        JOptionPane.INFORMATION_MESSAGE);
                return null;
            }

            Map<String, Double> weights = new LinkedHashMap<>(defaultWeights());
            Map<String, Double> saved = user.getPreferences().getWeights();
            if (saved != null) {
                for (Map.Entry<String, Double> entry : saved.entrySet()) {
                    weights.put(entry.getKey(), entry.getValue());
                }
            }
            return weights;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Unable to load weights right now.",
                    "Weights",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private static boolean persistWeights(String username, Map<String, Double> weights, Component parent, boolean showSuccess) {
        try {
            JSONDataObject dao = new JSONDataObject();
            Client user = dao.getUserByUsername(username);
            if (user == null || user.getPreferences() == null) {
                JOptionPane.showMessageDialog(parent,
                        "Set your preferences before adjusting weights.",
                        "Weights",
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

            MatchingPreferences prefs = user.getPreferences();
            prefs.setWeights(weights);
            dao.save(username, prefs);

            if (showSuccess) {
                JOptionPane.showMessageDialog(parent,
                        "Weights updated.",
                        "Weights",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Unable to update weights right now.",
                    "Weights",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private static void performSearch(String username, JPanel resultsList, JLabel profileHeader, JPanel profileContent, JPanel parent,
                                      MessagesPanel messagesPanel, CardLayout layout, JPanel contentPanel) {
        resultsList.removeAll();
        profileHeader.setText("Profile");
        profileContent.removeAll();
        JLabel placeholder = new JLabel("Match profiles will be shown here.");
        placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileContent.add(Box.createVerticalGlue());
        profileContent.add(placeholder);
        profileContent.add(Box.createVerticalGlue());
        refreshProfilePane(profileContent);
        try {
            JSONDataObject dao = new JSONDataObject();
            WeightedMatchingAlgorithm algorithm = new WeightedMatchingAlgorithm();
            FindMatchesOutputBoundary presenter = new FindMatchesOutputBoundary() {
                @Override
                public void prepareSuccessView(FindMatchesOutputData outputData) {
                    SwingUtilities.invokeLater(() -> renderMatchCards(resultsList, outputData.getMatches(), dao, profileHeader, profileContent, messagesPanel, layout, contentPanel));
                }

                @Override
                public void prepareFailView(String errorMessage) {
                    SwingUtilities.invokeLater(() -> showSearchError(resultsList, errorMessage));
                }
            };

            FindMatchesInputBoundary interactor = new FindMatchesInteractor(new JsonMatchDAO(dao), algorithm, presenter);
            interactor.execute(new FindMatchesInputData(username));
        } catch (Exception ex) {
            showSearchError(resultsList, "Unable to run search right now.");
        }

        resultsList.revalidate();
        resultsList.repaint();
    }

    private static void renderMatchCards(JPanel resultsList, Map<String, Double> matches, JSONDataObject dao,
                                         JLabel profileHeader, JPanel profileContent, MessagesPanel messagesPanel, CardLayout layout,
                                         JPanel contentPanel) {
        resultsList.removeAll();

        int displayed = 0;
        for (Map.Entry<String, Double> entry : matches.entrySet()) {
            String matchName = entry.getKey();
            JPanel card = buildMatchCard(matchName, dao, resultsList, profileHeader, profileContent, messagesPanel, layout, contentPanel);
            resultsList.add(card);
            resultsList.add(Box.createVerticalStrut(8));
            displayed++;
        }

        for (int i = displayed; i < 10; i++) {
            JPanel placeholder = buildPlaceholderCard();
            resultsList.add(placeholder);
            if (i < 9) {
                resultsList.add(Box.createVerticalStrut(8));
            }
        }

        if (matches.isEmpty()) {
            showSearchError(resultsList, "No matches found.");
        }

        resultsList.revalidate();
        resultsList.repaint();
    }

    private static JPanel buildMatchCard(String matchName, JSONDataObject dao, JPanel parent,
                                         JLabel profileHeader, JPanel profileContent, MessagesPanel messagesPanel,
                                         CardLayout layout, JPanel contentPanel) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel nameLabel = new JLabel(matchName);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 16f));
        card.add(nameLabel, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(e -> showMatchProfile(matchName, dao, profileHeader, profileContent, parent));

        JButton messageButton = new JButton("Message");
        messageButton.addActionListener(e -> {
            messagesPanel.startConversation(matchName, false);
            layout.show(contentPanel, MATCHES_CARD);
        });

        buttons.add(viewProfileButton);
        buttons.add(messageButton);

        card.add(buttons, BorderLayout.EAST);

        return card;
    }

    private static JPanel buildPlaceholderCard() {
        JPanel placeholder = new JPanel(new BorderLayout());
        placeholder.setOpaque(true);
        placeholder.setBackground(Color.WHITE);
        placeholder.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(14, 12, 14, 12)));
        placeholder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        return placeholder;
    }

    private static void showSearchError(JPanel resultsList, String message) {
        resultsList.removeAll();
        JLabel error = new JLabel(message);
        error.setForeground(Color.RED.darker());
        error.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultsList.add(error);
        resultsList.revalidate();
        resultsList.repaint();
    }

    private static void showMatchProfile(String username, JSONDataObject dao, JLabel header, JPanel content, Component parent) {
        content.removeAll();

        try {
            Client user = dao.getUserByUsername(username);
            if (user == null || user.getProfile() == null) {
                header.setText("Profile");
                JLabel empty = new JLabel("Profile details unavailable for this user.");
                content.add(empty);
                refreshProfilePane(content);
                return;
            }

            Profile profile = user.getProfile();
            MatchingPreferences prefs = user.getPreferences();
            if (prefs == null) {
                header.setText("Profile");
                JLabel empty = new JLabel("Preferences unavailable for this user.");
                content.add(empty);
                refreshProfilePane(content);
                return;
            }
            header.setText(username + "'s Profile");

            JPanel general = createProfileSection("General Information");
            general.add(createStackedField("Name", profile.getFirstName() + " " + profile.getLastName()));
            general.add(createStackedField("Email", profile.getEmail()));
            general.add(createStackedField("Phone", profile.getPhone()));
            general.add(createStackedField("Country of Origin", profile.getCountryOfOrigin()));
            general.add(createBioField(profile.getBio()));

            JPanel matching = createProfileSection("Matching Preferences");
            matching.add(createStackedField("Year of Study", formatYear(prefs)));
            matching.add(createStackedField("Courses", formatList(prefs.getCourses())));
            matching.add(createStackedField("Languages", formatList(prefs.getLanguages())));
            matching.add(createStackedField("Hobbies", formatList(prefs.getHobbies())));
            matching.add(createProgramList(prefs.getPrograms()));

            content.add(general);
            content.add(Box.createVerticalStrut(12));
            content.add(matching);
        } catch (Exception e) {
            header.setText("Profile");
            JLabel error = new JLabel("Unable to load profile details right now.");
            error.setForeground(Color.RED.darker());
            content.add(error);
        }

        refreshProfilePane(content);
    }

    private static void refreshProfilePane(JPanel content) {
        content.revalidate();
        content.repaint();
    }

    private static JPanel createProfileSection(String title) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(true);
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JLabel heading = new JLabel(title);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 16f));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(heading);
        section.add(Box.createVerticalStrut(8));

        return section;
    }

    private static JPanel createStackedField(String label, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel nameLabel = new JLabel(label);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 13f));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel((value == null || value.isEmpty()) ? "-" : value);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(8));
        return panel;
    }

    private static JPanel createBioField(String bio) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = new JLabel("Bio");
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea area = new JTextArea(bio == null ? "" : bio);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setOpaque(false);
        area.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        area.setFont(area.getFont().deriveFont(13f));

        panel.add(label);
        panel.add(Box.createVerticalStrut(4));
        panel.add(area);
        panel.add(Box.createVerticalStrut(8));
        return panel;
    }

    private static String formatYear(MatchingPreferences prefs) {
        if (prefs == null) {
            return "-";
        }
        int year = prefs.getYearOfStudy();
        switch (year) {
            case 1:
                return "1st";
            case 2:
                return "2nd";
            case 3:
                return "3rd";
            case 4:
                return "4th";
            default:
                return "Other";
        }
    }

    private static JPanel createProgramList(Map<String, String> programs) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = new JLabel("Programs");
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(4));

        if (programs == null || programs.isEmpty()) {
            JLabel none = new JLabel("-");
            none.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(none);
            panel.add(Box.createVerticalStrut(8));
            return panel;
        }

        for (Map.Entry<String, String> entry : programs.entrySet()) {
            String programName = entry.getKey();
            String type = entry.getValue();
            JLabel item = new JLabel(programName + " (" + type + ")");
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(item);
        }

        panel.add(Box.createVerticalStrut(8));
        return panel;
    }

    private static class JsonMatchDAO implements FindMatchesDataAccessObject {
        private final JSONDataObject delegate;

        JsonMatchDAO(JSONDataObject delegate) {
            this.delegate = delegate;
        }

        @Override
        public Client findByUsername(String username) {
            try {
                return delegate.getUserByUsername(username);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public List<Client> getAllUsers() {
            try {
                return delegate.getAllUsers();
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }
    }

    private static MessagesPanel makeMessagesPanel(String username, CardLayout layout, JPanel contentPanel) {
        try {
            JSONDataObject dao = new JSONDataObject();
            return new MessagesPanel(username, dao, layout, contentPanel);
        } catch (Exception e) {
            JPanel fallback = new JPanel(new BorderLayout());
            fallback.add(new JLabel("Messages unavailable."), BorderLayout.CENTER);
            return new MessagesPanel(username, null, layout, contentPanel, fallback);
        }
    }

    private static class MessagesPanel extends JPanel implements PropertyChangeListener {
        private final String username;
        private final JSONDataObject dao;
        private ChatController chatController;
        private NotificationController notificationController;
        private ChatViewModel chatViewModel;
        private NotificationViewModel notificationViewModel;
        private JLabel conversationTitle;
        private final CardLayout conversationCards = new CardLayout();
        private final JPanel conversationCardPanel = new JPanel(conversationCards);
        private final JTextArea conversationArea = new JTextArea();
        private final JTextField inputField = new JTextField(20);
        private final JPanel notificationsList = new JPanel();
        private final JPanel recentsList = new JPanel();
        private final JPanel fallbackPanel;
        private String activeConversation;

        MessagesPanel(String username, JSONDataObject dao, CardLayout layout, JPanel contentPanel) {
            this(username, dao, layout, contentPanel, null);
        }

        MessagesPanel(String username, JSONDataObject dao, CardLayout layout, JPanel contentPanel, JPanel fallbackPanel) {
            this.username = username;
            this.dao = dao;
            this.fallbackPanel = fallbackPanel;

            if (fallbackPanel != null) {
                setLayout(new BorderLayout());
                add(fallbackPanel, BorderLayout.CENTER);
                return;
            }

            chatViewModel = new ChatViewModel();
            notificationViewModel = new NotificationViewModel();
            ViewManagerModel viewManagerModel = new ViewManagerModel();

            ChatPresenter chatPresenter = new ChatPresenter(chatViewModel, viewManagerModel, notificationViewModel);
            ChatInputBoundary chatInteractor = new ChatInteractor(dao, chatPresenter);
            chatController = new ChatController(chatInteractor);

            NotificationPresenter notificationPresenter = new NotificationPresenter(notificationViewModel, chatViewModel, viewManagerModel);
            NotificationInputBoundary notificationInteractor = new NotificationInteractor(dao, notificationPresenter);
            notificationController = new NotificationController(notificationInteractor);

            chatViewModel.addPropertyChangeListener(this);
            notificationViewModel.addPropertyChangeListener(this);
            chatViewModel.getState().setSender(username);
            notificationViewModel.getState().setUsername(username);

            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            JLabel titleLabel = new JLabel("Messages");
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));

            JLabel descriptionLabel = new JLabel("See notifications, recent activity, and open conversations.");
            descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(16f));

            JPanel textPanel = new JPanel(new BorderLayout(0, 8));
            textPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            textPanel.setOpaque(false);
            textPanel.add(titleLabel, BorderLayout.NORTH);
            textPanel.add(descriptionLabel, BorderLayout.CENTER);

            Color borderColor = new Color(220, 220, 220);

            JPanel messageArea = new JPanel(new BorderLayout());
            messageArea.setBackground(Color.WHITE);
            messageArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(borderColor),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)));

            JPanel columns = new JPanel(new GridLayout(1, 3, 12, 0));
            columns.setOpaque(false);

            JPanel notificationsPanel = new JPanel(new BorderLayout());
            notificationsPanel.setBackground(Color.WHITE);
            notificationsPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(borderColor),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)));

            JLabel notificationsTitle = new JLabel("Notifications", JLabel.CENTER);
            notificationsTitle.setFont(notificationsTitle.getFont().deriveFont(Font.BOLD, 18f));
            notificationsPanel.add(notificationsTitle, BorderLayout.NORTH);

            notificationsList.setLayout(new BoxLayout(notificationsList, BoxLayout.Y_AXIS));
            notificationsList.setOpaque(false);
            JScrollPane notificationScroll = new JScrollPane(notificationsList);
            notificationScroll.setBorder(BorderFactory.createEmptyBorder());
            notificationScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            notificationScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            notificationScroll.getVerticalScrollBar().setUnitIncrement(14);
            notificationsPanel.add(notificationScroll, BorderLayout.CENTER);

            JPanel recentPanel = new JPanel(new BorderLayout());
            recentPanel.setBackground(Color.WHITE);
            recentPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(borderColor),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)));

            JLabel recentTitle = new JLabel("Past Conversations", JLabel.CENTER);
            recentTitle.setFont(recentTitle.getFont().deriveFont(Font.BOLD, 18f));
            recentPanel.add(recentTitle, BorderLayout.NORTH);

            recentsList.setLayout(new BoxLayout(recentsList, BoxLayout.Y_AXIS));
            recentsList.setOpaque(false);
            JScrollPane recentsScroll = new JScrollPane(recentsList);
            recentsScroll.setBorder(BorderFactory.createEmptyBorder());
            recentsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            recentsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            recentsScroll.getVerticalScrollBar().setUnitIncrement(14);
            recentPanel.add(recentsScroll, BorderLayout.CENTER);

            JPanel conversationPanel = new JPanel(new BorderLayout());
            conversationPanel.setBackground(Color.WHITE);
            conversationPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(borderColor),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)));

            conversationTitle = new JLabel("Conversation", JLabel.CENTER);
            conversationTitle.setFont(conversationTitle.getFont().deriveFont(Font.BOLD, 18f));
            conversationPanel.add(conversationTitle, BorderLayout.NORTH);

            JPanel placeholderCard = new JPanel(new BorderLayout());
            placeholderCard.setBackground(Color.WHITE);
            JLabel placeholder = new JLabel("Conversations will be shown here.", JLabel.CENTER);
            placeholder.setFont(placeholder.getFont().deriveFont(15f));
            placeholderCard.add(placeholder, BorderLayout.CENTER);
            conversationCardPanel.setBackground(Color.WHITE);
            conversationCardPanel.add(placeholderCard, "placeholder");

            conversationArea.setEditable(false);
            conversationArea.setLineWrap(true);
            conversationArea.setWrapStyleWord(true);
            conversationArea.setBackground(Color.WHITE);
            conversationArea.setOpaque(true);
            conversationArea.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

            JScrollPane conversationScroll = new JScrollPane(conversationArea);
            conversationScroll.setBorder(BorderFactory.createEmptyBorder());
            conversationScroll.getViewport().setBackground(Color.WHITE);

            JPanel composer = new JPanel(new BorderLayout(8, 0));
            composer.setBackground(Color.WHITE);
            JButton sendButton = new JButton("Send");
            sendButton.addActionListener(e -> sendMessage());
            composer.add(inputField, BorderLayout.CENTER);
            composer.add(sendButton, BorderLayout.EAST);

            JPanel chatPanel = new JPanel(new BorderLayout(0, 8));
            chatPanel.setBackground(Color.WHITE);
            chatPanel.add(conversationScroll, BorderLayout.CENTER);
            chatPanel.add(composer, BorderLayout.SOUTH);

            conversationCardPanel.add(chatPanel, "chat");
            conversationPanel.add(conversationCardPanel, BorderLayout.CENTER);
            conversationCards.show(conversationCardPanel, "placeholder");

            columns.add(notificationsPanel);
            columns.add(recentPanel);
            columns.add(conversationPanel);

            messageArea.add(columns, BorderLayout.CENTER);

            add(textPanel, BorderLayout.NORTH);
            add(messageArea, BorderLayout.CENTER);

            refreshNotificationsFromDao();
            refreshRecents();
        }

        private void refreshNotificationsFromDao() {
            if (dao == null) {
                return;
            }
            try {
                ArrayList<String> notifications = notificationController.getNotification(username);
                NotificationState state = notificationViewModel.getState();
                state.setNotification(notifications);
                notificationViewModel.firePropertyChange();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to load notifications right now.");
            }
        }

        private void refreshNotifications(ArrayList<String> notifications) {
            notificationsList.removeAll();
            if (notifications == null || notifications.isEmpty()) {
                JLabel header = new JLabel("You are all caught up.", JLabel.CENTER);
                header.setFont(header.getFont().deriveFont(Font.PLAIN, 14f));
                header.setAlignmentX(Component.CENTER_ALIGNMENT);
                notificationsList.add(Box.createVerticalGlue());
                notificationsList.add(header);
                notificationsList.add(Box.createVerticalGlue());
            } else {
                JLabel header = new JLabel("You have messages from the following users:");
                header.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
                header.setAlignmentX(Component.LEFT_ALIGNMENT);
                notificationsList.add(header);
                for (String name : notifications) {
                    JPanel card = buildUserCard(name, true);
                    card.setAlignmentX(Component.LEFT_ALIGNMENT);
                    notificationsList.add(card);
                    notificationsList.add(Box.createVerticalStrut(8));
                }
                notificationsList.add(Box.createVerticalGlue());
            }
            notificationsList.revalidate();
            notificationsList.repaint();
        }

        private JPanel buildUserCard(String name, boolean fromNotification) {
            JPanel card = new JPanel(new BorderLayout());
            card.setOpaque(true);
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 68));
            card.setPreferredSize(new Dimension(260, 68));

            JLabel label = new JLabel(name);
            label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
            card.add(label, BorderLayout.CENTER);

            JButton openButton = new JButton("Message");
            openButton.addActionListener(e -> startConversation(name, fromNotification));
            card.add(openButton, BorderLayout.EAST);
            return card;
        }

        private void refreshRecents() {
            recentsList.removeAll();
            if (dao != null) {
                try {
                    ArrayList<String> conversations = dao.getConversationUsers(username);
                    for (String name : conversations) {
                        recentsList.add(buildUserCard(name, false));
                        recentsList.add(Box.createVerticalStrut(8));
                    }
                } catch (Exception e) {
                    recentsList.add(new JLabel("Unable to load conversations."));
                }
            }
            recentsList.revalidate();
            recentsList.repaint();
        }

        void startConversation(String other, boolean fromNotification) {
            if (dao == null || other == null || other.isEmpty()) {
                return;
            }
            if (isSystemUser(other)) {
                JOptionPane.showMessageDialog(this, "Conversations with this user are unavailable.");
                return;
            }
            activeConversation = other;
            conversationTitle.setText("Conversation with " + other);
            chatViewModel.getState().setOther(other);
            chatViewModel.getState().setSender(username);
            conversationCards.show(conversationCardPanel, "chat");
            try {
                if (fromNotification) {
                    notificationController.execute(username, other);
                    refreshNotificationsFromDao();
                } else {
                    ArrayList<String> log = dao.getMessages(username, other);
                    chatViewModel.getState().setLog(log);
                    chatViewModel.firePropertyChange();
                    dao.removeUserFromNotificatoin(username, other);
                    refreshNotificationsFromDao();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to open conversation.");
            }
            refreshRecents();
        }

        private void sendMessage() {
            if (activeConversation == null || activeConversation.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a conversation to start messaging.");
                return;
            }
            String message = inputField.getText().trim();
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this, "The message cannot be empty!");
                return;
            }
            try {
                chatController.execute(username, activeConversation, message);
                inputField.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to send message right now.");
            }
        }

        private void renderConversation(ArrayList<String> log, String sender, String receiver) {
            conversationArea.setText("");
            if (log != null) {
                for (String msg : log) {
                    if (msg.startsWith("*")) {
                        conversationArea.append(receiver + " : " + msg.substring(1) + "\n");
                    } else {
                        conversationArea.append(sender + " : " + msg + "\n");
                    }
                }
            }
            conversationArea.setCaretPosition(conversationArea.getDocument().getLength());
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getSource() == chatViewModel) {
                ChatState state = (ChatState) evt.getNewValue();
                if (state.getError() != null) {
                    JOptionPane.showMessageDialog(this, state.getError());
                    state.setError(null);
                    return;
                }
                if (state.getOther() != null && !state.getOther().isEmpty()) {
                    activeConversation = state.getOther();
                    conversationTitle.setText("Conversation with " + activeConversation);
                }
                conversationCards.show(conversationCardPanel, "chat");
                renderConversation(state.getLog(), state.getSender(), state.getOther());
                refreshRecents();
            } else if (evt.getSource() == notificationViewModel) {
                NotificationState state = (NotificationState) evt.getNewValue();
                if (state.getError() != null) {
                    JOptionPane.showMessageDialog(this, state.getError());
                    state.setError(null);
                }
                refreshNotifications(state.getNotification());
            }
        }

        private boolean isSystemUser(String name) {
            return name != null && (name.equalsIgnoreCase("User1") || name.equalsIgnoreCase("User2"));
        }
    }

    private static void openEditProfileDialog(JFrame parent, String username) {
        try {
            JSONDataObject dao = new JSONDataObject();
            Client user = dao.getUserByUsername(username);
            if (user == null) {
                JOptionPane.showMessageDialog(parent,
                        "Unable to load your profile for editing.",
                        "Edit Profile",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            EditProfileDialog dialog = new EditProfileDialog(parent, dao, user);
            dialog.setLocationRelativeTo(parent);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                parent.dispose();
                showDashboard(username);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent,
                    "Unable to open profile editor.",
                    "Edit Profile",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class EditProfileDialog extends JDialog {
        private final JTextField firstNameField = new JTextField(15);
        private final JTextField lastNameField = new JTextField(15);
        private final JTextField countryField = new JTextField(15);
        private final JTextField emailField = new JTextField(15);
        private final JTextField phoneField = new JTextField(15);
        private final JTextArea bioArea = new JTextArea(6, 15);

        private final JComboBox<String> yearDropdown = new JComboBox<>();
        private final JComboBox<String> coursesDropdown = new JComboBox<>();
        private final JComboBox<String> programDropdown = new JComboBox<>();
        private final JComboBox<String> hobbiesDropdown = new JComboBox<>();
        private final JComboBox<String> languagesDropdown = new JComboBox<>();

        private final DefaultListModel<String> courseListModel = new DefaultListModel<>();
        private final DefaultListModel<String> programListModel = new DefaultListModel<>();
        private final DefaultListModel<String> hobbyListModel = new DefaultListModel<>();
        private final DefaultListModel<String> languageListModel = new DefaultListModel<>();

        private final ArrayList<String> coursesList = new ArrayList<>();
        private final ArrayList<String> programList = new ArrayList<>();
        private final ArrayList<String> programTypes = new ArrayList<>();
        private final ArrayList<String> hobbies = new ArrayList<>();
        private final ArrayList<String> languages = new ArrayList<>();
        private final HashMap<String, Double> weights = new HashMap<>();

        private boolean saved = false;

        EditProfileDialog(JFrame parent, JSONDataObject dao, Client user) {
            super(parent, "Editing Profile...", true);
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(920, 900));

            JLabel title = new JLabel("Editing Profile...", JLabel.CENTER);
            title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
            title.setBorder(BorderFactory.createEmptyBorder(16, 0, 12, 0));
            add(title, BorderLayout.NORTH);

            initDropdownData();
            preloadUserData(user);

            JPanel content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
            content.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));
            content.setOpaque(false);

            JPanel generalSection = buildGeneralSection();
            generalSection.setAlignmentY(Component.TOP_ALIGNMENT);
            content.add(generalSection);
            content.add(Box.createHorizontalStrut(14));
            JPanel preferencesSection = buildPreferencesSection();
            preferencesSection.setAlignmentY(Component.TOP_ALIGNMENT);
            content.add(preferencesSection);

            JButton confirmButton = new JButton("Confirm Changes");
            confirmButton.setPreferredSize(new Dimension(200, 40));
            confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            confirmButton.addActionListener(e -> {
                if (saveChanges(dao, user.getUsername())) {
                    saved = true;
                    JOptionPane.showMessageDialog(this,
                            "Profile updated.",
                            "Edit Profile",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            });

            JPanel footer = new JPanel();
            footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 14, 0));
            footer.add(confirmButton);

            add(new JScrollPane(content), BorderLayout.CENTER);
            add(footer, BorderLayout.SOUTH);

            pack();
        }

        boolean isSaved() {
            return saved;
        }

        private void initDropdownData() {
            ArrayList<String> yearOptions = new ArrayList<>();
            yearOptions.add("1st");
            yearOptions.add("2nd");
            yearOptions.add("3rd");
            yearOptions.add("4th");
            yearOptions.add("Other");
            yearDropdown.setModel(new DefaultComboBoxModel<>(yearOptions.toArray(new String[0])));

            ArrayList<String> courseCodes = loadValuesFromFile("uoft_courses_stgeorge.json", "code");
            coursesDropdown.setModel(new DefaultComboBoxModel<>(courseCodes.toArray(new String[0])));

            ArrayList<String> programNames = loadValuesFromFile("uoft_programs_stgeorge.json", "name");
            programDropdown.setModel(new DefaultComboBoxModel<>(programNames.toArray(new String[0])));

            ArrayList<String> hobbyOptions = new ArrayList<>();
            hobbyOptions.add("Sports");
            hobbyOptions.add("Arts");
            hobbyOptions.add("Science");
            hobbyOptions.add("Electronics");
            hobbyOptions.add("Dancing");
            hobbyOptions.add("Video games");
            hobbyOptions.add("Reading");
            hobbyOptions.add("Fishing");
            hobbyOptions.add("Music");
            hobbyOptions.add("Fashion");
            hobbiesDropdown.setModel(new DefaultComboBoxModel<>(hobbyOptions.toArray(new String[0])));

            ArrayList<String> languageOptions = new ArrayList<>();
            languageOptions.add("English");
            languageOptions.add("French");
            languageOptions.add("Arabic");
            languageOptions.add("Spanish");
            languageOptions.add("Mandarin");
            languageOptions.add("Farsi");
            languageOptions.add("Urdu");
            languageOptions.add("Russian");
            languageOptions.add("Japanese");
            languageOptions.add("Turkish");
            languagesDropdown.setModel(new DefaultComboBoxModel<>(languageOptions.toArray(new String[0])));
        }

        private ArrayList<String> loadValuesFromFile(String fileName, String key) {
            ArrayList<String> values = new ArrayList<>();
            try (FileReader reader = new FileReader(fileName)) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(reader);
                JSONArray array = (JSONArray) obj;
                for (Object entry : array) {
                    JSONObject jsonObject = (JSONObject) entry;
                    Object value = jsonObject.get(key);
                    if (value != null) {
                        values.add(value.toString());
                    }
                }
            } catch (IOException | ParseException e) {
                // ignore and return empty list so UI still loads
            }
            return values;
        }

        private JPanel buildGeneralSection() {
            JPanel section = createSectionPanel("General Information");

            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setOpaque(false);
            container.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

            Dimension fieldSize = new Dimension(260, 30);
            configureField(firstNameField, fieldSize);
            configureField(lastNameField, fieldSize);
            configureField(countryField, fieldSize);
            configureField(emailField, fieldSize);
            configureField(phoneField, fieldSize);

            bioArea.setLineWrap(true);
            bioArea.setWrapStyleWord(true);
            bioArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            bioArea.setPreferredSize(new Dimension(260, 140));
            bioArea.setMaximumSize(new Dimension(260, 180));

            container.add(labeledField("First Name", firstNameField));
            container.add(Box.createVerticalStrut(10));
            container.add(labeledField("Last Name", lastNameField));
            container.add(Box.createVerticalStrut(10));
            container.add(labeledField("Country of Origin", countryField));
            container.add(Box.createVerticalStrut(10));
            container.add(labeledField("Email", emailField));
            container.add(Box.createVerticalStrut(10));
            container.add(labeledField("Phone Number", phoneField));
            container.add(Box.createVerticalStrut(10));
            container.add(labeledArea("Bio", bioArea));

            section.add(container, BorderLayout.CENTER);
            return section;
        }

        private JPanel buildPreferencesSection() {
            JPanel section = createSectionPanel("Matching Preferences");

            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setOpaque(false);
            container.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

            container.add(labeledCombo("Year of Study", yearDropdown));
            container.add(Box.createVerticalStrut(12));
            container.add(selectionSection("Spoken Languages", languagesDropdown, languageListModel, languages));
            container.add(Box.createVerticalStrut(10));
            container.add(selectionSection("Hobbies/Interests", hobbiesDropdown, hobbyListModel, hobbies));
            container.add(Box.createVerticalStrut(10));
            container.add(selectionSection("Courses", coursesDropdown, courseListModel, coursesList));
            container.add(Box.createVerticalStrut(10));
            container.add(programSection());

            section.add(container, BorderLayout.CENTER);
            return section;
        }

        private void preloadUserData(Client user) {
            Profile profile = user.getProfile();
            if (profile != null) {
                firstNameField.setText(safe(profile.getFirstName()));
                lastNameField.setText(safe(profile.getLastName()));
                countryField.setText(safe(profile.getCountryOfOrigin()));
                emailField.setText(safe(profile.getEmail()));
                phoneField.setText(safe(profile.getPhone()));
                bioArea.setText(safe(profile.getBio()));
            }

            MatchingPreferences preferences = user.getPreferences();
            if (preferences != null) {
                selectYear(preferences.getYearOfStudy());

                if (preferences.getLanguages() != null) {
                    for (String lang : preferences.getLanguages()) {
                        languages.add(lang);
                        languageListModel.addElement(lang);
                    }
                }

                if (preferences.getHobbies() != null) {
                    for (String hobby : preferences.getHobbies()) {
                        hobbies.add(hobby);
                        hobbyListModel.addElement(hobby);
                    }
                }

                if (preferences.getCourses() != null) {
                    for (String course : preferences.getCourses()) {
                        coursesList.add(course);
                        courseListModel.addElement(course);
                    }
                }

                if (preferences.getPrograms() != null) {
                    for (Map.Entry<String, String> entry : preferences.getPrograms().entrySet()) {
                        programList.add(entry.getKey());
                        programTypes.add(entry.getValue());
                        programListModel.addElement(entry.getKey() + " (" + entry.getValue() + ")");
                    }
                }
                if (preferences.getWeights() != null) {
                    weights.putAll(preferences.getWeights());
                }
            }

            if (weights.isEmpty()) {
                weights.put("Courses", 0.35);
                weights.put("Programs", 0.25);
                weights.put("YearOfStudy", 0.2);
                weights.put("Languages", 0.12);
                weights.put("Hobbies", 0.08);
            }
        }

        private void selectYear(int year) {
            switch (year) {
                case 1:
                    yearDropdown.setSelectedItem("1st");
                    break;
                case 2:
                    yearDropdown.setSelectedItem("2nd");
                    break;
                case 3:
                    yearDropdown.setSelectedItem("3rd");
                    break;
                case 4:
                    yearDropdown.setSelectedItem("4th");
                    break;
                default:
                    yearDropdown.setSelectedItem("Other");
            }
        }

        private JPanel labeledField(String label, JTextField field) {
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setOpaque(false);
            JLabel jLabel = new JLabel(label);
            jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            container.add(jLabel);
            container.add(Box.createVerticalStrut(6));
            container.add(field);
            container.setAlignmentX(Component.CENTER_ALIGNMENT);
            return container;
        }

        private JPanel labeledArea(String label, JTextArea area) {
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setOpaque(false);
            JLabel jLabel = new JLabel(label);
            jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            container.add(jLabel);
            container.add(Box.createVerticalStrut(6));
            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setPreferredSize(new Dimension(260, 160));
            scrollPane.setMaximumSize(new Dimension(260, 190));
            container.add(scrollPane);
            container.setAlignmentX(Component.CENTER_ALIGNMENT);
            return container;
        }

        private JPanel labeledCombo(String label, JComboBox<String> comboBox) {
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setOpaque(false);
            JLabel jLabel = new JLabel(label);
            jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            comboBox.setMaximumSize(new Dimension(240, 32));
            comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            container.add(jLabel);
            container.add(Box.createVerticalStrut(6));
            container.add(comboBox);
            container.setAlignmentX(Component.CENTER_ALIGNMENT);
            return container;
        }

        private JPanel selectionSection(String label,
                                        JComboBox<String> dropdown,
                                        DefaultListModel<String> listModel,
                                        List<String> backingList) {
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setOpaque(false);

            JLabel jLabel = new JLabel(label);
            jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            dropdown.setMaximumSize(new Dimension(220, 32));
            row.add(dropdown);
            row.add(Box.createHorizontalStrut(8));
            JButton addButton = new JButton("Add");
            addButton.setPreferredSize(new Dimension(80, 32));
            row.add(addButton);
            row.add(Box.createHorizontalStrut(6));
            JButton removeButton = new JButton("Remove");
            removeButton.setPreferredSize(new Dimension(100, 32));
            row.add(removeButton);
            row.setAlignmentX(Component.CENTER_ALIGNMENT);

            JList<String> list = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(300, 70));
            scrollPane.setMaximumSize(new Dimension(320, 90));

            addButton.addActionListener(e -> {
                String choice = (String) dropdown.getSelectedItem();
                if (choice != null) {
                    backingList.add(choice);
                    listModel.addElement(choice);
                }
            });

            removeButton.addActionListener(e -> showRemovalDialog(label, listModel, backingList));

            container.add(jLabel);
            container.add(Box.createVerticalStrut(6));
            container.add(row);
            container.add(Box.createVerticalStrut(6));
            container.add(scrollPane);
            container.setAlignmentX(Component.CENTER_ALIGNMENT);
            return container;
        }

        private void showRemovalDialog(String label, DefaultListModel<String> listModel, List<String> backingList) {
            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "There are no items to remove.",
                        "Remove " + label,
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JPanel checkboxPanel = new JPanel();
            checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
            List<JCheckBox> boxes = new ArrayList<>();
            for (int i = 0; i < listModel.size(); i++) {
                JCheckBox box = new JCheckBox(listModel.getElementAt(i));
                boxes.add(box);
                checkboxPanel.add(box);
            }

            JScrollPane scrollPane = new JScrollPane(checkboxPanel);
            scrollPane.setPreferredSize(new Dimension(320, 180));

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Remove " + label,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                for (int i = boxes.size() - 1; i >= 0; i--) {
                    if (boxes.get(i).isSelected()) {
                        backingList.remove(i);
                        listModel.remove(i);
                    }
                }
            }
        }

        private JPanel programSection() {
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setOpaque(false);

            JLabel jLabel = new JLabel("Programs (Major/Minor/Specialist)");
            jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setOpaque(false);
            programDropdown.setMaximumSize(new Dimension(220, 32));
            programDropdown.setPreferredSize(new Dimension(220, 32));
            row.add(programDropdown);
            row.add(Box.createHorizontalStrut(10));
            JButton addButton = new JButton("Add Program");
            addButton.setPreferredSize(new Dimension(130, 32));
            addButton.setMaximumSize(new Dimension(140, 36));
            row.add(addButton);
            row.add(Box.createHorizontalStrut(8));
            JButton removeButton = new JButton("Remove");
            removeButton.setPreferredSize(new Dimension(100, 32));
            row.add(removeButton);
            row.setAlignmentX(Component.CENTER_ALIGNMENT);

            JList<String> list = new JList<>(programListModel);
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(300, 80));
            scrollPane.setMaximumSize(new Dimension(320, 100));

            addButton.addActionListener(e -> addSelectedProgram());
            removeButton.addActionListener(e -> showProgramRemovalDialog());

            container.add(jLabel);
            container.add(Box.createVerticalStrut(6));
            container.add(row);
            container.add(Box.createVerticalStrut(6));
            container.add(scrollPane);
            container.setAlignmentX(Component.CENTER_ALIGNMENT);
            return container;
        }

        private void showProgramRemovalDialog() {
            if (programListModel.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "There are no programs to remove.",
                        "Remove Programs",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JPanel checkboxPanel = new JPanel();
            checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
            List<JCheckBox> boxes = new ArrayList<>();
            for (int i = 0; i < programListModel.size(); i++) {
                JCheckBox box = new JCheckBox(programListModel.getElementAt(i));
                boxes.add(box);
                checkboxPanel.add(box);
            }

            JScrollPane scrollPane = new JScrollPane(checkboxPanel);
            scrollPane.setPreferredSize(new Dimension(320, 200));

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Remove Programs",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                for (int i = boxes.size() - 1; i >= 0; i--) {
                    if (boxes.get(i).isSelected()) {
                        programList.remove(i);
                        programTypes.remove(i);
                        programListModel.remove(i);
                    }
                }
            }
        }

        private void addSelectedProgram() {
            String choice = (String) programDropdown.getSelectedItem();
            if (choice != null) {
                String type = askProgramType();
                if (type != null) {
                    programList.add(choice);
                    programTypes.add(type);
                    programListModel.addElement(choice + " (" + type + ")");
                }
            }
        }

        private String askProgramType() {
            String[] options = new String[]{"Major", "Minor", "Specialist"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Select the type for this program:",
                    "Program Type",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice >= 0 && choice < options.length) {
                return options[choice];
            }
            return null;
        }

        private boolean saveChanges(JSONDataObject dao, String username) {
            if (isBlank(firstNameField.getText()) ||
                    isBlank(lastNameField.getText()) ||
                    isBlank(countryField.getText()) ||
                    isBlank(bioArea.getText()) ||
                    isBlank(emailField.getText()) ||
                    isBlank(phoneField.getText())) {
                JOptionPane.showMessageDialog(this,
                        "All fields are required.",
                        "Edit Profile",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (bioArea.getText().length() > 200) {
                JOptionPane.showMessageDialog(this,
                        "Bio must be at most 200 characters.",
                        "Edit Profile",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            int year = parseYear((String) yearDropdown.getSelectedItem());

            HashMap<String, String> programs = new HashMap<>();
            for (int i = 0; i < programList.size(); i++) {
                programs.put(programList.get(i), programTypes.get(i));
            }

            MatchingPreferences preferences = new MatchingPreferences(
                    new ArrayList<>(coursesList),
                    programs,
                    year,
                    new ArrayList<>(hobbies),
                    new ArrayList<>(languages),
                    new HashMap<>(weights)
            );

            Profile profile = new Profile(
                    username,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    countryField.getText(),
                    bioArea.getText(),
                    emailField.getText(),
                    phoneField.getText()
            );

            try {
                dao.save(profile);
                dao.save(username, preferences);
                return true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Unable to save changes. Please try again.",
                        "Edit Profile",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        private int parseYear(String yearSelection) {
            if (yearSelection == null) {
                return 0;
            }
            switch (yearSelection) {
                case "1st":
                    return 1;
                case "2nd":
                    return 2;
                case "3rd":
                    return 3;
                case "4th":
                    return 4;
                default:
                    return 0;
            }
        }

        private void configureField(JTextField field, Dimension size) {
            field.setPreferredSize(size);
            field.setMaximumSize(size);
            field.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        private boolean isBlank(String value) {
            return value == null || value.trim().isEmpty();
        }

        private String safe(String value) {
            return value == null ? "" : value;
        }
    }
}