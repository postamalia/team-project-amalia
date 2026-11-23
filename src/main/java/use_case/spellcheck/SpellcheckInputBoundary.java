package use_case.spellcheck;

/**
 * Input boundary for spellcheck action.
 */

public interface SpellcheckInputBoundary {

    /**
     * Executes the spellchecking action.
     * @param inputData the text to be checked
     */
    void execute(SpellcheckInputData inputData);
}
