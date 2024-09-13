package ga.problems.knapsack.model;

public class Item {
    private final double weight;
    private final double value;

    public Item(double weight, double value) {
        this.weight = weight;
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }
}
