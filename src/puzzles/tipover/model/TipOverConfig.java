package puzzles.tipover.model;

import solver.Configuration;
import util.Coordinates;

import java.io.*;
import java.util.*;

/**
 * Public Configuration class for the TipOver puzzle that holds all the state necessary for representing
 *  a configuration, and holds all the methods necesary for a Breadth First Search Algorithm.
 * @author Damon Gonzalez
 * November 2021
 */
public class TipOverConfig implements Configuration{
    /** The grid of squares that holds the value of the configuration */
    private char[] grid;
    /** The amount of rows */
    private static int rows;
    /** The amount of columns in each row */
    private static int cols;
    /** The location of the goal crate, does not change */
    private static Coordinates goal;
    /** The location of the tipper of this config */
    private Coordinates tipperLocation;

    /**
     * Public constructor for a TipOverConfig that is meant to be used to generate
     *  an initial configuration of a puzzle, given a filename
     * @param filename The name of the file that holds the puzzle
     */
    public TipOverConfig(String filename) {
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
            e.getStackTrace();
        }
    }

    /**
     * Copy constructor for this class
     */
    protected TipOverConfig(TipOverConfig other){
        this.grid = Arrays.copyOf(other.grid, other.grid.length);
        this.tipperLocation = new Coordinates(other.tipperLocation.row(), other.tipperLocation.col());
    }

    /**
     * A configuration is a goal if its tipper location is equal to its goal crate location
     * @return True if the configuration is a solution to the puzzle, False otherwise
     */
    @Override
    public boolean isSolution() {
        return goal.equals(tipperLocation);
    }

    @Override
    public List<Configuration> getNeighbors() {
        List<Configuration> neighbors = new ArrayList<>();
        char tipperSquare = get(tipperLocation);
        if(tipperSquare == '1'){
            Coordinates NORTH = new Coordinates(tipperLocation.row() - 1, tipperLocation.col());
            if(tipperLocation.row() > 0 && get(NORTH) != '0'){
                TipOverConfig neighbor = new TipOverConfig(this);
                neighbor.tipperLocation = NORTH;
                neighbors.add(neighbor);
            }
            Coordinates SOUTH = new Coordinates(tipperLocation.row() + 1, tipperLocation.col());
            if(tipperLocation.row() + 1 < rows && get(SOUTH) != '0'){
                TipOverConfig neighbor = new TipOverConfig(this);
                neighbor.tipperLocation = SOUTH;
                neighbors.add(neighbor);
            }
            Coordinates WEST = new Coordinates(tipperLocation.row(), tipperLocation.col() - 1);
            if(tipperLocation.col() > 0 && get(WEST) != '0'){
                TipOverConfig neighbor = new TipOverConfig(this);
                neighbor.tipperLocation = WEST;
                neighbors.add(neighbor);
            }
            Coordinates EAST = new Coordinates(tipperLocation.row(), tipperLocation.col() + 1);
            if(tipperLocation.col() + 1 < cols && get(EAST) != '0'){
                TipOverConfig neighbor = new TipOverConfig(this);
                neighbor.tipperLocation = EAST;
                neighbors.add(neighbor);
            }
        } else {
            int towerLength = Integer.parseInt(String.valueOf(tipperSquare));
            Coordinates fromNORTH = new Coordinates(tipperLocation.row() - 1, tipperLocation.col());
            Coordinates toNORTH = new Coordinates(tipperLocation.row() - towerLength, tipperLocation.col());
            if(tipperLocation.row() > 1 && isClear(fromNORTH, toNORTH)){
                TipOverConfig neighbor = new TipOverConfig(this);
                neighbor.towerFall(fromNORTH, toNORTH);
                neighbor.set(neighbor.tipperLocation, '0');
                neighbor.tipperLocation = fromNORTH;
                neighbors.add(neighbor);
            }
            Coordinates fromSOUTH = new Coordinates(tipperLocation.row() + 1, tipperLocation.col());
            Coordinates toSOUTH = new Coordinates(tipperLocation.row() + towerLength, tipperLocation.col());
            if(tipperLocation.row() + 2 < rows && isClear(fromSOUTH, toSOUTH)){
                TipOverConfig neighbor = new TipOverConfig(this);
                neighbor.towerFall(fromSOUTH, toSOUTH);
                neighbor.set(neighbor.tipperLocation, '0');
                neighbor.tipperLocation = fromSOUTH;
                neighbors.add(neighbor);
            }
            Coordinates fromWEST = new Coordinates(tipperLocation.row(), tipperLocation.col() - 1);
            Coordinates toWEST = new Coordinates(tipperLocation.row(), tipperLocation.col() - towerLength);
            if(tipperLocation.col() > 1 && isClear(fromWEST, toWEST)){
                TipOverConfig neighbor = new TipOverConfig(this);
                neighbor.towerFall(fromWEST, toWEST);
                neighbor.set(neighbor.tipperLocation, '0');
                neighbor.tipperLocation = fromWEST;
                neighbors.add(neighbor);
            }
            Coordinates fromEAST = new Coordinates(tipperLocation.row(), tipperLocation.col() + 1);
            Coordinates toEAST = new Coordinates(tipperLocation.row(), tipperLocation.col() + towerLength);
            if(tipperLocation.col() + 2 < cols && isClear(fromEAST, toEAST)){
                TipOverConfig neighbor = new TipOverConfig(this);
                neighbor.towerFall(fromEAST, toEAST);
                neighbor.set(neighbor.tipperLocation, '0');
                neighbor.tipperLocation = fromEAST;
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    /**
     * A TipOverConfig is equal to another if its grids are equal and its tipperLocation is equal
     * @param other The object being compared
     * @return True if the objects are completely equal, false otherwise
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof TipOverConfig otherConfig){
            return this.tipperLocation.equals(otherConfig.tipperLocation)
                    && Arrays.equals(this.grid, otherConfig.grid);
        }
        return false;
    }

    /**
     * Gives a hash code that is necessary for the HashMap used in the solver
     * @return An integer hash value of the state of this grid
     */
    @Override
    public int hashCode(){
        return Arrays.hashCode(grid) + tipperLocation.hashCode();
    }

    /**
     * Represents this configuration as text symbols
     * @return String representation
     */
    @Override
    public String toString(){
        StringBuilder message = new StringBuilder("   ");
        for(int i = 0; i < cols; i++)
            message.append("  ").append(i);
        message.append("\n   ");
        message.append("___".repeat(Math.max(0, cols)));
        message.append("\n");
        int start = 0;
        for(int i = 0; i < rows; i++){
            message.append(i).append(" |");
            for(int j = start; j < start + cols; j++){
                Coordinates c = new Coordinates(i, j);
                message.append(" ");
                if(goal.equals(c))
                    message.append("!");
                else if(tipperLocation.equals(c))
                    message.append("*");
                else
                    message.append(" ");
                char square = get(c);
                if(square == '0') message.append("_");
                else message.append(square);
            }
            message.append("\n");
        }
        return message.toString();
    }

    /**
     * Private helper function to abstract configuration's relationship to the array of characters representing
     *  a two-dimensional grid. Sets a given location in grid to a specified new character; Guaranteed
     *  to be valid coordinates of grid
     * @param a The Coordinate object that represents its row, col location
     * @param c The char being set
     */
    private void set(Coordinates a, char c){
        int index = a.row() * cols + a.col();
        grid[index] = c;
    }

    /**
     * Private helper function to abstract configuration's relationship to the array of characters representing
     *  a two-dimensional grid. Returns a char at a given location in grid; Guaranteed
     *  to be valid coordinates of grid
     * @param a The Coordinate object that represents its row, col location
     * @return The char being gotten
     */
    private char get(Coordinates a){
        int index = a.row() * cols + a.col();
        return grid[index];
    }

    /**
     * Private helper function to determine if there is space between two coordinates(on the grid) for a tower to fall on. Guaranteed
     *  to be valid coordinates of grid and to be in line horizontally or vertically
     * @param from The coordinate of one end of the fallen tower
     * @param to The coordinate of the other end of the fallen tower
     * @return True if there is space, False otherwise
     */
    private boolean isClear(Coordinates from, Coordinates to){
        if(from.row() == to.row()){
            if(from.col() > to.col()){
                for(int i = to.col(); i <= from.col(); i++){
                    Coordinates current = new Coordinates(from.row(), i);
                    if(get(current) != '0') return false;
                }
            } else {
                for(int i = from.col(); i <= to.col(); i++){
                    Coordinates current = new Coordinates(from.row(), i);
                    if(get(current) != '0') return false;
                }
            }
        } else {
            if(from.row() > to.row()){
                for(int i = to.row(); i <= from.row(); i++){
                    Coordinates current = new Coordinates(i, from.col());
                    if(get(current) != '0') return false;
                }
            } else {
                for(int i = from.row(); i <= to.row(); i++){
                    Coordinates current = new Coordinates(i, from.col());
                    if(get(current) != '0') return false;
                }
            }
        }
        return true;
    }

    /**
     * Private helper function that is meant to simulate the tipping over of a tower, coordinates are provided
     *  for each end of the tower. In order to simulate this, the method sets all coordinates in grid from one parameter
     *  to the other, inclusive, to the char '1'.
     * @param from One end of the fallen tower
     * @param to The other end of the fallen tower
     */
    private void towerFall(Coordinates from, Coordinates to){
        if(from.row() == to.row()){
            if(from.col() > to.col()){
                for(int i = to.col(); i <= from.col(); i++){
                    Coordinates current = new Coordinates(from.row(), i);
                    set(current, '1');
                }
            } else {
                for(int i = from.col(); i <= to.col(); i++){
                    Coordinates current = new Coordinates(from.row(), i);
                    set(current, '1');
                }
            }
        } else {
            if(from.row() > to.row()){
                for(int i = to.row(); i <= from.row(); i++){
                    Coordinates current = new Coordinates(i, from.col());
                    set(current, '1');
                }
            } else {
                for(int i = from.row(); i <= to.row(); i++){
                    Coordinates current = new Coordinates(i, from.col());
                    set(current, '1');
                }
            }
        }
    }


    /* Temporary Tester Function for this class's methods */
    public void test(){

    }
}
