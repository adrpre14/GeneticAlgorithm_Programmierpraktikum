package ga.problems.knapsack.operators;

import ga.framework.model.Solution;
import ga.framework.operators.EvolutionException;
import ga.framework.operators.EvolutionaryOperator;
import ga.problems.knapsack.model.Item;
import ga.problems.knapsack.model.KnapsackSolution;

import java.util.List;

public class KnapsackMutation implements EvolutionaryOperator {
    @Override
    public Solution evolve(Solution solution) throws EvolutionException {
        KnapsackSolution solutionObj = (KnapsackSolution) solution;
        int randomNumber = (int) Math.round(Math.random()); // 0 or 1
        // if 0, remove a random item from the bag else add a random item to the bag
        if (randomNumber == 0) {
            try {
                return new RemoveRandomItemMutation().evolve(solutionObj);
            }
            catch (EvolutionException e) {
                return new AddRandomItemMutation().evolve(solutionObj);
            }
        } else {
            try {
                return new AddRandomItemMutation().evolve(solutionObj);
            }
            catch (EvolutionException e) {
                return new RemoveRandomItemMutation().evolve(solutionObj);
            }
        }
    }
}

class RemoveRandomItemMutation implements EvolutionaryOperator {
    @Override
    public Solution evolve(Solution solution) throws EvolutionException {
        KnapsackSolution solutionObj = new KnapsackSolution(solution.getProblem(), ((KnapsackSolution) solution).getItemsInTheBag());
        if (solutionObj.getItemsInTheBag().isEmpty()) throw new EvolutionException("No items in the bag");
        int randomIndex = (int) (Math.random() * solutionObj.getItemsInTheBag().size());
        solutionObj.removeItemFromTheBag(randomIndex);
        return solutionObj;
    }
}

class AddRandomItemMutation implements EvolutionaryOperator {
    @Override
    public Solution evolve(Solution solution) throws EvolutionException {
        KnapsackSolution solutionObj =
                new KnapsackSolution(solution.getProblem(), ((KnapsackSolution) solution).getItemsInTheBag());
        List<Item> itemsThatCanFit =
                solutionObj.getRemainingItems().stream()
                        .filter(item -> item.getWeight() <= solutionObj.getRemainingCapacity())
                        .toList();
        if (itemsThatCanFit.isEmpty()) throw new EvolutionException("No more items can fit in the bag");
        int randomIndex = (int) (Math.random() * itemsThatCanFit.size());
        solutionObj.addItemInTheBag(itemsThatCanFit.get(randomIndex));
        return solutionObj;
    }
}
