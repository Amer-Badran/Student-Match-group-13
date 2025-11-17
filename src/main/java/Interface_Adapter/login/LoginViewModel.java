package Interface_Adapter.login;

import Interface_Adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {
    public LoginViewModel() {
        super("Login");
        setState(new LoginState());
    }
}
