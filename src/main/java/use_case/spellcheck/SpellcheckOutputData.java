package use_case.spellcheck;

import java.util.List;

/**
 * Output data holder for spellcheck use case.
 */

public class SpellcheckOutputData {
    private final List<String> suggestions;

    /**
     * Constructs a SpellcheckOutputData object.
     * @param suggestions the spellchecker suggestions
     */
    public SpellcheckOutputData(List<String> suggestions) {
    this.suggestions = suggestions;
    }

    /**
     * Gets the list of spellchecker suggestions belonging to this SpellcheckOutputData object.
     * @return the list of spellchecker suggestions
     */
    public List<String> getSuggestions() {
    return this.suggestions;
    }
}
