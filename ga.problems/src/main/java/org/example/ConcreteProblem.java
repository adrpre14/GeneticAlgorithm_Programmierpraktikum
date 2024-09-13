package org.example;

import ga.framework.GeneticAlgorithm;
import ga.framework.TopKSurvival;
import ga.framework.TournamentSelection;
import ga.framework.model.Solution;
import ga.problems.knapsack.model.Item;
import ga.problems.knapsack.model.KnapsackProblem;
import ga.problems.knapsack.model.KnapsackSolution;
import ga.problems.knapsack.operators.KnapsackFitnessEvaluator;
import ga.problems.knapsack.operators.KnapsackMutation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ConcreteProblem {
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm();
        KnapsackProblem problem = new KnapsackProblem(new ArrayList<>(
                List.of(
                        new Item(5, 10),
                        new Item(4, 8),
                        new Item(4, 6),
                        new Item(4, 4),
                        new Item(3, 7),
                        new Item(3, 4),
                        new Item(2, 6),
                        new Item(2, 3),
                        new Item(1, 3),
                        new Item(1, 1)
                        )
        ), 11);
        List<Solution> solutions = ga.solve(problem)
            .withPopulationOfSize(1000)
            .evolvingSolutionsWith(new KnapsackMutation())
            .evaluatingSolutionsBy(new KnapsackFitnessEvaluator())
            .selectingSurvivorsWith(new TopKSurvival(450))
            .performingSelectionWith(new TournamentSelection())
            .stoppingAtEvolution(1000000)
            .runOptimization();

        solutions.stream()
                .map(s -> (KnapsackSolution) s)
                .sorted(Comparator.comparingDouble(Solution::getFitness).reversed())
                .limit(5)
                .forEach(s -> {
                    double fitness = s.getFitness();
                    List<Item> items = s.getItemsInTheBag();

                    System.out.println("----------------");
                    System.out.println("Fitness: " + fitness);
                    System.out.println("Items in the bag: " +
                            items.stream().map(i -> "(" + i.getWeight() + ", " + i.getValue() + ")")
                                    .reduce((a, b) -> a + ", " + b).orElse("None")
                    );
        //            System.out.println("Total value: " + s.getTotalValue());
                });
    }
}
