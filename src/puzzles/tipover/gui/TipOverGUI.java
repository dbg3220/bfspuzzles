package puzzles.tipover.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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

    @Override
    public void start( Stage stage ) {
        stage.setTitle( "Tip Over" );
        Image spaceship = new Image(
                TipOverGUI.class.getResourceAsStream(
                        "resources" + File.separator + "tipper.png"
                )
        );
        Button temp = new Button();
        temp.setGraphic( new ImageView( spaceship ) );
        Scene scene = new Scene( temp, 640, 480 );
        stage.setScene( scene );
        stage.show();
    }

    @Override
    public void update( TipOverModel tipOverModel, String o ) {
        System.out.println( "My model has changed! (DELETE THIS LINE)");
    }

    public static void main( String[] args ) {
        System.err.println( "REPLACE THIS METHOD!" );
        Application.launch( args );
    }
}
