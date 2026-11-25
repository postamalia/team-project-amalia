package use_case.format_text;

import entity.TextStyle;
import use_case.load_fonts.FontDataAccessInterface;

public class FormatTextInteractor implements FormatTextInputBoundary {
    private final FormatTextOutputBoundary presenter;
    private final FontDataAccessInterface fontDataAccess;

    public FormatTextInteractor(FormatTextOutputBoundary presenter, FontDataAccessInterface fontDataAccess){
        this.presenter = presenter;
        this.fontDataAccess = fontDataAccess;
    }

    @Override
    public void execute(FormatTextInputData in){
        TextStyle style = in.getCurrentStyle();
        TextStyle updated;
        switch(in.getAction()){
            case TOGGLE_BOLD:
                updated = style.toggleBold();
                break;
            case TOGGLE_ITALIC:
                updated = style.toggleItalic();
                break;
            case CHANGE_FONT:
                fontDataAccess.makeAvailable(in.getNewFont());
                updated = style.withFont(in.getNewFont());
                break;
            case REPORT_STYLE:
                updated = style;
                break;
            default:
                return;
        }
        presenter.prepareSuccessView(new FormatTextOutputData(in.getStart(), in.getEnd(), updated, in.getAction()));
    }
}
