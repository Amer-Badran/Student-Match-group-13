package Interface_Adapter.chat;

import java.util.ArrayList;

public class ChatState {
    private String Sender;
    private String Other;
    private String error = null;
    private ArrayList<String> log = new ArrayList<String>();


    public ArrayList<String> getLog() {
        return log;
    }


    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOther() {
        return Other;
    }

    public String getSender() {
        return Sender;
    }

    public void setOther(String other) {
        Other = other;
    }

    public void setSender(String sender) {
        Sender = sender;
    }
}
