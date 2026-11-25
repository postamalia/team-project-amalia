package interface_adapter.load_fonts;

import interface_adapter.editor.EditorViewModel;
import use_case.load_fonts.LoadFontsOutputBoundary;
import use_case.load_fonts.LoadFontsOutputData;

public class LoadFontsPresenter implements LoadFontsOutputBoundary{
    private final EditorViewModel viewModel;

    public LoadFontsPresenter(EditorViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(LoadFontsOutputData outputData){
        viewModel.setFonts(outputData.getFamilies());
        viewModel.fireFontsLoaded();
    }
}