package use_case.open_document;

import entity.Document;
import entity.User;

public class OpeningInteractor implements OpeningInputBoundary {
    private final OpeningDataAccessInterface documentDataAccessObject;
    private final OpeningOutputBoundary userPresenter;

    public OpeningInteractor(OpeningDataAccessInterface documentDataAccessInterface,
                             OpeningOutputBoundary openingOutputBoundary) {
        this.documentDataAccessObject = documentDataAccessInterface;
        this.userPresenter = openingOutputBoundary;
    }

    @Override
    public void execute(OpeningInputData openingInputData) {
        String docName = openingInputData.getDocumentName();
        User accessor = openingInputData.getAccessor();
        if (!documentDataAccessObject.existsByName(docName)) {
            userPresenter.prepareFailView("No document with that name exists.");
        }
        else if (documentDataAccessObject.get(docName).getCreator() != accessor &&
                !documentDataAccessObject.get(docName).getAccessedBy().contains(accessor)) {
            userPresenter.prepareFailView("User does not have access to that document.");
        }
        else {
            final OpeningOutputData outputData = new OpeningOutputData(documentDataAccessObject.get(docName));
            userPresenter.prepareSuccessView(outputData);
        }
    }
}
