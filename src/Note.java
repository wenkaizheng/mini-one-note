import View.NoteView;
import javafx.application.Application;

/**
 * This class starts the application
 */
public class Note {

    /**
     * the main method
     * @param args an array of string that is never used.
     */
     public static void main(String[] args) {

        // build the observer-observable Connection
        Application.launch(NoteView.class, args);
    }
}
