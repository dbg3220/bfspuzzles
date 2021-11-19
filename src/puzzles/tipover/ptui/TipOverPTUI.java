package puzzles.tipover.ptui;

import puzzles.tipover.model.*;

import util.Observer;
import util.ptui.ConsoleApplication;

import java.io.PrintWriter;

/**
 * DESCRIPTION
 * @author Damon Gonzalez
 * November 2021
 */
public class TipOverPTUI extends ConsoleApplication implements Observer<TipOverModel, String> {

    public TipOverPTUI(){

    }

    @Override
    public void update(TipOverModel tipOverModel, String s) {

    }

    @Override
    public void start(PrintWriter console) throws Exception {

    }

    public static void main(String[] args) {
        TipOverPTUI puzzle = new TipOverPTUI();
        //ConsoleApplication.launch(args);
    }
}
