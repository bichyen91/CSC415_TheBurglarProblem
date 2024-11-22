package csc415_knapsack;

import java.util.ArrayList;

public class KnapsackBoardCells {

    int curWeight;  // What weight does this column in the board correspond to.
    int curValue;
    ArrayList<KnapsackItem> itemStored;

    KnapsackBoardCells() {
        itemStored = new ArrayList<>();
    }
}
