package use_case.spellcheck;

/**
 * Input data holder for spellcheck use case.
 */

public class SpellcheckInputData {
    private final String text;

    /**
     * Constructs a SpellcheckInputData object.
     * @param text the text to be checked
     */
     public SpellcheckInputData(String text) {
     this.text = text;
     }

    /**
     * Gets the text belonging to this SpellcheckInputData object.
     * @return the text
     */
     public String getText() {
     return this.text;
     }
}
