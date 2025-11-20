package Interface_Adapter.EnterInfo;

import Interface_Adapter.ViewModel;

public class EnterInfoViewModel extends ViewModel<EnterInfoState> {

    public EnterInfoViewModel() {
        super("EnterInfo");
        this.setState(new EnterInfoState());
    }
}
