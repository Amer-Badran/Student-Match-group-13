package View;

import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class ChatView extends JPanel implements ActionListener, PropertyChangeListener {
    private ChatController controller;
    final private ChatViewModel chatViewModels;
    private JButton Back;
    private JButton send;
    private JTextField write;
    private String receiver;
    private String sender;
    private ArrayList<String> log;
    private JScrollPane scrollPanes;
    private JPanel ChatHistory;
    private JTextArea textArea;

    public ChatView(ChatViewModel chatViewModel){
        this.controller = null;
        this.chatViewModels = chatViewModel;
        this.chatViewModels.addPropertyChangeListener(this);

        final ChatState state = chatViewModels.getState();
        sender = chatViewModels.getState().getSender();
        receiver = chatViewModels.getState().getOther();
        log = chatViewModels.getState().getLog();

        JPanel interactions = new JPanel();


        textArea = new JTextArea(10, 40);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        // Add the text area to a JScrollPane with a vertical scrollbar
         scrollPanes = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        ChatHistory = new JPanel();
        ChatHistory.setLayout(new BoxLayout(ChatHistory, BoxLayout.Y_AXIS));
        if(!(log.isEmpty()|| log == null)){
            for(String msg:log) {
                if(msg.startsWith("*")){
                    String newMessge = msg.replace("*","");
                    textArea.append(receiver+ " : " + newMessge+'\n');
                    //ChatHistory.add(add);
                }
                else{
                    String newMessge = msg.replace("*","");
                    textArea.append(sender+ " : " + newMessge+'\n');
                    //ChatHistory.add(add);

                }
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
                        String other = chatViewModel.getState().getOther();
                        String person = chatViewModel.getState().getSender();
                        try {

                            controller.execute(person,other,message);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }

                        textArea.setText("");

                        String senderd = state.getSender();
                        String receiverd = state.getOther();
                        ArrayList<String> logd = state.getLog();

                        for(String msg:logd) {
                            if(!(msg.startsWith("*"))){
                                textArea.append(senderd+ " : " + msg+'\n');
                                //ChatHistory.add(add);
                            }
                            else{
                                String newMessge = msg.replace("*","");
                                textArea.append(receiverd+ " : " + newMessge+'\n');
                                //ChatHistory.add(add);

                            }
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
                        controller.switchToNotificationView();


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
        final ChatState state = (ChatState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            state.setError(null);
        }
        else if((state.getLog() == null)){


       textArea.setText("");}
        else if(!(state.getLog() == null)){

            String senderd = state.getSender();
            String receiverd = state.getOther();
            ArrayList<String> logd = state.getLog();
            if((logd.isEmpty())){
                SwingUtilities.invokeLater(() -> {
                JScrollBar bar = scrollPanes.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
               });}
            else{

                for(String msg:logd) {
                    if(!(msg.startsWith("*"))){
                        textArea.append(senderd+ " : " + msg+'\n');
                        //ChatHistory.add(add);
                    }
                    else{
                        String newMessge = msg.replace("*","");
                        textArea.append(receiverd+ " : " + newMessge+'\n');
                        //ChatHistory.add(add);

                    }
                }
            SwingUtilities.invokeLater(() -> {
                JScrollBar bar = scrollPanes.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
            });


        }}}


    public void setChatController(ChatController controller) {
        this.controller = controller;
    }
}
