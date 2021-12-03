package puzzles.tipover.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.tipover.model.TipOverConfig;
import puzzles.tipover.model.TipOverModel;
import util.Observer;

import java.io.File;
import java.util.List;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOverGUI extends Application
        implements Observer< TipOverModel, String > {

    /** The model used by this GUI */
    private TipOverModel model;
    /** The label that displays messages to the user */
    private Label topLabel;
    /** The directory of input files for this program */
    private static final String RESOURCE_DIRECTORY = "data/tipover/";

    /**
     * Initializes the text-based interface to set up the game
     */
    @Override
    public void init(){
        this.model = new TipOverModel();
        this.model.addObserver(this);
        List<String> args = getParameters().getRaw();
        if(args.size() != 0)
            model.safeLoad(args.get(0));
    }

    /**
     * Starts the GUI by creating the physical component on screen and showing it
     * @param stage The stage that acts as the main Window for this program
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle( "Tip Over" );
        stage.setResizable(false);
        BorderPane main = new BorderPane();
        main.setBottom(createBottom(stage));
        main.setRight(createSide());
        topLabel = new Label();
        main.setTop(topLabel);
        BorderPane.setAlignment(topLabel, Pos.CENTER);
        Scene scene = new Scene( main, 640, 480 );
        stage.setScene( scene );
        model.go();
        stage.show();
    }

    /**
     * Creates the bottom portion of the GUI, buttons load, reload, and hint
     * @param stage The stage that is the main window for this program, is necessary for initializing the file chooser
     * @return an HBox with all of the buttons in it
     */
    private HBox createBottom(Stage stage){
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        Button load = new Button("LOAD");
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = new File(RESOURCE_DIRECTORY);
            fileChooser.setInitialDirectory(file);
            fileChooser.setTitle("Open Resource File");
            File loadedFile = fileChooser.showOpenDialog(stage);
            if(loadedFile != null){
                model.load(RESOURCE_DIRECTORY + loadedFile.getName());
            }
        });
        hBox.getChildren().add(load);
        Button reload = new Button("RELOAD");
        reload.setOnAction(e -> model.reload());
        hBox.getChildren().add(reload);
        Button hint = new Button("HINT");
        hint.setOnAction(e -> model.cheat());
        hBox.getChildren().add(hint);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * Creates the side buttons of the GUI, the ones that move the tipper
     * @return A GridPane of buttons that move the tipper NORTH, SOUTH, EAST, or WEST
     */
    private GridPane createSide(){
        GridPane directions = new GridPane();
        Button NORTH = new Button("N");//SET ON ACTIONS
        NORTH.setMinHeight(35);
        NORTH.setMinWidth(65);
        NORTH.setOnAction(e -> model.move("NORTH"));
        Button SOUTH = new Button("S");
        SOUTH.setMinHeight(35);
        SOUTH.setMinWidth(65);
        SOUTH.setOnAction(e -> model.move("SOUTH"));
        Button EAST = new Button("E");
        EAST.setMinHeight(35);
        EAST.setMinWidth(65);
        EAST.setOnAction(e -> model.move("EAST"));
        Button WEST = new Button("W");
        WEST.setMinHeight(35);
        WEST.setMinWidth(65);
        WEST.setOnAction(e -> model.move("WEST"));
        directions.add(NORTH, 1, 0);
        directions.add(SOUTH, 1, 2);
        directions.add(EAST, 0, 1);
        directions.add(WEST, 2, 1);
        return directions;
    }

    /**
     * This updates the GUI based on information provided by the model
     * @param tipOverModel The model being used
     * @param data Additional optional information being provided
     */
    @Override
    public void update(TipOverModel tipOverModel, String data) {
        TipOverConfig currentConfig = tipOverModel.getCurrentConfig();
        if(currentConfig == null){
            topLabel.setText("No File Loaded");
        }
        else{
            System.out.println(currentConfig);
            if(!data.equals(""))
                topLabel.setText("Cannot move in " + data + " direction");
            else if(currentConfig.isSolution())
                topLabel.setText("You won in " + tipOverModel.getNumSteps() + " steps");
        }
    }

    public static void main( String[] args ) {
        if(args.length < 2) Application.launch( args );
        else System.out.println("USAGES: \njava TipOverPTUI\njava TipOverPTUI {filename}");
    }
}

/*
Image spaceship = new Image(
                TipOverGUI.class.getResourceAsStream(
                        "resources" + File.separator + "tipper.png"
                )
        );
 */
