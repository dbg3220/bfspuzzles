package puzzles.lunarlanding.model;

import solver.Configuration;
import util.Coordinates;
import util.Grid;

import java.io.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

/**
 * DESCRIPTION
 * @author George Banacos
 * November 2021
 */
public class LunarLandingConfig implements Configuration {

    private final Character blank = '_';

    private Coordinates goalSpot;

    private final Character lunarLander = 'X';

    private ArrayList<Coordinates> robotLocations = new ArrayList<>();

    private Grid<Character> board;

    public LunarLandingConfig(String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line = reader.readLine();
            String[] boardArgs = line.split(" ");
            board = new Grid<Character>(blank, Integer.parseInt(boardArgs[0]), Integer.parseInt(boardArgs[1]) );
            goalSpot = new Coordinates( Integer.parseInt(boardArgs[2]), Integer.parseInt( boardArgs[3]));
            int numRobots = 0;
            while (!( line = reader.readLine()).equals("")){
                String[] robotArgs = line.split("\\s++");
                Coordinates c = new Coordinates(Integer.parseInt(robotArgs[1]), Integer.parseInt(robotArgs[2]));
                board.set(robotArgs[0].charAt(0), c);
                robotLocations.add(c);
            }
        }catch(IOException e){
            System.out.println("File not found!");
        }

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
    public String toString() {
        String result = "    ";

        String[] boardResult = new String[board.getNRows()];
        for (int r = 0; r < board.getNRows(); r++){
            String rowResult = "";
            for (int c = 0; c < board.getNCols(); c++){
                if (goalSpot.col() == c && goalSpot.row() == r && !board.get(r,c).equals(blank)){
                    rowResult += " !" + board.get(r,c);
                }
                else{
                    rowResult += "  " + board.get(r, c);
                }
            }
            boardResult[r] = rowResult;
        }

        for (int c = 0; c < board.getNCols(); c++){
            result += "  " + c;
        }
        result += "\n    _______________\n";
        for(int r = 0; r < board.getNRows(); r++){
            result += r + "  |" + boardResult[r] + "\n";
        }
        return result;
    }
}
