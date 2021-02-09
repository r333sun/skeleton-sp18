package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {
    SearchNode last;

    public Solver(WorldState initial) {
        MinPQ<SearchNode> minPQ = new MinPQ((Comparator<SearchNode>) (o1, o2) -> {
            int p1 = o1.getDistanceToInitial() + o1.worldState.estimatedDistanceToGoal();
            int p2 = o2.getDistanceToInitial() + o2.worldState.estimatedDistanceToGoal();
            return p1 - p2;
        });
        SearchNode init = new SearchNode(initial, 0, null);
        minPQ.insert(init);
        boolean found = false;
        if(init.worldState.isGoal()){
            last = init;
            found = true;
        }
        while (!found) {
            SearchNode curr = minPQ.delMin();
            Iterable<WorldState> neighbors = curr.worldState.neighbors();
            for (WorldState neighbor : neighbors) {
                if (neighbor.isGoal()) {
                    last = new SearchNode(neighbor, curr.distanceToInitial + 1, curr);
                    found = true;
                    break;
                }
                if (curr.prev == null) {
                    minPQ.insert(new SearchNode(neighbor, curr.distanceToInitial + 1, curr));
                } else {
                    if (!neighbor.equals(curr.prev.worldState)) {
                        minPQ.insert(new SearchNode(neighbor, curr.distanceToInitial + 1, curr));
                    }
                }
            }
        }
    }

    public int moves() {
        return last.distanceToInitial;
    }

    public Iterable<WorldState> solution() {
        Stack<WorldState> solution = new Stack<>();
        SearchNode curr = last;
        while (curr != null) {
            solution.push(curr.worldState);
            curr = curr.prev;
        }
        return solution;
    }

    private class SearchNode {
        private WorldState worldState;
        private int distanceToInitial;
        private SearchNode prev;

        public SearchNode(WorldState worldState, int distanceToInitial, SearchNode prev) {
            this.worldState = worldState;
            this.distanceToInitial = distanceToInitial;
            this.prev = prev;
        }

        public WorldState getWorldState() {
            return worldState;
        }

        public void setWorldState(WorldState worldState) {
            this.worldState = worldState;
        }

        public int getDistanceToInitial() {
            return distanceToInitial;
        }

        public void setDistanceToInitial(int distanceToInitial) {
            this.distanceToInitial = distanceToInitial;
        }

        public SearchNode getPrev() {
            return prev;
        }

        public void setPrev(SearchNode prev) {
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "SearchNode{"
                    + "worldState=" + worldState
                    + ", distanceToInitial=" + distanceToInitial
                    + '}';
        }
    }
}
