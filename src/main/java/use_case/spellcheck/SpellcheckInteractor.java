package use_case.spellcheck;

import java.util.List;

/**
 * Interactor for the spellcheck use case. Gets suggestions and passes them to SpellcheckPresenter.
 */
public class SpellcheckInteractor implements SpellcheckInputBoundary {
    private final SpellcheckDataAccessInterface spellcheckDataAccess;
    private final SpellcheckOutputBoundary spellcheckOutputBoundary;

    /**
     * Constructs a SpellcheckInteractor object.
     * @param spellcheckDataAccess the SpellcheckDataAccessInterface object that interacts with the API
     * @param spellcheckOutputBoundary the SpellcheckOutputBoundary object for presenting suggestions
     */
     public SpellcheckInteractor(SpellcheckDataAccessInterface spellcheckDataAccess,
                                 SpellcheckOutputBoundary spellcheckOutputBoundary) {
         this.spellcheckDataAccess = spellcheckDataAccess;
         this.spellcheckOutputBoundary = spellcheckOutputBoundary;
     }

    /**
     * Implements the execute method from SpellcheckInputBoundary. Executes the spellchecking action.
     * @param inputData the text to be checked
     */
     @Override
     public void execute(SpellcheckInputData inputData) {
     try {
         String text = inputData.getText();
         List<String> suggestions = this.spellcheckDataAccess.getSuggestions(text);
         SpellcheckOutputData results = new SpellcheckOutputData(suggestions);
         this.spellcheckOutputBoundary.prepareSuccessView(results);
     }
     catch (Exception e) {
        this.spellcheckOutputBoundary.prepareFailView("Retrieving suggestions failed: " + e.getMessage());
     }
     }
}




