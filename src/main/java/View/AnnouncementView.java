package View;

import interface_adapter.announcement.AnnouncementController;
import interface_adapter.announcement.AnnouncementState;
import interface_adapter.announcement.AnnouncementViewModel;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class AnnouncementView extends JPanel implements ActionListener, PropertyChangeListener {



    private AnnouncementController controller;
    final private AnnouncementViewModel announcementViewModel;
    private JButton Back;
    private JButton send;
    private JTextField write;
    private ArrayList<String> log;
    private JScrollPane scrollPanes;
    private JPanel ChatHistory;
    private JTextArea textArea;

    public AnnouncementView(AnnouncementViewModel announcementViewModel){
        this.controller = null;
        this.announcementViewModel = announcementViewModel;
        this.announcementViewModel.addPropertyChangeListener(this);

        final AnnouncementState state = announcementViewModel.getState();
        log = announcementViewModel.getState().getAnnouncementList();

        JPanel interactions = new JPanel();


        textArea = new JTextArea(10, 40);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        // Add the text area to a JScrollPane with a vertical scrollbar
        scrollPanes = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        ChatHistory = new JPanel();
        ChatHistory.setLayout(new BoxLayout(ChatHistory, BoxLayout.Y_AXIS));
        textArea.setText("");
        if(!(log == null)){
            for(String msg:log) {
                    textArea.append(msg +'\n');

                }

        }


        Back = new JButton("back");
        send = new JButton("send");
        write = new JTextField(30);
        interactions.add(Back);
        interactions.add(write);
        interactions.add(send);
        send.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String message = write.getText();
                        write.setText(null);
                        String name = announcementViewModel.getState().getUsername();

                        try {
                            controller.execute(name,message);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }

                        textArea.setText("");

                        ArrayList<String> logd = state.getAnnouncementList();
                            for(String msg:logd) {
                                textArea.append(msg +'\n');
                        }
                        SwingUtilities.invokeLater(() -> {
                            JScrollBar bar = scrollPanes.getVerticalScrollBar();
                            bar.setValue(bar.getMaximum());
                        });



                    }
                }
        );

        Back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.BackToHomeView();


                    }
                }
        );




        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

//        this.add(title);
        this.add(scrollPanes);
        this.add(interactions);
//        this.add(repeatPasswordInfo);






    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AnnouncementState state = (AnnouncementState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            state.setError(null);
        }
        else if(!(state.getAnnouncementList() == null)){


            textArea.setText("");

            ArrayList<String> logd = state.getAnnouncementList();
            for(String msg:logd) {
                textArea.append(msg +'\n');
            }
            SwingUtilities.invokeLater(() -> {
                JScrollBar bar = scrollPanes.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
            });


            }}


    public void setAnnouncementController(AnnouncementController controller) {
        this.controller = controller;
    }
}


