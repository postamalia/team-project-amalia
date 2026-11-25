package interface_adapter.format_text;

import entity.TextStyle;
import interface_adapter.editor.EditorViewModel;
import use_case.format_text.FormatTextOutputBoundary;
import use_case.format_text.FormatTextOutputData;

public class FormatTextPresenter implements FormatTextOutputBoundary{
    private final EditorViewModel viewModel;

    public FormatTextPresenter(EditorViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(FormatTextOutputData out){
        TextStyle s = out.getNewStyle();
        Boolean applyBold = null;
        Boolean applyItalic = null;
        String applyFont = null;

        switch(out.getAction()){
            case TOGGLE_BOLD:
                applyBold = s.isBold();
                break;
            case TOGGLE_ITALIC:
                applyItalic = s.isItalic();
                break;
            case CHANGE_FONT:
                applyFont = s.getFontFamily();
                break;
            default:
                break;
        }

        viewModel.setFormat(out.getStart(), out.getEnd(), applyBold, applyItalic, applyFont, s);
        viewModel.fireFormatChanged();
    }
}