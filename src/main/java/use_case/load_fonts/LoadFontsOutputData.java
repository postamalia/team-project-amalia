package use_case.load_fonts;

import java.util.List;

public class LoadFontsOutputData {
    private final List<String> families;

    public LoadFontsOutputData(List<String> families){
        this.families = families;
    }

    public List<String> getFamilies(){
        return families;
    }
}
