package View;

import Entity.MatchingPreferences;
import Interface_Adapter.EnterInfo.EnterInfoController;
import Interface_Adapter.EnterInfo.EnterInfoState;
import Interface_Adapter.EnterInfo.EnterInfoViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class EnterInfoView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "enter info for matching";

    private EnterInfoController controller;
    private final EnterInfoViewModel viewModel;

    // Wrap JLists of multiple choices for drop-downs into JScrollPane (for multi-select).
    private final JList<String> coursesList = new JList<>();
    private final JList<String> programsList = new JList<>();
    private final JList<String> hobbiesList = new JList<>();
    private final JList<String> languagesList = new JList<>();
    // Drop-down for single-select.
    private final JComboBox<Integer> yearList = new JComboBox<>();
    // Weights dropdowns.
    private final String[] ranks = {"1", "2", "3", "4", "5"};
    private final JComboBox<String> weightCourses = new JComboBox<>(ranks);
    private final JComboBox<String> weightPrograms = new JComboBox<>(ranks);
    private final JComboBox<String> weightYear = new JComboBox<>(ranks);
    private final JComboBox<String> weightHobbies = new JComboBox<>(ranks);
    private final JComboBox<String> weightLanguages = new JComboBox<>(ranks);

    // The Save Button
    private final JButton saveButton = new JButton("Save Preferences");

    public EnterInfoView(EnterInfoController controller, EnterInfoViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Enter Matching Information");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);

        // Panel for courses with scrolling for drop-down.
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        coursePanel.setBorder(BorderFactory.createTitledBorder("Courses (Select up to 6)"));

        coursesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane courseScroll = new JScrollPane(coursesList);
        courseScroll.setPreferredSize(new Dimension(300, 100));

        JPanel courseWeightPanel = new JPanel();
        courseWeightPanel.add(new JLabel("Importance (1-5):"));
        courseWeightPanel.add(weightCourses);

        coursePanel.add(courseScroll);
        coursePanel.add(courseWeightPanel);
        this.add(coursePanel);

        // Panel for programs with scrolling for drop-down.
        JPanel programPanel = new JPanel();
        programPanel.setLayout(new BoxLayout(programPanel, BoxLayout.Y_AXIS));
        programPanel.setBorder(BorderFactory.createTitledBorder("Programs (Select up to 3)"));

        programsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane programScroll = new JScrollPane(programsList);
        programScroll.setPreferredSize(new Dimension(300, 80));

        JPanel programWeightPanel = new JPanel();
        programWeightPanel.add(new JLabel("Importance (1-5):"));
        programWeightPanel.add(weightPrograms);

        programPanel.add(programScroll);
        programPanel.add(programWeightPanel);
        this.add(programPanel);

        // Panel for year of study.
        JPanel yearPanel = new JPanel();
        yearPanel.setBorder(BorderFactory.createTitledBorder("Year of Study"));
        yearPanel.add(new JLabel("Year:"));
        yearPanel.add(yearList);
        yearPanel.add(new JLabel("Importance:"));
        yearPanel.add(weightYear);
        this.add(yearPanel);

        // HOBBIES
        JPanel hobbiesPanel = new JPanel();
        hobbiesPanel.setLayout(new BoxLayout(hobbiesPanel, BoxLayout.Y_AXIS));
        hobbiesPanel.setBorder(BorderFactory.createTitledBorder("Hobbies"));

        hobbiesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane hobbyScroll = new JScrollPane(hobbiesList);
        hobbyScroll.setPreferredSize(new Dimension(300, 80));

        JPanel hobbyWeightPanel = new JPanel();
        hobbyWeightPanel.add(new JLabel("Importance (1-5):"));
        hobbyWeightPanel.add(weightHobbies);

        hobbiesPanel.add(hobbyScroll);
        hobbiesPanel.add(hobbyWeightPanel);
        this.add(hobbiesPanel);

        // LANGUAGES
        JPanel languagesPanel = new JPanel();
        languagesPanel.setLayout(new BoxLayout(languagesPanel, BoxLayout.Y_AXIS));
        languagesPanel.setBorder(BorderFactory.createTitledBorder("Languages"));

        languagesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane langScroll = new JScrollPane(languagesList);
        langScroll.setPreferredSize(new Dimension(300, 80));

        JPanel langWeightPanel = new JPanel();
        langWeightPanel.add(new JLabel("Importance (1-5):"));
        langWeightPanel.add(weightLanguages);

        languagesPanel.add(langScroll);
        languagesPanel.add(langWeightPanel);
        this.add(languagesPanel);

        // Save button panel.
        JPanel buttons = new JPanel();
        buttons.add(saveButton);
        this.add(buttons);

        saveButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(saveButton)) {

            // Handle potential null for single select
            int year = 1;
            if (yearList.getSelectedItem() != null) {
                year = (Integer) yearList.getSelectedItem();
            }

            // Gather Weights
            Map<String, Double> weights = new HashMap<>();

            weights.put("Courses", Double.valueOf((String) weightCourses.getSelectedItem()));
            weights.put("Programs", Double.valueOf((String) weightPrograms.getSelectedItem()));
            weights.put("YearOfStudy", Double.valueOf((String) weightYear.getSelectedItem()));
            weights.put("Hobbies", Double.valueOf((String) weightHobbies.getSelectedItem()));
            weights.put("Languages", Double.valueOf((String) weightLanguages.getSelectedItem()));

            // Send to Controller
            String username = viewModel.getState().username;

            controller.execute(
                    username,
                    coursesList.getSelectedValuesList(),
                    programsList.getSelectedValuesList(),
                    year,
                    hobbiesList.getSelectedValuesList(),
                    languagesList.getSelectedValuesList(),
                    weights
            );
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EnterInfoState state = (EnterInfoState) evt.getNewValue();

        // 1. Handle Errors
        if (state.getFailedSaveMessage() != null && !state.getFailedSaveMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getFailedSaveMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            state.setFailedSaveMessage(""); // Reset error
        }
        // 2. Handle Success
        else if (state.getSaveMessage() != null && !state.getSaveMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getSaveMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            state.setSaveMessage(""); // Reset message
        }
        // 3. Populate Dropdowns (Only if empty, to avoid resetting user selection)
        else {
            if (coursesList.getModel().getSize() == 0) {
                // Populate Courses (Displaying Code: Title)
                DefaultListModel<String> courseModel = new DefaultListModel<>();
                for (Map.Entry<String, String> entry : state.getAllCourses().entrySet()) {
                    courseModel.addElement(entry.getKey() + ": " + entry.getValue());
                }
                coursesList.setModel(courseModel);
            }
            if (programsList.getModel().getSize() == 0) {
                programsList.setListData(state.getAllPrograms().toArray(new String[0]));
            }

            if (hobbiesList.getModel().getSize() == 0) {
                hobbiesList.setListData(state.getAllHobbies().toArray(new String[0]));
            }

            if (languagesList.getModel().getSize() == 0) {
                languagesList.setListData(state.getAllLanguages().toArray(new String[0]));
            }

            if (yearList.getItemCount() == 0) {
                for (Integer year : state.getAllYears()) {
                    yearList.addItem(year);
                }
            }
        }
    }
}