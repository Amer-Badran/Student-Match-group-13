package Interface_Adapter.findmatches;

import Interface_Adapter.ViewModel;

public class FindMatchesViewModel extends ViewModel<FindMatchesState> {

    public FindMatchesViewModel() {
        super("findMatches");
        setState(new FindMatchesState());
    }
}