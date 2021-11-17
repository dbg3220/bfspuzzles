package puzzles.tipover;

import puzzles.tipover.model.TipOverConfig;

import java.io.*;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOver {
    /** The starting configuration of this puzzle */
    private TipOverConfig initialConfig;

    /**
     * Public constructor for the TipOver puzzle that specifies the initial condition of the puzzle
     *  given a filename
     * @param filename The name of the puzzle file
     */
    public TipOver(String filename) throws FileNotFoundException {
        initialConfig = new TipOverConfig(filename);
    }

    /**
     * Solves this puzzle using the initial configuration specified by the constructor, returns nothing
     */
    public void solve(){
        System.out.println("Solving is Hard");
    }

    public static void main( String[] args ) {
        if(args.length == 1){
            try {
                TipOver puzzle = new TipOver(args[0]);
                puzzle.solve();
            }catch(FileNotFoundException e){
                System.out.println("ERROR: " + args[0] + " was not found");
            }
        }else {
            System.out.println("USAGE: java TipOver {filename}");
        }
    }
}
