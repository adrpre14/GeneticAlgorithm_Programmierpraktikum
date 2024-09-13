package ga.framework;

import ga.framework.model.NoSolutionException;
import ga.framework.model.Problem;
import ga.framework.model.Solution;
import ga.framework.operators.*;

import java.util.List;

public class main {
    public static void main(String[] args) {
        Problem yourProblem = new Problem() {
            @Override
            public Solution createNewSolution() throws NoSolutionException {
                return null;
            }
        };
        EvolutionaryOperator yourEvolutionaryOperator = new EvolutionaryOperator() {
            @Override
            public Solution evolve(Solution solution) throws EvolutionException {
                return null;
            }
        };
        FitnessEvaluator yourFitnessEvaluator = new FitnessEvaluator() {
            @Override
            public void evaluate(List<Solution> population) {
            }
        };
        SelectionOperator yourSelectionOperator = new SelectionOperator() {
            @Override
            public Solution selectParent(List<Solution> candidates) {
                return null;
            }
        };
        SurvivalOperator yourSurvivalOperator = new SurvivalOperator() {
            @Override
            public List<Solution> selectPopulation(List<Solution> candidates, int populationSize) throws SurvivalException {
                return null;
            }
        };

        GeneticAlgorithm ga = new GeneticAlgorithm();
        List<Solution> result = ga.solve(yourProblem)
                .withPopulationOfSize(10)
                .evolvingSolutionsWith(yourEvolutionaryOperator)
                .evolvingSolutionsWith(yourEvolutionaryOperator)
                .evaluatingSolutionsBy(yourFitnessEvaluator)
                .selectingSurvivorsWith(yourSurvivalOperator)
                .performingSelectionWith(yourSelectionOperator)
                .stoppingAtEvolution(100)
                .runOptimization();

        System.out.println(result);
    }
}
