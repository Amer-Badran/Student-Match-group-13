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
    private final File Courses;
    private final File Programs;
    private final JSONParser parser = new JSONParser();
    private ArrayList<String> courses_list = new ArrayList<>();
    private ArrayList<String> program_list = new ArrayList<>();
    private ArrayList<String> type_list = new ArrayList<>();
    private HashMap<String, String> programs_map = new HashMap<>();
    private int years;
    private ArrayList<String> hobbies= new ArrayList<>();
    private ArrayList<String>  languages= new ArrayList<>();
    private HashMap<String, Double> weights = new HashMap<>();

    private final JTextField nameField = new JTextField(15);
    // private final JTextField weights = new JTextField(20);
    private final JTextField yearOfStudy = new JTextField(15);
    private final JComboBox Courses_drop;
    //private final JComboBox Languages_drop;

    private final JComboBox Program_drop;
    private final JComboBox program_type;
    private final JComboBox program_count;
    private final JComboBox hobbies_drop;
    private final JComboBox languages_drop;
    private final JLabel errorLabel = new JLabel();
    private final JLabel infoLabel = new JLabel();

    private final JButton saveButton = new JButton("Save profile");

    public EnterInfoView(EnterInfoViewModel viewModel) {

        this.controller = null;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);


        // loading course data from Julia's JSON
        Courses = new File("uoft_courses_stgeorge.json");
        Object obj;
        try (FileReader reader = new FileReader(Courses)) {
            obj = parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray course_array = (JSONArray) obj;
        final JPanel accumulation = new JPanel();
        JLabel choices = new JLabel("");
        accumulation.add(choices);


        ArrayList<String> codes = new ArrayList<>();
        for (Object objs : course_array) {
            JSONObject user = (JSONObject) objs;
            codes.add((String) user.get("code"));}
        Courses_drop = new JComboBox(codes.toArray(new String[0]));
        // end of course data


        // loading program data from Julia's JSON
        Programs = new File("uoft_programs_stgeorge.json");
        Object Pobj;
        try (FileReader reader = new FileReader(Programs)) {
            Pobj = parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray program_array = (JSONArray) Pobj;
        JLabel Pchoices = new JLabel("");
        accumulation.add(Pchoices);


        ArrayList<String> Pcodes = new ArrayList<>();
        for (Object objs : program_array) {
            JSONObject user = (JSONObject) objs;
            Pcodes.add((String) user.get("name"));}
        Program_drop = new JComboBox(Pcodes.toArray(new String[0]));

        // end of program data



        File HmyObj = new File("hobbies.txt");
        ArrayList<String> hobby = new ArrayList<>();

        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(HmyObj)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                hobby.add(data);

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


//        File LmyObj = new File("languages.txt");
//        ArrayList<String> language = new ArrayList<>();
//
//        // try-with-resources: Scanner will be closed automatically
//        try (Scanner myReader = new Scanner(LmyObj)) {
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                language.add(data);
//
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }

        ArrayList<String> types = new ArrayList<>();
        types.add("Minor");
        types.add("Specialist");
        types.add("Major");

        program_type= new JComboBox<>(types.toArray(new String[0]));
        JLabel Tchoices = new JLabel("");
        accumulation.add(Tchoices);

        ArrayList<String> count = new ArrayList<>();
        types.add("1");
        types.add("2");
        types.add("3");
        program_count = new JComboBox<>(count.toArray(new String[0]));


//        hobby.add("sports");
//        hobby.add("arts");
//        hobby.add("science");
//        hobby.add("electronics");
//        hobby.add("dancing");
//        hobby.add("video games");
//        hobby.add("reading");
//        hobby.add("fishing");
//        hobby.add("music");
//        hobby.add("fashion");
        hobbies_drop = new JComboBox<>(hobby.toArray(new String[0]));
        final JPanel Haccumulation = new JPanel();
        JLabel Hchoices = new JLabel("");
        Haccumulation.add(Hchoices);



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
        final JPanel Laccumulation = new JPanel();
        JLabel Lchoices = new JLabel("");
        Laccumulation.add(Lchoices);


        languages_drop.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String choice = (String)  languages_drop.getSelectedItem();
                        languages.add(choice);
                        Lchoices.setText(Lchoices.getText() + " " + choice);

                        // Refresh UI
                        Laccumulation.revalidate();
                        Laccumulation.repaint();

                    }
                }
        );


        hobbies_drop.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String choice = (String)  hobbies_drop.getSelectedItem();
                        hobbies.add(choice);
                        Hchoices.setText(Hchoices.getText() + " " + choice);

                        // Refresh UI
                        Haccumulation.revalidate();
                        Haccumulation.repaint();

                    }
                }
        );









        Courses_drop.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String choice = (String)  Courses_drop.getSelectedItem();
                        courses_list.add(choice);
                        choices.setText(choices.getText() + " " + choice);

                        // Refresh UI
                        accumulation.revalidate();
                        accumulation.repaint();

                    }
                }
        );


        Program_drop.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String choice = (String)  Program_drop.getSelectedItem();
                        program_list.add(choice);
                        Pchoices.setText(Pchoices.getText() + " " + choice);

                        // Refresh UI
                        accumulation.revalidate();
                        accumulation.repaint();

                    }
                }
        );
        program_type.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String choice = (String)  program_type.getSelectedItem();
                        type_list.add(choice);
                        Tchoices.setText(Tchoices.getText() + " " + choice);

                        // Refresh UI
                        accumulation.revalidate();
                        accumulation.repaint();

                    }
                }
        );



        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JPanel usernameInfo = new JPanel();
        final JPanel drop_academic = new JPanel();
        final JPanel LanguageInfo = new JPanel();
        final JPanel hobbyInfo = new JPanel();



        usernameInfo.add(new JLabel("what year are you in  : "));
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


    @Override
    public void actionPerformed(ActionEvent e) {
        // username should be set in EnterInfo after Profile
        //String username = viewModel.getState().username;
        for(int i = 0; i <program_list.size();i++){
            programs_map.put(program_list.get(i),type_list.get(i));
        }
        years =Integer.parseInt(yearOfStudy.getText());
        String userName = viewModel.getState().getUsername();
        // Default {"Courses" : FIRST, "Programs" : SECOND, "YearOfStudy" : THIRD, "Languages" FOURTH:, "Hobbies" : FIFTH}
        // 0.35, 0.25, 0.2, 0.12, 0.08
        weights.put("Courses",0.35);weights.put("Programs",0.25);
        weights.put("YearOfStudy",0.2);weights.put("Languages",0.12);
        weights.put("Hobbies",0.08);
        try {
            controller.execute(

                    userName, // EXAMPLE : getting the Name from the name field E
                    courses_list,
                    programs_map,
                    years,
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EnterInfoState state = (EnterInfoState) evt.getNewValue();

//        errorLabel.setText(state.errorMessage);
//        infoLabel.setText(state.infoMessage);
//
//        nameField.setText(state.name);
////        countryField.setText(state.countryOfOrigin);
////        emailField.setText(state.email);
////        instagramField.setText(state.instagram);
////        phoneField.setText(state.phone);
////        bioArea.setText(state.bio);
    }
    public void setEnterInfoController(EnterInfoController controller) {
        this.controller = controller;
    }
}
