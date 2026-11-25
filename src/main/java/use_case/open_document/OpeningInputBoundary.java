package use_case.open_document;

/**
 * Input Boundary for actions which are related to opening a document.
 */
public interface OpeningInputBoundary {

    /**
     * Executes the opening a file use case.
     * @param openingInputData the input data
     */
    void execute(OpeningInputData openingInputData);

}
