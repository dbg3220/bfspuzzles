package puzzles.tipover.ptui;

import puzzles.tipover.model.*;

import util.Observer;
import util.ptui.ConsoleApplication;

import java.io.PrintWriter;
import java.util.List;

/**
 * DESCRIPTION
 * @author Damon Gonzalez
 * November 2021
 */
public class TipOverPTUI extends ConsoleApplication implements Observer<TipOverModel, Object> {
    /** The model being used by this Application */
    private TipOverModel model;
    /** The PrintWriter object that updates the game to the console */
    private PrintWriter out;
    /** Constants for all the commands a user can perform */
    private final static String LOAD = "load";
    private final static String RELOAD = "reload";
    private final static String MOVE = "move";
    private final static String HINT = "hint";
    private final static String SHOW = "show";

    @Override
    public void init(){
        this.model = new TipOverModel();
        this.model.addObserver(this);
        List<String> args = getArguments();
        if(args.size() != 0)
            model.safeLoad(args.get(0));
    }

    @Override
    public void start(PrintWriter console) {
        this.out = console;
        super.setOnCommand(
                LOAD, 1, ": load a new puzzle",
                new LoadCommand()
        );
        super.setOnCommand(
                RELOAD, 0, ": reload the same puzzle",
                e -> model.reload()
        );
        super.setOnCommand(
                MOVE, 1, ": move the tipper(NORTH, SOUTH, EAST, WEST)",
                new MoveCommand()
        );
        super.setOnCommand(
                HINT, 0, ": show the next step",
                e -> model.cheat()
        );
        super.setOnCommand(
                SHOW, 0, ": display the current state of the board",
                e -> update(model, null)
        );
    }

    @Override
    public void update(TipOverModel model, Object o) {
        TipOverConfig currentConfig = model.getCurrentConfig();
        this.out.println(currentConfig);
        if(currentConfig.isSolution()) {
            int steps = model.getNumSteps();
            this.out.println("YOU WIN!!!(in " + steps + " steps)");
            this.out.println("You can keep moving your tipper around(although, why would you want to do that),");
            this.out.println("You can reload the same puzzle to see if there is a faster way, or");
            this.out.println("You can load a totally new puzzle");
        }
    }

    /**
     * Private class that is a ConsoleHandler and simulates the load command
     */
    private class LoadCommand implements ConsoleHandler{
        /**
         * Handles the load command by calling load() on the model and passing in the filename of the new puzzle
         *  as an argument
         * @param commandArgs the strings entered <em>after</em>
         */
        @Override
        public void handle(String[] commandArgs) {
            model.load(commandArgs[0]);
        }
    }

    /**
     * Private class that is a ConsoleHandler and simulates the move command
     */
    private class MoveCommand implements ConsoleHandler{
        /**
         * Handles the move command by calling move() on the model and passing in the direction of the tipper's movement
         *  as an argument
         * @param commandArgs the strings entered <em>after</em>
         */
        @Override
        public void handle(String[] commandArgs) {
            model.move(commandArgs[0]);
        }
    }

    public static void main(String[] args) {
        if(args.length < 2) ConsoleApplication.launch(TipOverPTUI.class, args);
        else System.out.println("USAGES: \njava TipOverPTUI\njava TipOverPTUI {filename}");
    }
}
