package puzzles.lunarlanding.model;

import solver.Configuration;

import java.util.List;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class LunarLandingConfig implements Configuration {

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
        return null;
    }
}
