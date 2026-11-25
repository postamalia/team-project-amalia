package view;

import entity.TextStyle;
import interface_adapter.editor.EditorViewModel;
import interface_adapter.format_text.FormatTextController;
import interface_adapter.load_fonts.LoadFontsController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**/

public class EditorView extends JPanel implements EditorViewModel.Listener{
    private final EditorViewModel viewModel;
    private final JTextPane textPane = new JTextPane();
    private final JToggleButton boldBtn = new JToggleButton("B");
    private final JToggleButton italicBtn = new JToggleButton("I");
    private final JComboBox<String> fontBox = new JComboBox<>(new String[]{"Serif"});

    private FormatTextController formatController;
    private LoadFontsController loadFontsController;

    private boolean updatingToolbar = false;

    public EditorView(EditorViewModel viewModel){
        this.viewModel = viewModel;
        viewModel.setListener(this);
        setLayout(new BorderLayout());

        /*Default*/
        Style defaultStyle = textPane.getStyledDocument().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setFontFamily(defaultStyle, "Serif");
        StyleConstants.setFontSize(defaultStyle, 16);

        boldBtn.setFont(boldBtn.getFont().deriveFont(Font.BOLD));
        italicBtn.setFont(italicBtn.getFont().deriveFont(Font.ITALIC));
        fontBox.setPrototypeDisplayValue("Abcdefghijklmnopqrstuvwxyz");
        fontBox.setMaximumSize(new Dimension(240, 30));
        boldBtn.setFocusable(false);
        italicBtn.setFocusable(false);
        fontBox.setFocusable(false);

        boldBtn.addActionListener(e -> {
            int[] r = targetRange();
            formatController.toggleBold(r[0], r[1], styleAt(r));
        });
        italicBtn.addActionListener(e -> {
            int[] r = targetRange();
            formatController.toggleItalic(r[0], r[1], styleAt(r));
        });
        fontBox.addActionListener(e -> {
            if(updatingToolbar){
                return;
            }
            int[] r = targetRange();
            TextStyle current = styleAt(r);
            String chosen = (String) fontBox.getSelectedItem();
            runInBackground(() -> formatController.changeFont(r[0], r[1], current, chosen));
        });

        textPane.addCaretListener(e -> SwingUtilities.invokeLater(this::reportStyle));

        JToolBar bar = new JToolBar();
        bar.setFloatable(false);
        bar.add(boldBtn);
        bar.add(italicBtn);
        bar.addSeparator();
        bar.add(fontBox);

        add(bar, BorderLayout.NORTH);
        add(new JScrollPane(textPane), BorderLayout.CENTER);
    }

    public void setFormatController(FormatTextController c){
        this.formatController = c;
    }

    public void setLoadFontsController(LoadFontsController c){
        this.loadFontsController = c;
    }

    public void loadFonts(){
        runInBackground(() -> loadFontsController.execute());
    }

    /*The selection, otherwise the word under the cursor, otherwise an empty range at the cursor*/
    private int[] targetRange(){
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if(start != end){
            return new int[]{start, end};
        }
        int caret = textPane.getCaretPosition();
        try{
            int ws = Utilities.getWordStart(textPane, caret);
            int we = Utilities.getWordEnd(textPane, caret);
            String raw = textPane.getDocument().getText(ws, we - ws);
            int lead = raw.length() - raw.stripLeading().length();
            int trail = raw.length() - raw.stripTrailing().length();
            if(lead + trail < raw.length()){ //the range contains a real word
                return new int[]{ws + lead, we - trail};
            }
        }catch(BadLocationException ex){
            //use the cursor only
        }
        return new int[]{caret, caret};
    }

    private TextStyle styleAt(int[] range){
        AttributeSet a;
        if(range[0] != range[1]){
            a = textPane.getStyledDocument().getCharacterElement(range[0]).getAttributes();
        } else{
            a = textPane.getInputAttributes(); //style of the next typed character
        }
        return new TextStyle(StyleConstants.isBold(a), StyleConstants.isItalic(a),
                StyleConstants.getFontFamily(a));
    }

    private void reportStyle(){
        if(formatController == null){
            return;
        }
        int[] r = targetRange();
        formatController.reportStyle(r[0], r[1], styleAt(r));
    }

    private void runInBackground(Runnable task){
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground(){
                task.run();
                return null;
            }
        }.execute();
    }

    @Override
    public void onFormatChanged(){
        SwingUtilities.invokeLater(() -> { //UI changes must happen on the Swing thread
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            boolean hasChange = false;
            if(viewModel.getApplyBold() != null){
                StyleConstants.setBold(attrs, viewModel.getApplyBold());
                hasChange = true;
            }
            if(viewModel.getApplyItalic() != null){
                StyleConstants.setItalic(attrs, viewModel.getApplyItalic());
                hasChange = true;
            }
            if(viewModel.getApplyFont() != null){
                StyleConstants.setFontFamily(attrs, viewModel.getApplyFont());
                hasChange = true;
            }
            if(hasChange){
                int length = viewModel.getEnd() - viewModel.getStart();
                if(length > 0){
                    textPane.getStyledDocument().setCharacterAttributes(
                            viewModel.getStart(), length, attrs, false); //false = merge, don't reset
                }
                if(textPane.getSelectionStart() == textPane.getSelectionEnd()){
                    textPane.getInputAttributes().addAttributes(attrs); //style the next typed text
                }
            }
            TextStyle shown = viewModel.getShownStyle(); //light up the toolbar
            updatingToolbar = true;
            boldBtn.setSelected(shown.isBold());
            italicBtn.setSelected(shown.isItalic());
            fontBox.setSelectedItem(shown.getFontFamily());
            updatingToolbar = false;
        });
    }

    @Override
    public void onFontsLoaded(){
        SwingUtilities.invokeLater(() -> {
            updatingToolbar = true;
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Serif");
            for(String family : viewModel.getFonts()){
                if(!family.equals("Serif")){
                    model.addElement(family);
                }
            }
            fontBox.setModel(model);
            fontBox.setSelectedItem("Serif");
            updatingToolbar = false;
        });
    }
}