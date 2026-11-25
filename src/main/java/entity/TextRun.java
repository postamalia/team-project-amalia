package entity;

/**A range of text [start, end) that has one style*/
public class TextRun {
    private final int start;
    private final int end;
    private final TextStyle style;

    public TextRun(int start, int end, TextStyle style){
        this.start = start;
        this.end = end;
        this.style = style;
    }

    public int getStart(){
        return start;
    }
    public int getEnd(){
        return end;
    }
    public TextStyle getStyle(){
        return style;
    }
}
