package csc415_knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class KnapsackBoard {

    // The board as has columns corresponding to weights: 1, 2, 3, 4, etc.
    // The board has rows corresponding to items being considered.
    KnapsackBoardCells board[][];
    int nRows;     // rows correspond to items so this is the number of items.
    int nCols;     // columns corresponds to weights so this is max weight - 1.

    /**
     * Given an array items, creates all the cells in the board.
     *
     * @param items
     */
    public KnapsackBoard(KnapsackItem items[]) {
        sortItems(items);
        // Figure out the number of rows and columns we need.
        nRows = items.length;
        nCols = items[nRows - 1].weight;
        // Create the board matrix.
        board = new KnapsackBoardCells[nRows][nCols];
        // Create the KnapsackBoardCells in the matrix.
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                board[r][c] = new KnapsackBoardCells();
            }
        }
    }

    private void sortItems(KnapsackItem items[]) {
        Arrays.sort(items, Comparator.comparingInt(item -> item.weight));
    }

    public void printFullBoard() {
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (board[r][c].curWeight == 0) {
                    System.out.print("\t\tN/A");
                } else {
                    System.out.print("\t\t" + board[r][c].curValue + "(");
                    int last = board[r][c].itemStored.size() - 1;
                    int i = 0;
                    for (KnapsackItem item : board[r][c].itemStored) {
                        if (i != last) {
                            System.out.print(item.name + ", ");
                        } else {
                            System.out.print(item.name + ")");
                        }
                        i++;
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    // Keep in mind that r corresponds to item row.  c corresponds to weight column
    public KnapsackBoardCells get(int r, int c) {
        return board[r][c];
    }

    public void set(int r, int c, KnapsackItem item) {
        board[r][c].itemStored.add(item);
        board[r][c].curWeight += item.weight;
        board[r][c].curValue += item.value;
    }

    public void copyCell(int r, int c, KnapsackBoardCells preCell) {
        board[r][c].itemStored = (ArrayList<KnapsackItem>) preCell.itemStored.clone();
        board[r][c].curWeight = preCell.curWeight;
        board[r][c].curValue = preCell.curValue;
    }
}
