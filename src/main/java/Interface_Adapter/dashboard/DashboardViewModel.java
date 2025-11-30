package Interface_Adapter.dashboard;

import Interface_Adapter.ViewModel;

public class DashboardViewModel extends ViewModel<DashboardState> {
    public DashboardViewModel() {
        super("dashboard");
        setState(new DashboardState());
    }
}
