package ga.framework;

import ga.framework.model.Solution;
import ga.framework.operators.SurvivalException;
import ga.framework.operators.SurvivalOperator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TopKSurvival implements SurvivalOperator {
    private final int k;

    public TopKSurvival(int k) {
        this.k = k;
    }

    @Override
    public List<Solution> selectPopulation(List<Solution> candidates, int populationSize) throws SurvivalException {
        if (k > populationSize) {
            throw new SurvivalException("k is greater than the population size");
        }
        ArrayList<Solution> sortedCandidates = new ArrayList<>(candidates);

        sortedCandidates.sort(Comparator.comparingDouble(Solution::getFitness).reversed());

        List<Solution> selectedPopulation = new ArrayList<>(sortedCandidates.subList(0, k));

        Random random = new Random();
        while (selectedPopulation.size() < populationSize) {
            selectedPopulation.add(sortedCandidates.get(random.nextInt(sortedCandidates.size())));
        }

        return selectedPopulation;
    }
}
