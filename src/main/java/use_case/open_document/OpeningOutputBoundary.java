package use_case.open_document;

public interface OpeningOutputBoundary {

    /**
     * Prepares the success view for the opening a document use case.
     * @param outputData the output data
     */
    void prepareSuccessView(OpeningOutputData outputData);

    /**
     * Prepares the failure view for the opening a document use case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
