package use_case.format_text;

import entity.TextStyle;

public class FormatTextOutputData {
    private final int start;
    private final int end;
    private final TextStyle newStyle;
    private final FormatAction action;

    public FormatTextOutputData(int start, int end, TextStyle newStyle, FormatAction action){
        this.start = start;
        this.end = end;
        this.newStyle = newStyle;
        this.action = action;
    }

    public int getStart(){
        return start;
    }
    public int getEnd(){
        return end;
    }
    public TextStyle getNewStyle(){
        return newStyle;
    }
    public FormatAction getAction(){
        return action;
    }
}
