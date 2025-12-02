package View;

import interface_adapter.enterInfo.EnterInfoController;
import interface_adapter.enterInfo.EnterInfoState;
import interface_adapter.enterInfo.EnterInfoViewModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EnterInfoView extends JPanel implements ActionListener, PropertyChangeListener {

    private EnterInfoController controller;
    private final EnterInfoViewModel viewModel;

    // File and JSON tools
    private final File Courses;
    private final File Programs;
    private final JSONParser parser = new JSONParser();

    // Data Accumulators
    private ArrayList<String> courses_list = new ArrayList<>();
    private ArrayList<String> program_list = new ArrayList<>();
    private ArrayList<String> type_list = new ArrayList<>();
    private HashMap<String, String> programs_map = new HashMap<>();
    private int years;
    private ArrayList<String> hobbies = new ArrayList<>();
    private ArrayList<String> languages = new ArrayList<>();
    private HashMap<String, Double> weights = new HashMap<>();

    // UI Components
    private final JTextField nameField = new JTextField(15);
    private final JTextField yearOfStudy = new JTextField(15);

    private final JComboBox Courses_drop;
    private final JComboBox Program_drop;
    private final JComboBox program_type;
    private final JComboBox program_count;
    private final JComboBox hobbies_drop;
    private final JComboBox languages_drop;

    private final JLabel errorLabel = new JLabel();
    private final JLabel infoLabel = new JLabel();
    private final JButton saveButton = new JButton("Save profile");

    // NEW!!: Class-level labels to display choices (so that they can be cleared later in the event of error)
    private JLabel choices = new JLabel("");
    private JLabel Pchoices = new JLabel("");
    private JLabel Tchoices = new JLabel("");
    private JLabel Hchoices = new JLabel("");
    private JLabel Lchoices = new JLabel("");

    // NEW!!: Class-level panels (so we they can be revalidated when clearing)
    private final JPanel accumulation = new JPanel();
    private final JPanel Haccumulation = new JPanel();
    private final JPanel Laccumulation = new JPanel();

    // NEW!!: Safety flag to prevent listeners from firing during reset
    private boolean isResetting = false;

    public EnterInfoView(EnterInfoViewModel viewModel) {

        this.controller = null;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        // --- Loading Course Data ---
        Courses = new File("uoft_courses_stgeorge.json");
        Object obj;
        try (FileReader reader = new FileReader(Courses)) {
            obj = parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray course_array = (JSONArray) obj;

        // Add the class-level label to the panel
        accumulation.add(choices);

        ArrayList<String> codes = new ArrayList<>();
        for (Object objs : course_array) {
            JSONObject user = (JSONObject) objs;
            codes.add((String) user.get("code"));
        }
        Courses_drop = new JComboBox(codes.toArray(new String[0]));
        // --- End Course Data ---

        // --- Loading Program Data ---
        Programs = new File("uoft_programs_stgeorge.json");
        Object Pobj;
        try (FileReader reader = new FileReader(Programs)) {
            Pobj = parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray program_array = (JSONArray) Pobj;

        // Add the class-level label to the panel
        accumulation.add(Pchoices);

        ArrayList<String> Pcodes = new ArrayList<>();
        for (Object objs : program_array) {
            JSONObject user = (JSONObject) objs;
            Pcodes.add((String) user.get("name"));
        }
        Program_drop = new JComboBox(Pcodes.toArray(new String[0]));
        // --- End Program Data ---

        // --- Hobbies Loading ---
        File HmyObj = new File("hobbies.txt");
        ArrayList<String> hobby = new ArrayList<>();
        try (Scanner myReader = new Scanner(HmyObj)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                hobby.add(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // --- Program Types ---
        ArrayList<String> types = new ArrayList<>();
        types.add("Minor");
        types.add("Specialist");
        types.add("Major");

        program_type = new JComboBox<>(types.toArray(new String[0]));
        accumulation.add(Tchoices); // Add class-level label

        ArrayList<String> count = new ArrayList<>();
        count.add("1");
        count.add("2");
        count.add("3");
        program_count = new JComboBox<>(count.toArray(new String[0]));

        hobbies_drop = new JComboBox<>(hobby.toArray(new String[0]));
        Haccumulation.add(Hchoices); // Add class-level label

        // --- Languages Loading ---
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
        languages_drop = new JComboBox<>(language.toArray(new String[0]));
        Laccumulation.add(Lchoices); // Add class-level label

        // --- LISTENERS (!Updated with isResetting check!) ---

        languages_drop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isResetting) return; // Stop if we are clearing fields to avoid choosing English by default
                String choice = (String) languages_drop.getSelectedItem();
                languages.add(choice);
                Lchoices.setText(Lchoices.getText() + " " + choice);
                Laccumulation.revalidate();
                Laccumulation.repaint();
            }
        });

        hobbies_drop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isResetting) return; // Stop if we are clearing fields
                String choice = (String) hobbies_drop.getSelectedItem();
                hobbies.add(choice);
                Hchoices.setText(Hchoices.getText() + " " + choice);
                Haccumulation.revalidate();
                Haccumulation.repaint();
            }
        });

        Courses_drop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isResetting) return; // Stop if we are clearing fields
                String choice = (String) Courses_drop.getSelectedItem();
                courses_list.add(choice);
                choices.setText(choices.getText() + " " + choice);
                accumulation.revalidate();
                accumulation.repaint();
            }
        });

        Program_drop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isResetting) return; // Stop if we are clearing fields
                String choice = (String) Program_drop.getSelectedItem();
                program_list.add(choice);
                Pchoices.setText(Pchoices.getText() + " " + choice);
                accumulation.revalidate();
                accumulation.repaint();
            }
        });

        program_type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isResetting) return; // Stop if we are clearing fields
                String choice = (String) program_type.getSelectedItem();
                type_list.add(choice);
                Tchoices.setText(Tchoices.getText() + " " + choice);
                accumulation.revalidate();
                accumulation.repaint();
            }
        });

        // --- Layout Setup ---
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JPanel usernameInfo = new JPanel();
        final JPanel drop_academic = new JPanel();
        final JPanel LanguageInfo = new JPanel();
        final JPanel hobbyInfo = new JPanel();

        usernameInfo.add(new JLabel("what year are you in : "));
        usernameInfo.add(yearOfStudy);

        LanguageInfo.add(new JLabel("what languages do you speak : "));
        LanguageInfo.add(languages_drop);
        LanguageInfo.add(Laccumulation);

        hobbyInfo.add(new JLabel("what are your hobbies : "));
        hobbyInfo.add(hobbies_drop);
        hobbyInfo.add(Haccumulation);

        add(usernameInfo);
        add(LanguageInfo);
        add(hobbyInfo);
        drop_academic.add(Courses_drop);
        drop_academic.add(Program_drop);
        drop_academic.add(program_type);
        add(drop_academic);
        add(accumulation);
        add(saveButton);
        add(errorLabel);
        add(infoLabel);

        saveButton.addActionListener(this);
    }

    // --- NEW! HELPER METHOD TO CLEAR FIELDS ---
    private void clearFields() {
        isResetting = true; // Lock listeners

        // 1. Clear Data Structures
        courses_list.clear();
        program_list.clear();
        type_list.clear();
        programs_map.clear();
        hobbies.clear();
        languages.clear();

        // 2. Clear Visual Text Fields
        yearOfStudy.setText("");

        // 3. Clear Visual Choice Labels
        choices.setText("");
        Pchoices.setText("");
        Tchoices.setText("");
        Hchoices.setText("");
        Lchoices.setText("");

        // 4. Reset Dropdowns to default index
        if (Courses_drop.getItemCount() > 0) Courses_drop.setSelectedIndex(0);
        if (Program_drop.getItemCount() > 0) Program_drop.setSelectedIndex(0);
        if (program_type.getItemCount() > 0) program_type.setSelectedIndex(0);
        if (hobbies_drop.getItemCount() > 0) hobbies_drop.setSelectedIndex(0);
        if (languages_drop.getItemCount() > 0) languages_drop.setSelectedIndex(0);

        // 5. Refresh Panels to show empty labels
        accumulation.revalidate();
        accumulation.repaint();
        Haccumulation.revalidate();
        Haccumulation.repaint();
        Laccumulation.revalidate();
        Laccumulation.repaint();

        isResetting = false; // Unlock listeners
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            for (int i = 0; i < program_list.size(); i++) {
                programs_map.put(program_list.get(i), type_list.get(i));
            }
            try {
                years = Integer.parseInt(yearOfStudy.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String userName = viewModel.getState().getUsername();

            // Weights logic
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
                        years,
                        hobbies,
                        languages,
                        weights
                );
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EnterInfoState state = (EnterInfoState) evt.getNewValue();

        // If there is an error message
        if (state.getFailedSaveMessage() != null && !state.getFailedSaveMessage().isEmpty()) {

            // 1. Show Error Popup
            JOptionPane.showMessageDialog(this, state.getFailedSaveMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            // 2. Clear all user input
            clearFields();

            // 3. Reset error state
            state.setFailedSaveMessage("");
        }
    }

    public void setEnterInfoController(EnterInfoController controller) {
        this.controller = controller;
    }
}