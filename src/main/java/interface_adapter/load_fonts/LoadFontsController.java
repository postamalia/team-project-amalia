package interface_adapter.load_fonts;

import use_case.load_fonts.LoadFontsInputBoundary;

public class LoadFontsController {
    private final LoadFontsInputBoundary interactor;

    public LoadFontsController(LoadFontsInputBoundary interactor){
        this.interactor = interactor;
    }

    public void execute(){
        interactor.execute();
    }
}