package use_case.spellcheck;

public interface SpellcheckOutputBoundary {

    /**
     * Prepares the success view for the spellcheck use case.
     * @param outputData the output data
     */
    void prepareSuccessView(SpellcheckOutputData outputData);

    /**
     * Prepares the failure view for the spellcheck use case.
     * @param errorMessage the description of the failure
     */
     void prepareFailView(String errorMessage);
}
