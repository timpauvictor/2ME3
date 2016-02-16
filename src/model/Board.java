package model;

import java.util.Arrays;

public class Board {


    /**
     * R0C0, R0C1, R0C2,
     * R1C0, R1C1, R1C2,
     * R2C0, R2C1, R2C2, R2C3,
     * R3C0, R3C1, R3C2,
     * R4C0, R4C1, R4C2
     */
    private Node[] outer = new Node[8];
    private Node[] inner = new Node[8];
    private Node[][] board = new Node[5][];

    // 8 * (n % 3)
    public Board() {
        for (int i = 0; i < inner.length; i++) {
            inner[i] = new Node();
            outer[i] = new Node();
        }
        square(inner);
        square(outer);
        connect(outer, inner);
        create();
    }

    private void square(Node[] b) {
        Adjacency a = Adjacency.Right;
        for (int i = 0; i < b.length; i++) {
            if (i % 2 == 0 && i != 0) {
                a = a.getNext();
            }
            if (i == b.length - 1) {
                b[i].addVertex(b[0], a);
            } else {
                b[i].addVertex(b[i + 1], a);
            }
        }
    }

    private void connect(Node[] a, Node[] b) {
        Adjacency adj = Adjacency.Down;
        for (int i = 1; i < a.length; i += 2) {
            a[i].addVertex(b[i], adj);
            adj = adj.getNext();
        }
    }

    private void create() {
        for (int i = 0; i < this.board.length; i++) {
            if (i != 2)
                board[i] = new Node[3];
            else
                board[i] = new Node[4];
        }

        for (int i = 0; i < 3; i++) {
            board[0][i] = outer[i];
            board[1][i] = inner[i];
            board[3][i] = inner[7 - i];
            board[4][i] = outer[7 - i];
        }

        for (int i = 0; i <= 2; i += 2) {
            board[2][i] = inner[7 - i * 2];
            board[2][i + 1] = inner[7 - i * 2];
        }
    }

    public String toString() {
        return Arrays.deepToString(board);
    }

    /**
     * use this to retrieve a node
     *
     * @param row
     * @param column
     * @return
     */
    public Node getNode(int row, int column) {
        return board[row][column];
    }

    /**
     * use this to change color of a Node to one of the Setting Class
     *
     * @param row
     * @param column
     * @param setting
     */
    public void setNode(int row, int column, Setting setting) {
        board[row][column].setColor(setting);
    }

    /**
     * really tired, work concluded on 20/02, will continue later on 21/02
     */
}