package entity;

public class TextStyle {
    private final boolean bold;
    private final boolean italic;
    private final String fontFamily;

    public TextStyle(boolean bold, boolean italic, String fontFamily){
        this.bold = bold;
        this.italic = italic;
        this.fontFamily = fontFamily;
    }

    public boolean isBold(){
        return bold;
    }
    public boolean isItalic(){
        return italic;
    }
    public String getFontFamily(){
        return fontFamily;
    }
    public TextStyle toggleBold(){
        return new TextStyle(!bold, italic, fontFamily);
    }
    public TextStyle toggleItalic(){
        return new TextStyle(bold, !italic, fontFamily);
    }
    public TextStyle withFont(String family){
        return new TextStyle(bold, italic, family);
    }
}
