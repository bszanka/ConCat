package concat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

public class ConCat extends Application implements Initializable {

    private final int BG_WIDTH = 250;
    private final int BG_HEIGHT = 250;

//<editor-fold defaultstate="collapsed" desc="Setting the scene.">
    @Override
    public void start(Stage stage) throws Exception {
        ImageView imgView = new ImageView("open.png");
        imgView.setFitWidth(20);
        imgView.setFitHeight(20);
        ImageView background = new ImageView("3.jpg");
        background.setFitWidth(BG_WIDTH);
        background.setFitHeight(BG_HEIGHT);
        Menu file = new Menu("File");
        MenuItem item = new MenuItem("Open Files", imgView);
        file.getItems().addAll(item);
        //Creating a File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Multiple Files");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        //Creating a menu bar and adding menu to it.
        MenuBar menuBar = new MenuBar(file);
        AnchorPane pane = new AnchorPane(background);
        TextArea textArea = new TextArea("\t(c) Balazs Szanka\nhttps://github.com/bszanka");
        textArea.setEditable(false);
        VBox vbox = new VBox(textArea);
        vbox.setLayoutX((BG_HEIGHT / 1.5) / 4);
        vbox.setLayoutY((BG_WIDTH / 1.5) / 4);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(BG_WIDTH / 1.5, BG_HEIGHT / 1.5);
        Group root = new Group(pane, menuBar, vbox);
        Scene scene = new Scene(root, BG_WIDTH, BG_HEIGHT, Color.LIGHTGREY);
//<editor-fold defaultstate="collapsed" desc="Adding action on the menu item">
item.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent event) {
        textArea.clear();
        
        //Opening a dialog box and put the output in a list
        List<File> output = fileChooser.showOpenMultipleDialog(stage);
        List<String> fileStr = new ArrayList<>();
        File file3 = new File("W:\\Szanka Balázs/EKÖ.txt");
        
        for (int i = 0; i < output.size(); i++) {
            try {
                fileStr.add(FileUtils.readFileToString(output.get(i), Charset.defaultCharset()));
                FileUtils.write(file3, fileStr.get(i), Charset.defaultCharset(), true);
            } catch (IOException ex) {
                Logger.getLogger(ConCat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        File inputFile = file3;
        File tempFile = new File("W:\\Szanka Balázs/tmp.txt");
        
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConCat.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(tempFile));
        } catch (IOException ex) {
            Logger.getLogger(ConCat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String lineToRemove = "CSOP:TCS140400000000";
        String lineToRemove2 = "#GONGYOLEG";
        String currentLine;
        
        try {
            while ((currentLine = reader.readLine()) != null) {
                try {
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    if (trimmedLine.equals(lineToRemove) || trimmedLine.equals(lineToRemove2)) {
                        continue;
                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                } catch (IOException ex) {
                    Logger.getLogger(ConCat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConCat.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ConCat.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(ConCat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
        
        textArea.appendText("Siker!");
        
    }
    
});
//</editor-fold>
stage.setResizable(false);
stage.getIcons().add(new Image("harold.png"));
stage.setTitle("ConCat++");
stage.setScene(scene);
stage.show();
    }
//</editor-fold>

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
