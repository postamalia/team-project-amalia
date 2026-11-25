package use_case.load_fonts;

import java.util.List;

public interface FontDataAccessInterface {
    /*Font names for the dropdown. Got back into the installed fonts if failed*/
    List<String> getFontFamilies();

    /*Makes the font usable by the program*/
    boolean makeAvailable(String family);
}
