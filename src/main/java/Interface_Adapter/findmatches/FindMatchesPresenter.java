package Interface_Adapter.findmatches;

import Interface_Adapter.ViewManagerModel;
import Use_Case.findmatches.FindMatchesOutputBoundary;
import Use_Case.findmatches.FindMatchesOutputData;

public class FindMatchesPresenter implements FindMatchesOutputBoundary {

    private final FindMatchesViewModel findMatchesViewModel;
    private final ViewManagerModel viewManagerModel;

    public FindMatchesPresenter(ViewManagerModel viewManagerModel,
                                FindMatchesViewModel viewModel) {
        this.viewManagerModel = viewManagerModel;
        this.findMatchesViewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(FindMatchesOutputData outputData) {
        FindMatchesState state = findMatchesViewModel.getState();
        state.setMatches(outputData.getMatches());
        state.setErrorMessage("");

        findMatchesViewModel.setState(state);
        findMatchesViewModel.firePropertyChange(); // notify UI

        viewManagerModel.setState(findMatchesViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        FindMatchesState state = findMatchesViewModel.getState();
        state.setErrorMessage(errorMessage);

        findMatchesViewModel.setState(state);
        findMatchesViewModel.firePropertyChange();
    }
}
