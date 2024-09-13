package ga.problems.knapsack.model;

import ga.framework.model.NoSolutionException;
import ga.framework.model.Problem;
import ga.framework.model.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KnapsackProblem implements Problem {
    private final double capacity;
    private final List<Item> allItems = new ArrayList<>();

    public KnapsackProblem(List<Item> allItems, double capacity) {
        this.capacity = capacity;
        this.allItems.addAll(allItems);
    }

    @Override
    public Solution createNewSolution() throws NoSolutionException {
        double currentCapacity = capacity;
        List<Item> itemsInTheBag = new ArrayList<>();
        while (currentCapacity > 0) {
            double finalCurrentCapacity = currentCapacity;
            List<Item> itemsToChoseFrom = allItems.stream()
                    .filter(i -> i.getWeight() <= finalCurrentCapacity)
                    .toList();

            if (itemsToChoseFrom.isEmpty()) break;

            Item item = itemsToChoseFrom.get(new Random().nextInt(itemsToChoseFrom.size()));
            currentCapacity -= item.getWeight();
            itemsInTheBag.add(item);
        }

        if (itemsInTheBag.isEmpty()) throw new NoSolutionException("None of the items fit in the bag!");
        return new KnapsackSolution(this, itemsInTheBag);
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    public double getMaxValue() {
        return this.allItems.stream().map(Item::getValue).reduce(0d, Double::sum);
    }

    public double getCapacity() {
        return capacity;
    }
}
