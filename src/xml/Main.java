package xml;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


/**
 * Manage and parse XML files
 *
 * @author Vincent Liu
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class Main extends Application {
    // kind of data files to look for
    public static final String DATA_FILE_EXTENSION = "*.xml";

    // NOTE: generally accepted behavior that the chooser remembers where user left it last
    private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);

    @Override
    public void start (Stage primaryStage) throws Exception {
        var dataFile = myChooser.showOpenDialog(primaryStage);
        while (dataFile != null) {
            try {
//                var m = new ParentXMLParser("media").getGame(dataFile);
                var m = new GameOfLifeXMLParser().getGameOfLife(dataFile);
                new Alert(AlertType.INFORMATION, m.toString()).showAndWait();
            }
            catch (XMLException e) {
                new Alert(AlertType.ERROR, e.getMessage()).showAndWait();
            }
            dataFile = myChooser.showOpenDialog(primaryStage);
        }

        // nothing selected, so quit the application
        Platform.exit();

    }

    // set some sensible defaults when the FileChooser is created
    private FileChooser makeChooser (String extensionAccepted) {
        var result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }


    public static void main (String[] args) {
        launch(args);
    }
}
