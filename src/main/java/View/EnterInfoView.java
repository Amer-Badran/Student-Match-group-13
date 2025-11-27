package View;

import Interface_Adapter.EnterInfo.EnterInfoController;
import Interface_Adapter.EnterInfo.EnterInfoState;
import Interface_Adapter.EnterInfo.EnterInfoViewModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnterInfoView extends JPanel implements ActionListener, PropertyChangeListener {

    private EnterInfoController controller;
    private final EnterInfoViewModel viewModel;
    private final File Courses;
    private final File Programs;
    private final JSONParser parser = new JSONParser();
    private final ArrayList<String> courses_list = new ArrayList<>();
    private final ArrayList<String> program_list = new ArrayList<>();
    private final ArrayList<String> type_list = new ArrayList<>();
    private final HashMap<String, String> programs_map = new HashMap<>();
    private final ArrayList<String> hobbies = new ArrayList<>();
    private final ArrayList<String> languages = new ArrayList<>();
    private final HashMap<String, Double> weights = new HashMap<>();

    private final JComboBox<String> yearDropdown;
    private final JComboBox<String> coursesDropdown;
    private final JComboBox<String> programDropdown;
    private final JComboBox<String> hobbiesDropdown;
    private final JComboBox<String> languagesDropdown;

    private final DefaultListModel<String> courseListModel = new DefaultListModel<>();
    private final DefaultListModel<String> programListModel = new DefaultListModel<>();
    private final DefaultListModel<String> hobbyListModel = new DefaultListModel<>();
    private final DefaultListModel<String> languageListModel = new DefaultListModel<>();

    private final JLabel errorLabel = new JLabel();

    private final JButton saveButton = new JButton("Create Profile");

    public EnterInfoView(EnterInfoViewModel viewModel) {

        this.controller = null;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        Courses = new File("uoft_courses_stgeorge.json");
        Object obj;
        try (FileReader reader = new FileReader(Courses)) {
            obj = parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray course_array = (JSONArray) obj;
        ArrayList<String> codes = new ArrayList<>();
        for (Object objs : course_array) {
            JSONObject user = (JSONObject) objs;
            codes.add((String) user.get("code"));
        }
        coursesDropdown = new JComboBox<>(codes.toArray(new String[0]));

        Programs = new File("uoft_programs_stgeorge.json");
        Object Pobj;
        try (FileReader reader = new FileReader(Programs)) {
            Pobj = parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray program_array = (JSONArray) Pobj;
        ArrayList<String> Pcodes = new ArrayList<>();
        for (Object objs : program_array) {
            JSONObject user = (JSONObject) objs;
            Pcodes.add((String) user.get("name"));
        }
        programDropdown = new JComboBox<>(Pcodes.toArray(new String[0]));

        ArrayList<String> hobby = new ArrayList<>();
        hobby.add("Sports");
        hobby.add("Arts");
        hobby.add("Science");
        hobby.add("Electronics");
        hobby.add("Dancing");
        hobby.add("Video games");
        hobby.add("Reading");
        hobby.add("Fishing");
        hobby.add("Music");
        hobby.add("Fashion");
        hobbiesDropdown = new JComboBox<>(hobby.toArray(new String[0]));

        ArrayList<String> language = new ArrayList<>();
        language.add("English");
        language.add("French");
        language.add("Arabic");
        language.add("Spanish");
        language.add("Mandarin");
        language.add("Farsi");
        language.add("Urdu");
        language.add("Russian");
        language.add("Japanese");
        language.add("Turkish");
        languagesDropdown = new JComboBox<>(language.toArray(new String[0]));

        ArrayList<String> years = new ArrayList<>();
        years.add("1st");
        years.add("2nd");
        years.add("3rd");
        years.add("4th");
        years.add("Other");
        yearDropdown = new JComboBox<>(years.toArray(new String[0]));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 24, 24, 24));

        final JLabel title = new JLabel("Profile Creation: Preferences");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(Box.createVerticalStrut(20));

        add(labeledCombo("Year of Study", yearDropdown));
        add(Box.createVerticalStrut(16));
        add(selectionSection("Spoken Languages", languagesDropdown, languageListModel, languages));
        add(Box.createVerticalStrut(12));
        add(selectionSection("Hobbies/Interests", hobbiesDropdown, hobbyListModel, hobbies));
        add(Box.createVerticalStrut(12));
        add(selectionSection("Courses", coursesDropdown, courseListModel, courses_list));
        add(Box.createVerticalStrut(12));
        add(programSection());
        add(Box.createVerticalStrut(18));

        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setPreferredSize(new Dimension(200, 36));
        saveButton.setMaximumSize(new Dimension(220, 40));
        saveButton.addActionListener(this);
        add(saveButton);

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(8));
        add(errorLabel);
    }

    private JPanel labeledCombo(String label, JComboBox<String> comboBox) {
        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        final JLabel jLabel = new JLabel(label);
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
        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        final JLabel jLabel = new JLabel(label);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        dropdown.setMaximumSize(new Dimension(220, 32));
        row.add(dropdown);
        row.add(Box.createHorizontalStrut(8));
        final JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(80, 32));
        row.add(addButton);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JList<String> list = new JList<>(listModel);
        final JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 70));
        scrollPane.setMaximumSize(new Dimension(320, 90));

        addButton.addActionListener(e -> {
            String choice = (String) dropdown.getSelectedItem();
            if (choice != null) {
                backingList.add(choice);
                listModel.addElement(choice);
            }
        });

        container.add(jLabel);
        container.add(Box.createVerticalStrut(6));
        container.add(row);
        container.add(Box.createVerticalStrut(6));
        container.add(scrollPane);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        return container;
    }

    private JPanel programSection() {
        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        final JLabel jLabel = new JLabel("Programs (Major/Minor/Specialist)");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        programDropdown.setMaximumSize(new Dimension(220, 32));
        programDropdown.setPreferredSize(new Dimension(220, 32));
        row.add(programDropdown);
        row.add(Box.createHorizontalStrut(10));
        final JButton addButton = new JButton("Add Program");
        addButton.setPreferredSize(new Dimension(130, 32));
        addButton.setMaximumSize(new Dimension(140, 36));
        row.add(addButton);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JList<String> list = new JList<>(programListModel);
        final JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 80));
        scrollPane.setMaximumSize(new Dimension(320, 100));

        addButton.addActionListener(e -> addSelectedProgram());

        container.add(jLabel);
        container.add(Box.createVerticalStrut(6));
        container.add(row);
        container.add(Box.createVerticalStrut(6));
        container.add(scrollPane);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        return container;
    }

    private void addSelectedProgram() {
        String choice = (String) programDropdown.getSelectedItem();
        if (choice != null) {
            String type = askProgramType();
            if (type != null) {
                program_list.add(choice);
                type_list.add(type);
                programListModel.addElement(choice + " (" + type + ")");
            }
        }
    }

    private String askProgramType() {
        final String[] options = new String[]{"Major", "Minor", "Specialist"};
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

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < program_list.size(); i++) {
            programs_map.put(program_list.get(i), type_list.get(i));
        }
        String yearSelection = (String) yearDropdown.getSelectedItem();
        int year = parseYear(yearSelection);
        String userName = viewModel.getState().getUsername();
        weights.put("Courses", 0.35);
        weights.put("Programs", 0.25);
        weights.put("YearOfStudy", 0.2);
        weights.put("Languages", 0.12);
        weights.put("Hobbies", 0.08);
        try {
            controller.execute(
                    userName,
                    courses_list,
                    programs_map,
                    year,
                    hobbies,
                    languages,
                    weights

            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (java.text.ParseException ex) {
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EnterInfoState state = (EnterInfoState) evt.getNewValue();
        errorLabel.setText(state.getFailedSaveMessage());
    }

    public void setEnterInfoController(EnterInfoController controller) {
        this.controller = controller;
    }
}
