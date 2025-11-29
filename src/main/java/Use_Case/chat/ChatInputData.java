package Use_Case.chat;

public class ChatInputData {
    private String sender;
    private String receiver;
    private String message;
    public ChatInputData(String S,String R,String M){
        this.sender = S;
        this.receiver = R;
        this.message = M;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiver() {
        return receiver;
    }
}
