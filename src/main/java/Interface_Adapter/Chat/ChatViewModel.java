package Interface_Adapter.Chat;

import Interface_Adapter.ViewModel;

public class ChatViewModel extends ViewModel<ChatState> {
    public ChatViewModel() {
        super("chat");
        setState(new ChatState());
    }
}
