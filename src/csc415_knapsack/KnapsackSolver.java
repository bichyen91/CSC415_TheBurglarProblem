package csc415_knapsack;

import java.util.Arrays;
import java.util.Comparator;

public class KnapsackSolver {

    public static void main(String[] args) {
        KnapsackItem items1[] = {
            new KnapsackItem("stereo", 4, 3000),
            new KnapsackItem("guitar", 1, 1500),
            new KnapsackItem("laptop", 3, 2000)
        };
        System.out.println("Problem 1: ");
        KnapsackSolver.solve(items1);

        KnapsackItem items2[] = {
            new KnapsackItem("stereo", 5, 3000),
            new KnapsackItem("guitar", 2, 1500),
            new KnapsackItem("laptop", 3, 2000),
            new KnapsackItem("phone", 1, 2000)
        };
        System.out.println("Problem 2: ");
        KnapsackSolver.solve(items2);

    }

    public static void sortItems(KnapsackItem items[]) {
        Arrays.sort(items, Comparator.comparingInt(item -> item.weight));
    }

    private static void solve(KnapsackItem items[]) {
        sortItems(items);
        // Create the board
        KnapsackBoard board = new KnapsackBoard(items);
        // Fill in the first row (row 0).
        // We're going to put items[0] into every cell in this row.
        for (int c = 0; c < board.nCols; c++) {
            if (c >= items[0].weight - 1) {
                board.set(0, c, items[0]);
            }
        }

        // Fill in the remaining rows
        for (int r = 1; r < board.nRows; r++) {  // r corresponds to items
            for (int c = 0; c < board.nCols; c++) {  // c correspond to weights

                // Get the values from the row above.
                KnapsackBoardCells preCellTop = board.get(r - 1, c);
                KnapsackBoardCells preCellLeft = new KnapsackBoardCells();
                if (c != 0) {
                    preCellLeft = board.get(r, c - 1);
                }

                if (c < items[r].weight - 1) {
                    board.copyCell(r, c, preCellTop);
                } else if (c == items[r].weight - 1) {
                    if (preCellLeft != null) {
                        if (preCellTop.curValue < items[r].value
                                && preCellLeft.curValue < items[r].value) {
                            board.set(r, c, items[r]);
                        } else if (preCellTop.curValue < preCellLeft.curValue) {
                            board.copyCell(r, c, preCellLeft);
                        } else {
                            board.copyCell(r, c, preCellTop);
                        }
                    } else {
                        if (preCellTop.curValue < items[r].value) {
                            board.set(r, c, items[r]);
                        } else {
                            board.copyCell(r, c, preCellTop);
                        }
                    }
                } else {
                    int remainWeight = c + 1 - items[r].weight;
                    KnapsackBoardCells preCellRemain = board.get(r - 1, remainWeight - 1);
                    int tempValue = items[r].value + preCellRemain.curValue;

                    if (preCellLeft != null) {
                        if (preCellTop.curValue < tempValue
                                && preCellLeft.curValue < tempValue) {
                            board.copyCell(r, c, preCellRemain);
                            board.set(r, c, items[r]);
                        } else if (preCellTop.curValue < preCellLeft.curValue) {
                            board.copyCell(r, c, preCellLeft);
                        } else {
                            board.copyCell(r, c, preCellTop);
                        }
                    } else {
                        if (preCellTop.curValue < items[r].value) {
                            board.set(r, c, items[r]);
                        } else {
                            board.copyCell(r, c, preCellTop);
                        }
                    }
                }
            }
        }
        board.printFullBoard();
    }
}



