package use_case.format_text;

import entity.TextStyle;

public class FormatTextInputData {
    private final int start;
    private final int end;
    private final TextStyle currentStyle;
    private final FormatAction action;
    private final String newFont;

    public FormatTextInputData(int start, int end, TextStyle currentStyle, FormatAction action, String newFont){
        this.start = start;
        this.end = end;
        this.currentStyle = currentStyle;
        this.action = action;
        this.newFont = newFont;
    }

    public int getStart(){
        return start;
    }
    public int getEnd(){
        return end;
    }
    public TextStyle getCurrentStyle(){
        return currentStyle;
    }
    public FormatAction getAction(){
        return action;
    }
    public String getNewFont(){
        return newFont;
    }
}
