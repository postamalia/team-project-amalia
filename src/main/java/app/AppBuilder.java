package app;

import data_access.GoogleFontsDataAccess;
import interface_adapter.editor.EditorViewModel;
import interface_adapter.format_text.FormatTextController;
import interface_adapter.format_text.FormatTextPresenter;
import interface_adapter.load_fonts.LoadFontsController;
import interface_adapter.load_fonts.LoadFontsPresenter;
import use_case.format_text.FormatTextInteractor;
import use_case.load_fonts.FontDataAccessInterface;
import use_case.load_fonts.LoadFontsInteractor;
import view.EditorView;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // other instance attributes as needed

    /**/
    private final EditorViewModel editorViewModel = new EditorViewModel();
    private final FontDataAccessInterface fontDataAccess = new GoogleFontsDataAccess(System.getenv("GOOGLE_FONTS_API_KEY"));
    private EditorView editorView;
    /**/

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /*Added*/
    public AppBuilder addEditorView(){
        editorView = new EditorView(editorViewModel);
        editorView.setPreferredSize(new Dimension(800, 550));
        cardPanel.add(editorView, "editor");
        return this;
    }

    public AppBuilder addFormatTextUseCase(){
        FormatTextPresenter presenter = new FormatTextPresenter(editorViewModel);
        FormatTextInteractor interactor = new FormatTextInteractor(presenter, fontDataAccess);
        editorView.setFormatController(new FormatTextController(interactor));
        return this;
    }

    public AppBuilder addLoadFontsUseCase(){
        LoadFontsPresenter presenter =  new LoadFontsPresenter(editorViewModel);
        LoadFontsInteractor interactor = new LoadFontsInteractor(fontDataAccess, presenter);
        editorView.setLoadFontsController(new LoadFontsController(interactor));
        return this;
    }
    /**/

    public JFrame build() {
        final JFrame application = new JFrame("Text Editor");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);
        editorView.loadFonts(); //add font list in the background;
        // Add code similar to below:
        // viewManagerModel.setState(SignupViewModel.VIEW_NAME);
        // viewManagerModel.notifyViewChanged();

        return application;
    }

}