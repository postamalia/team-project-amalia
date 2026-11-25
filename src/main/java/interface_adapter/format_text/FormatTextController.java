package interface_adapter.format_text;

import entity.TextStyle;
import use_case.format_text.FormatAction;
import use_case.format_text.FormatTextInputBoundary;
import use_case.format_text.FormatTextInputData;

public class FormatTextController {
    private final FormatTextInputBoundary interactor;

    public FormatTextController(FormatTextInputBoundary interactor){
        this.interactor = interactor;
    }

    public void toggleBold(int start, int end, TextStyle current){
        interactor.execute(new FormatTextInputData(start, end, current, FormatAction.TOGGLE_BOLD, null));
    }

    public void toggleItalic(int start, int end, TextStyle current){
        interactor.execute(new FormatTextInputData(start, end, current, FormatAction.TOGGLE_ITALIC, null));
    }

    public void changeFont(int start, int end, TextStyle current, String newFont){
        interactor.execute(new FormatTextInputData(start, end, current, FormatAction.CHANGE_FONT, newFont));
    }

    public void reportStyle(int start, int end, TextStyle current){
        interactor.execute(new FormatTextInputData(start, end, current, FormatAction.REPORT_STYLE, null));
    }
}
