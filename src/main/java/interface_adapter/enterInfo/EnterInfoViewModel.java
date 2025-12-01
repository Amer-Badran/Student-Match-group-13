package interface_adapter.enterInfo;

import interface_adapter.ViewModel;

public class EnterInfoViewModel extends ViewModel<EnterInfoState> {

    public EnterInfoViewModel() {
        super("EnterInfo");
        this.setState(new EnterInfoState());
    }
}
