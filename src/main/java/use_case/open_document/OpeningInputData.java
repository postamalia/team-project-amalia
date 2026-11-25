package use_case.open_document;

import entity.User;

public class OpeningInputData {

    private final String documentName;
    private final User accessor;

    public OpeningInputData(String documentName, User accessor) {
        this.documentName = documentName;
        this.accessor = accessor;
    }

    String getDocumentName() {
        return documentName;
    }

    User getAccessor() {
        return accessor;
    }
}
