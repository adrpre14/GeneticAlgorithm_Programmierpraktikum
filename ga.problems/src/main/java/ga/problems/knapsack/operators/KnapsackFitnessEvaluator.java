package ga.problems.knapsack.operators;

import ga.framework.model.Solution;
import ga.framework.operators.FitnessEvaluator;
import ga.problems.knapsack.model.KnapsackSolution;

import java.util.List;

public class KnapsackFitnessEvaluator implements FitnessEvaluator {
    @Override
    public void evaluate(List<Solution> population) {
        population
            .forEach((solution) -> solution.setFitness(((KnapsackSolution) solution).getTotalValue()));
    }
}
