package puzzles.lunarlanding;

import puzzles.lunarlanding.model.LunarLandingConfig;
import solver.Configuration;

/**
 * DESCRIPTION
 * @author George Banacos
 * November 2021
 */
public class LunarLanding {

    private Configuration lunarLandingConfiguration;

    public LunarLanding(String fileName){

        lunarLandingConfiguration = new LunarLandingConfig(fileName);
    }

    /*
     * code to read the file name from the command line and
     * run the solver on the puzzle
     */

    public static void main( String[] args ) {
        if (args.length == 1){
            String fileName = args[0];
            LunarLanding l = new LunarLanding(fileName);
        }
        else{
            System.out.println("Input file name");
        }
    }
}
