package model;

import javafx.scene.shape.Circle;

import java.io.*;

public class Board {

    // 8 * (n % 3)

    private Node[] outer = new Node[8];
    private Node[] inner = new Node[8];
    private Node[][] board = new Node[5][];

    private PlayerColor turn = PlayerColor.Black;

    /**
     * default constructor for board class
     * creates a 6-mens-morris board through
     * the use of a inner and outer array then
     * finally putting the two in a jagged array
     */
    public Board() {
        for (int i = 0; i < inner.length; i++) {
            inner[i] = new Node();
            outer[i] = new Node();
        }
        square(inner);
        square(outer);
        connect(inner, outer);
        create(inner, outer);
    }

    /**
     * alternative constructor to load a saved state as a new board, constructor is similar
     * the default board, using similar methodology to connect the inner and outer rows
     * @param file the name of the file storing the saved state
     */
    public Board(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            setTurn(PlayerColor.convert(reader.readLine()));

            for (int i = 0; i < inner.length; i++) {
                Node n = new Node();
                n.setColor(Setting.fromString(reader.readLine()));
                inner[i] = n;
            }

            for (int i = 0; i < outer.length; i++) {
                Node n = new Node();
                n.setColor(Setting.fromString(reader.readLine()));
                outer[i] = n;
            }

            connect(inner,outer);
            create(inner, outer);
        } catch (IOException e) {
        	
            System.out.println("error reading stored file, did you tamper with it???");
        }
    }

    /**
     * accepts any non-linked array of nodes
     * and links the nodes to form a "square"
     * such that a -> b -> c -> a
     *
     * @param nodes - the unlinked array of nodes
     */
    private void square(Node[] nodes) {
        Adjacency adj = Adjacency.Right;
        for (int i = 0; i < nodes.length; i++) {
            if (i % 2 == 0 && i != 0) {
                adj = adj.getNext();
            }
            if (i == nodes.length - 1) {
                nodes[i].addVertex(nodes[0], adj);
            } else {
                nodes[i].addVertex(nodes[i + 1], adj);
            }
        }
    }

    /**
     * accepts two square linked arrays of nodes
     * and links every 2nd node of each array to
     * each other e.g. {a,b,c} connect {e,d,f}
     * b -> d & d -> b
     *
     * @param inner - the first square array
     * @param outer - the second square array
     */
    private void connect(Node[] inner, Node[] outer) {
        Adjacency adj = Adjacency.Down;
        for (int i = 1; i < outer.length; i += 2) {
            outer[i].addVertex(inner[i], adj);
            adj = adj.getNext();
        }
    }

    /**
     * creates the board in the form of a jagged
     * array following pattern:
     * R0C0, R0C1, R0C2,
     * R1C0, R1C1, R1C2,
     * R2C0, R2C1, R2C2, R2C3,
     * R3C0, R3C1, R3C2,
     * R4C0, R4C1, R4C2
     * representing the Morris board
     *
     * @param inner - the inner square of the 6-men-morris board
     * @param outer - the outer square of the 6-men-morris board
     */
    private void create(Node[] inner, Node[] outer) {
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


    /**
     * retrieves a node from the board through
     * 0-based indexed row and columns
     * @param row - the row index of the desired node
     * @param column - the column index of the desired node
     * @return the node @ {row, column}
     */
    public Node getNode(int row, int column) {
        return board[row][column];
    }

    /**
     * retrieves the variable keeping track of turns
     * @return the current player's turn
     */
    public PlayerColor getTurn() {
        return this.turn;
    }

    /**
     * change the turn to some turn parameter (unspecified)
     * @param turn the new setting for the turn
     */
    public void setTurn(PlayerColor turn) {
        this.turn = turn;
    }

    /**
     * alternating method to switch between two player's turns
     * if red -> then blue
     * and vice versa
     * otherwise, the turn remains the same
     */
    public void changeTurn() {
        if (turn == PlayerColor.Blue)
            turn = PlayerColor.Red;
        else if (turn == PlayerColor.Red)
            turn = PlayerColor.Blue;
        else
            return;
    }

    /**
     * changes a node's color through 0-based
     * indexed row and column
     * @param row - the row index of the node
     * @param column - the column index of the node
     * @param setting - the new color of the node
     */
    public void setNode(int row, int column, Setting setting) {
        board[row][column].setColor(setting);
    }

    /**
     * overloaded method to
     * changes a node's color through 0-based
     * indexed row and column
     *
     * @param row    - the row index of the node
     * @param column - the column index of the node
     * @param circle  - the object with the new color of the node
     */
    public void setNode(int row, int column, Circle circle) {
        board[row][column].setColor(circle);
    }

    /**
     * check if any nodes on the board are illegal
     * @return the legality of the board
     */
    public boolean isLegal() {
        for (Node[] nodes : board)
            for (Node n : nodes)
                if (!n.isLegal())
                    return false;
        return true;
    }

    /**
     * a method to output a string for the legality
     * of the baord
     *
     * @return "legal" if isLegal() else a string detailing the illegal nodes
     */
    public String isLegalString() {
        if (this.isLegal())
            return "legal";
        else {
            String output = "Nodes ";
            for (int i = 0; i < board.length; i++)
                for (int j = 0; j < board[i].length; j++) {
                    if (!(board[i][j].isLegal()))
                        output += "R" + i + "C" + j + " ";
                }
            output += "are illegal";
            return output;
        }
    }

    /**
     * maps a 2-D array of circles to the 2-D array of nodes
     * of the board
     *
     * @param circles - the 2 - D array of circles
     */
    public void update(Circle[][] circles) {
        for (int i = 0; i < circles.length; i++)
            for (int j = 0; j < circles[i].length; j++) {
                this.setNode(i, j, circles[i][j]);
            }
    }


    public void toFile(String filename, PlayerColor turn) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename), false));
            writer.write(turn.toString());
            writer.newLine();

            for (Node n: outer) {
                writer.write(n.value());
                writer.newLine();
            }

            for (Node n: inner) {
                writer.write(n.value());
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("error writing to file");
        }
    }
    /**
     * to get string value of the board
     * in format:
     * R0C0
     * R0C1
     * R0C...
     *
     * @return - string value of the board
     */
    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                output += "R" + i + "C" + j + " -> " + board[i][j] + "\n";
        return output;
    }

    public static void main(String[] args) {
        Board b = new Board();
        b.toFile("data/data.board", PlayerColor.Red);
        Board c = new Board("data/data.board");
        System.out.println(c.toString());
        System.out.println(c.getTurn());
    }
}