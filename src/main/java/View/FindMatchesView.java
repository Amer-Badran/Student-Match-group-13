package View;

import entity.Profile;
import interface_adapter.findmatches.FindMatchesController;
import interface_adapter.findmatches.FindMatchesState;
import interface_adapter.findmatches.FindMatchesViewModel;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class FindMatchesView extends JPanel implements PropertyChangeListener {
    private final JButton findMatches;
    private final JButton searchProfile;
    private final JButton back;
    private final JTextField searchBar;
    private JTextArea textArea;
    private JScrollPane scrollPanes;
    private JTextArea textAreaProfile;
    private JScrollPane scrollPanesProfile;
    private  FindMatchesController controller;
    private final FindMatchesViewModel viewModel;

    public FindMatchesView(FindMatchesViewModel findMatchesViewModel){
        this.viewModel = findMatchesViewModel;
        this.controller = null;
        viewModel.addPropertyChangeListener(this);

        findMatches = new JButton("find matches");
        searchProfile = new JButton("search");
        searchBar = new JTextField(15);
        back = new JButton("back");
        JLabel searchInstructions = new JLabel("Enter Username to see their Profile : ");
        JLabel findInstructions = new JLabel("click to find matches : ");


        textArea = new JTextArea(10, 40);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        // Add the text area to a JScrollPane with a vertical scrollbar
        scrollPanes = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        textAreaProfile = new JTextArea(10, 40);
        textAreaProfile.setWrapStyleWord(true);
        textAreaProfile.setLineWrap(true);

        // Add the text area to a JScrollPane with a vertical scrollbar
        scrollPanesProfile = new JScrollPane(textAreaProfile,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomePanel = new JPanel();

        back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.switchToHomeView();
                    }
                }
        );

        findMatches.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = viewModel.getState().getUesrnmae();
                        try {
                            controller.execute(name);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
        );

        searchProfile.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = searchBar.getText();
                        Profile profile = null;
                        try {
                            profile = controller.getProfile(username);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        textAreaProfile.setText("");

                        textAreaProfile.append("Name : " + profile.getName() +"\n");
                        textAreaProfile.append("Nationality : " + profile.getCountryOfOrigin() +"\n");
                        textAreaProfile.append("email : " + profile.getEmail() +"\n");
                        textAreaProfile.append("Instagram : " + profile.getInstagram() +"\n");
                        textAreaProfile.append("Phone : " + profile.getPhone() +"\n");
                        textAreaProfile.append("Bio : " + profile.getBio() +"\n");
                        SwingUtilities.invokeLater(() -> {
                            JScrollBar bar = scrollPanesProfile.getVerticalScrollBar();
                            bar.setValue(bar.getMaximum());
                        });

                    }
                }
        );

        topPanel.add(findInstructions);
        topPanel.add(findMatches);
        middlePanel.add(scrollPanes);
        middlePanel.add(scrollPanesProfile);
        bottomePanel.add(back);
        bottomePanel.add(searchInstructions);
        bottomePanel.add(searchBar);
        bottomePanel.add(searchProfile);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(topPanel);
        this.add(middlePanel);
        this.add(bottomePanel);

    }
    public void setFindMatchesController(FindMatchesController controller) {
        this.controller = controller;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final FindMatchesState state = (FindMatchesState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
            state.setErrorMessage(null);}
        if (state.getMatches() != null) {
            textArea.setText("");
            textArea.append("User Name" + "                     " + "Score " + "\n");
            textArea.append("\n");
            for (String person:state.getMatches().keySet()){
                String result = String.format("%.2f", state.getMatches().get(person));
            textArea.append(person + "                              " + result +"\n");}
            SwingUtilities.invokeLater(() -> {
                JScrollBar bar = scrollPanes.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
            });
        }
    }
}
