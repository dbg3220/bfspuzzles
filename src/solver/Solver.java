package solver;

import java.util.*;

/**
 * This class contains a universal algorithm to find a path from a starting
 * configuration to a solution, if one exists.
 * @author Damon Gonzalez
 */
public class Solver {
    /**
     * This method will hold the BFS algorithm that can be used across all graph
     * related puzzles. It will be given a node(Configuration) in the graph to start with;
     * computing the path from there. When the algorithm terminates it will return a List representing
     * the shortest path between two nodes in the graph. If the List is empty, there is no path.
     * @param startConfig The initial Configuration
     * @return The path that is calculated
     */
    public static List<Configuration> BFS(Configuration startConfig){
        List<Configuration> queue = new LinkedList<>();
        Map<Configuration, Configuration> predecessorMap = new HashMap<>();
        queue.add(startConfig);
        predecessorMap.put(startConfig, null);
        int totalConfigs = 0;
        int uniqueConfigs;
        Configuration solution = null;
        while(queue.size() != 0){
            Configuration currentConfig = queue.remove(0);
            List<Configuration> neighbors = currentConfig.getNeighbors();
            totalConfigs += neighbors.size();
            for (Configuration neighbor : neighbors){
                if(!predecessorMap.containsKey(neighbor)){
                    queue.add(neighbor);
                    predecessorMap.put(neighbor, currentConfig);
                    if(neighbor.isSolution()) {
                        solution = neighbor;
                        queue.clear();
                        break;
                    }
                }
            }
        }
        uniqueConfigs = predecessorMap.size();
        System.out.println("Total configs: " + totalConfigs);
        System.out.println("Unique configs: " + uniqueConfigs);
        List<Configuration> path = new ArrayList<>();
        if(solution != null) {
            Configuration traversalConfig = solution;
            path.add(0, traversalConfig);
            while (!startConfig.equals(traversalConfig)) {
                traversalConfig = predecessorMap.get(traversalConfig);
                path.add(0, traversalConfig);
            }
        }
        return path;
    }
}
