package use_case.open_document;

import entity.Document;

public class OpeningOutputData {

    private final Document document;

    public OpeningOutputData(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }
}
