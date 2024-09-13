package ga.framework;

import ga.framework.model.NoSolutionException;
import ga.framework.model.Problem;
import ga.framework.model.Solution;
import ga.framework.operators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GeneticAlgorithm extends GAProblem {
    private List<Solution> population;

    public GeneticAlgorithm() {}

    public List<Solution> runOptimization() {
        List<Solution> startPopulation = Stream.generate(() -> {
            try {
                return problem.createNewSolution();
            } catch (NoSolutionException e) {
                e.printStackTrace();
                return null;
            }
        }).limit(populationSize).toList();

        fitnessEvaluator.evaluate(startPopulation);

        List<Solution> iteratedPopulation = startPopulation;
        for (int i = 0; i < iterations; i++) {
            List<Solution> finalIteratedPopulation = new ArrayList<>(iteratedPopulation);
            iteratedPopulation = Stream
                    .generate(() -> {
                        return selectionOperator.selectParent(finalIteratedPopulation);
                    })
                    .limit(populationSize)
                    .map((parent) -> {
                        try {
                            EvolutionaryOperator randomOperator = evolutionaryOperators.get((int) (Math.random() * evolutionaryOperators.size()));
                            return randomOperator.evolve(parent);
                        }
                        catch (EvolutionException e) {
                            return parent;
                        }
                    }).toList();

            finalIteratedPopulation.addAll(iteratedPopulation);

            fitnessEvaluator.evaluate(finalIteratedPopulation);

            try {
                iteratedPopulation = survivalOperator.selectPopulation(finalIteratedPopulation, populationSize);
            } catch (SurvivalException e) {
                e.printStackTrace();
            }
        }

        population = iteratedPopulation;

        return iteratedPopulation;
    }

    public int getPopulationSize() {
        return populationSize;
    }
}

class GAProblem extends GAPopulationSize {
    protected Problem problem;

    public GeneticAlgorithm solve(Problem problem) {
        this.problem = problem;
        return (GeneticAlgorithm) this;
    }
}

class GAPopulationSize extends GAEvolvingSolutions {
    protected int populationSize;

    public GeneticAlgorithm withPopulationOfSize(int populationSize) {
        this.populationSize = populationSize;
        return (GeneticAlgorithm) this;
    }
}

class GAEvolvingSolutions extends GAEvaluatingSolutions {
    protected List<EvolutionaryOperator> evolutionaryOperators = new ArrayList<>();

    public GeneticAlgorithm evolvingSolutionsWith(EvolutionaryOperator evolutionaryOperator) {
        evolutionaryOperators.add(evolutionaryOperator);
        return (GeneticAlgorithm) this;
    }
}

class GAEvaluatingSolutions extends GASurvivalOperator {
    protected FitnessEvaluator fitnessEvaluator;

    public GeneticAlgorithm evaluatingSolutionsBy(FitnessEvaluator fitnessEvaluator) {
        this.fitnessEvaluator = fitnessEvaluator;
        return (GeneticAlgorithm) this;
    }
}

class GASurvivalOperator extends GASelectionOperator {
    protected SurvivalOperator survivalOperator;

    public GeneticAlgorithm selectingSurvivorsWith(SurvivalOperator survivalOperator) {
        this.survivalOperator = survivalOperator;
        return (GeneticAlgorithm) this;
    }
}

class GASelectionOperator extends GAIterations {
    protected SelectionOperator selectionOperator;

    public GeneticAlgorithm performingSelectionWith(SelectionOperator selectionOperator) {
        this.selectionOperator = selectionOperator;
        return (GeneticAlgorithm) this;
    }
}

class GAIterations {
    protected int iterations;

    public GeneticAlgorithm stoppingAtEvolution(int iterations) {
        this.iterations = iterations;
        return (GeneticAlgorithm) this;
    }
}
