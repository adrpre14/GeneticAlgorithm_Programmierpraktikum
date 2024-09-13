package ga.problems.knapsack.model;

import ga.framework.model.Problem;
import ga.framework.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class KnapsackSolution extends Solution {
    private final List<Item> itemsInTheBag = new ArrayList<>();

    public KnapsackSolution(Problem problem, List<Item> itemsInTheBag) {
        super(problem);
        this.itemsInTheBag.addAll(itemsInTheBag);
    }

    public KnapsackProblem getProblem() {
        return (KnapsackProblem) super.getProblem();
    }

    public List<Item> getItemsInTheBag() {
        return this.itemsInTheBag;
    }

    public void addItemInTheBag(Item item) {
        this.itemsInTheBag.add(item);
    }

    public void removeItemFromTheBag(int index) {
        this.itemsInTheBag.remove(index);
    }

    public List<Item> getRemainingItems() {
        return this.getProblem().getAllItems().stream()
                .filter(item -> !this.itemsInTheBag.contains(item))
                .toList();
    }

    public double getTotalValue() {
        return this.itemsInTheBag.stream()
                .map(Item::getValue)
                .reduce(0d, Double::sum);
    }

    public double getRemainingCapacity() {
        return this.getProblem().getCapacity() -
                this.itemsInTheBag.stream().map(Item::getWeight).reduce(0d, Double::sum);
    }
}
