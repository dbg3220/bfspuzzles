package puzzles.tipover;

import puzzles.tipover.model.TipOverConfig;
import solver.Configuration;
import solver.Solver;

import java.util.List;

/**
 * DESCRIPTION
 * @author Damon Gonzalez
 * November 2021
 */
public class TipOver {
    /** The starting configuration of this puzzle */
    private final TipOverConfig initialConfig;

    /**
     * Public constructor for the TipOver puzzle that specifies the initial condition of the puzzle
     *  given a filename
     * @param filename The name of the puzzle file
     */
    public TipOver(String filename) {
        initialConfig = new TipOverConfig(filename);
    }

    /**
     * Solves this puzzle using the initial configuration specified by the constructor, returns nothing
     */
    public void solve(){
        System.out.println("Start Configuration: ");
        System.out.println(initialConfig);
        List<Configuration> path = Solver.BFS(initialConfig);
        if(path.size() == 0)
            System.out.println("No Solution");
        for(int i = 0; i < path.size(); i++){
            System.out.println("Step " + i + ":");
            System.out.println(path.get(i));
        }
    }

    public static void main(String[] args) {
        if(args.length == 1){
            TipOver puzzle = new TipOver(args[0]);
            puzzle.solve();
        }else {
            System.out.println("USAGE: java TipOver {filename}");
        }
    }
}
