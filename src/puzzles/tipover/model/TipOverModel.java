package puzzles.tipover.model;

import solver.Solver;
import util.Observer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * DESCRIPTION
 * @author Damon Gonzalez
 * November 2021
 */
public class TipOverModel {
    /** The current Configuration in this model */
    private TipOverConfig currentConfig;
    /** A counter variable to keep track of how many steps the user has performed on this attempt */
    private int steps;
    /** The name of the file from where the last successful puzzle load was from */
    private String lastFilename;
    /** A list of observers to this model */
    private final List<Observer<TipOverModel, Object>> observers = new LinkedList<>();

    enum Direction{//GET PROPER USAGE OF ENUMS FROM TOLL LAB
        NORTH,
        SOUTH,
        EAST,
        WEST
    }
    /**
     * Loads a new puzzle into this model using safeLoad() and given a filename, then notifies observers
     * @param filename The file of the initial configuration of the puzzle
     */
    public void load(String filename){
        safeLoad(filename);
        steps = 0;
        notifyObservers();
    }

    /**
     * Loads the same puzzle as loaded last, effectively resets the currentConfig back to its original value, then notifies observers
     */
    public void reload(){
        load(lastFilename);
    }

    /**
     * Loads a new puzzle by setting the currentConfig the TipOverConfig initializer's constructor. Does
     *  not call notifyObservers to avoid an exception at the start of the program
     * @param filename The file of the initial configuration of the puzzle
     */
    public void safeLoad(String filename){
        try {
            currentConfig = new TipOverConfig(filename);
            lastFilename = filename;
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void cheat(){
        setNextStep();
        notifyObservers();
    }

    private void setNextStep(){
        currentConfig = (TipOverConfig) Solver.BFS(currentConfig).get(1);
        steps++;
    }

    public void move(String direction){
        System.out.println("MOVE THE TIPPER " + direction);
        steps++;
    }

    public TipOverConfig getCurrentConfig() { return currentConfig; }

    public int getNumSteps() { return steps; }

    public void addObserver(Observer o){
        observers.add(o);
    }

    private void notifyObservers(){
        for(Observer observer : observers)
            observer.update(this, null);
    }
    /*
     * Code here includes...
     * Additional data variables for anything needed beyond what is in
     *   the config object to describe the current state of the puzzle
     * Methods to support the controller part of the GUI, e.g., load, move
     * Methods and data to support the "subject" side of the Observer pattern
     *
     * WARNING: To support the hint command, you will likely have to do
     *   a cast of Config to TipOverConfig somewhere, since the solve
     *   method works with, and returns, objects of type Configuration.
     */
}
