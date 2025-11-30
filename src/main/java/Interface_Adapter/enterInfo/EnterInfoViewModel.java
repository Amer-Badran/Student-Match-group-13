package Interface_Adapter.enterInfo;

import Interface_Adapter.ViewModel;

public class EnterInfoViewModel extends ViewModel<EnterInfoState> {

    public EnterInfoViewModel() {
        super("EnterInfo");
        this.setState(new EnterInfoState());
    }
}
