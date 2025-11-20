package Interface_Adapter.EnterInfo;

import Interface_Adapter.ViewModel;

public class EnterInfoViewModel extends ViewModel<EnterInfoState> {

    public EnterInfoViewModel() {
        super("enter info to match");
        this.setState(new EnterInfoState());
    }
}
