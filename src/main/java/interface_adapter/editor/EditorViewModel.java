package interface_adapter.editor;

import entity.TextStyle;
import java.util.ArrayList;
import java.util.List;

public class EditorViewModel {
    public interface Listener{
        void onFormatChanged();
        void onFontsLoaded();
    }

    private Listener listener;
    /*Formatting*/
    private int start;
    private int end;
    private Boolean applyBold;
    private Boolean applyItalic;
    private String applyFont;
    private TextStyle shownStyle = new TextStyle(false, false, "Serif");
    private List<String> fonts = new ArrayList<>();

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public void setFormat(int start, int end, Boolean applyBold, Boolean applyItalic, String applyFont, TextStyle shownStyle){
        this.start = start;
        this.end = end;
        this.applyBold = applyBold;
        this.applyItalic = applyItalic;
        this.applyFont = applyFont;
        this.shownStyle = shownStyle;
    }

    public void setFonts(List<String> fonts){
        this.fonts = fonts;
    }

    public void fireFormatChanged(){
        if(listener != null){
            listener.onFormatChanged();
        }
    }

    public void fireFontsLoaded(){
        if(listener != null){
            listener.onFontsLoaded();
        }
    }

    public int getStart(){
        return start;
    }

    public int getEnd(){
        return end;
    }

    public Boolean getApplyBold(){
        return applyBold;
    }

    public Boolean getApplyItalic(){
        return applyItalic;
    }

    public String getApplyFont(){
        return applyFont;
    }

    public TextStyle getShownStyle(){
        return shownStyle;
    }

    public List<String> getFonts(){
        return fonts;
    }
}
