package interface_adapter.signup;

import interface_adapter.ViewModel;

public class SignupViewModel extends ViewModel<SignupState> {


    public SignupViewModel() {
        super("Sign up");
        setState(new SignupState());
    }
}
