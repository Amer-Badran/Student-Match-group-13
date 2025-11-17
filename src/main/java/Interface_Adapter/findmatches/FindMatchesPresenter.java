package Interface_Adapter.findmatches;

import Use_Case.findmatches.*;

public class FindMatchesPresenter implements FindMatchesOutputBoundary {

    private final FindMatchesViewModel viewModel;

    public FindMatchesPresenter(FindMatchesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(FindMatchesOutputData outputData) {
        viewModel.setMatches(outputData.getMatches());
        viewModel.setErrorMessage(null);
        viewModel.firePropertyChanged(); // notify UI
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setErrorMessage(errorMessage);
        viewModel.firePropertyChanged();
    }
}
