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
    private char[] grid;
    /** The amount of rows */
    private int rows;
    /** The amount of columns in each row */
    private int cols;
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
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line = in.readLine();//ANALYZE FIRST LINE VALUES
            String[] values = line.split("\\s++");
            rows = Integer.parseInt(values[0]);
            cols = Integer.parseInt(values[1]);
            grid = new char[rows * cols];
            tipperLocation = new Coordinates(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
            goal = new Coordinates(Integer.parseInt(values[4]), Integer.parseInt(values[5]));
            int index = 0;
            while(!(line = in.readLine()).equals("")){//ANALYZE THE GRID
                String[] row = line.split("\\s++");
                for(String square : row) {
                    grid[index] = square.charAt(0);
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Represents this configuration as text symbols
     * @return String representation
     */
    @Override
    public String toString(){
        String message = "   ";
        for(int i = 0; i < cols; i++)
            message += "  " + i;
        message += "\n   ";
        for(int i = 0; i < cols; i++)
            message += "___";
        message += "\n";
        int start = 0;
        for(int i = 0; i < rows; i++){
            message += i + " |";
            for(int j = start; j < start + cols; j++){
                Coordinates c = new Coordinates(i, j);
                message += " ";
                if(goal.equals(c))
                    message += "!";
                else if(tipperLocation.equals(c))
                    message += "*";
                else
                    message += " ";
                message += get(c);
            }
            message += "\n";
        }
        return message;
    }

    /**
     * Private helper function to abstract configuration's relationship to the array of characters representing
     *  a two-dimensional grid. Sets a given location in grid to a specified new character
     * @param a The Coordinate object that represents its row, col location
     * @param c The char being set
     */
    private void set(Coordinates a, char c){
        int index = a.row() * cols + a.col();
        grid[index] = c;
    }

    /**
     * Private helper function to abstract configuration's relationship to the array of characters representing
     *  a two-dimensional grid. Returns a char at a given location in grid
     * @param a The Coordinate object that represents its row, col location
     * @return The char being gotten
     */
    private char get(Coordinates a){
        int index = a.row() * cols + a.col();
        return grid[index];
    }
}
