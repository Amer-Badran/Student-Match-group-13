package interface_adapter.findmatches;

import interface_adapter.ViewModel;

public class FindMatchesViewModel extends ViewModel<FindMatchesState> {

    public FindMatchesViewModel() {
        super("findMatches");
        setState(new FindMatchesState());
    }
}