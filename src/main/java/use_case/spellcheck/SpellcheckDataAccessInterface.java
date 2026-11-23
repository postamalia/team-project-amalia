package use_case.spellcheck;

import java.util.List;

/**
 * Data access interface for spellcheck use case.
 */

public interface SpellcheckDataAccessInterface {

    /**
     * Gets suggestions for text.
     * @param text the text to be checked
     * @return the list of suggestions from the API
     * @throws Exception if reaching the service fails
     */
    List<String> getSuggestions(String text) throws Exception;
}