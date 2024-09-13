package ga.framework;

import ga.framework.model.Solution;
import ga.framework.operators.SelectionOperator;

import java.util.List;

public class TournamentSelection implements SelectionOperator {

    @Override
    public Solution selectParent(List<Solution> candidates) {
        Solution parent1 = candidates.get((int) (Math.random() * candidates.size()));
        Solution parent2 = candidates.get((int) (Math.random() * candidates.size()));

        if (parent1.getFitness() > parent2.getFitness()) {
            return parent1;
        } else if (parent1.getFitness() < parent2.getFitness()) {
            return parent2;
        } else {
            return parent1;
        }
    }
}
