package Interface_Adapter.signup;

import Interface_Adapter.ViewModel;

public class SignupViewModel extends ViewModel<SignupState> {


    public SignupViewModel() {
        super("Sign up");
        setState(new SignupState());
    }
}
