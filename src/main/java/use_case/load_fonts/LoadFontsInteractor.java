package use_case.load_fonts;

import java.util.List;

public class LoadFontsInteractor implements LoadFontsInputBoundary {
    private final FontDataAccessInterface fontDataAccess;
    private final LoadFontsOutputBoundary presenter;

    public LoadFontsInteractor(FontDataAccessInterface fontDataAccess, LoadFontsOutputBoundary presenter){
        this.fontDataAccess = fontDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(){
        presenter.prepareSuccessView(new LoadFontsOutputData(fontDataAccess.getFontFamilies()));
    }
}
