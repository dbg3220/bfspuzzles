package puzzles.tipover.model;

import solver.Configuration;
import util.Coordinates;

import java.io.*;
import java.util.List;

/**
 * DESCRIPTION
 * @author Damon Gonzalez
 * November 2021
 */
public class TipOverConfig implements Configuration{
    /** The grid of squares that holds the value of the configuration */
    private char[][] grid;
    /** The location of the goal crate, does not change */
    private static Coordinates goal;
    /** The location of the tipper of this config */
    private Coordinates tipperLocation;

    /**
     * Public constructor for a TipOverConfig that is meant to be used to generate
     *  an initial configuration of a puzzle, given a filename
     * @param filename The name of the file that holds the puzzle
     */
    public TipOverConfig(String filename) throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        try{
            String line;
            while(!(line = in.readLine()).equals("")){
                System.out.println("Line of the File: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        goal = new Coordinates(0, 0);//STUB VALUES
        tipperLocation = new Coordinates(0, 0);//STUB VALUES
        grid = new char[0][0];
    }

    /**
     * Copy constructor for this class
     * @return A deep copy of this object
     */
    protected TipOverConfig copy(){
        return null;
    }

    @Override
    public boolean isSolution() {
        return false;
    }

    @Override
    public List<Configuration> getNeighbors() {
        return null;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public String toString(){
        return "";
    }
}
