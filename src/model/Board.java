package model;

import javafx.scene.shape.Circle;

import java.io.*;
import java.util.Arrays;

public class Board {

    // 8 * (n % 3)

    private Node[] outer = new Node[8];
    private Node[] inner = new Node[8];
    private Node[][] board = new Node[5][];
    private String[][] prevState = new String[5][];
    private boolean[][] milledBefore = new boolean[5][];
    private PlayerColor turn = PlayerColor.Black;

    /**
     * default constructor for board class
     * creates a 6-mens-morris board through
     * the use of a inner and outer array then
     * finally putting the two in a jagged array
     */
    public Board() {
        for (int i = 0; i < inner.length; i++) {
            inner[i] = new Node("I" + i);
            outer[i] = new Node("O" + i);
        }
        square(inner);
        square(outer);
        connect(inner, outer);
        create(inner, outer);

        for (int i = 0; i < board.length; i++){
            milledBefore[i] = new boolean[board[i].length];
            for (int j = 0; j < milledBefore[i].length; j++)
                milledBefore[i][j] = false;
        }

        record();
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

            for (int i = 0; i < outer.length; i++) {
                Node n = new Node("O" + i);
                n.setColor(Setting.fromString(reader.readLine()));
                outer[i] = n;
            }

            for (int i = 0; i < inner.length; i++) {
                Node n = new Node("I" + i);
                n.setColor(Setting.fromString(reader.readLine()));
                inner[i] = n;
            }

            square(inner);
            square(outer);
            connect(inner,outer);
            create(inner, outer);
        } catch (IOException e) {
        	
            System.out.println("error reading stored file, did you tamper with it???");
        }

        for (int i = 0; i < board.length; i++){
            milledBefore[i] = new boolean[board[i].length];
            for (int j = 0; j < board[i].length; j++)
                milledBefore[i][j] = false;
        }

        record();
    }

    private void record() {
        for (int i = 0; i < board.length; i++) {
            prevState[i] = new String[board[i].length];
            for (int j = 0; j < board[i].length; j++) {
                prevState[i][j] = board[i][j].toString();
            }
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
            board[3][i] = inner[6 - i];
            board[4][i] = outer[6 - i];
        }

        board[2][0] = outer[7];
        board[2][1] = inner[7];
        board[2][2] = inner[3];
        board[2][3] = outer[3];
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

    public Node getNode(String position) {
        String[] positions = position.split("C");
        positions[0] = positions[0].replace("R", "");
        return getNode(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
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

    public void refresh(int row, int column) {
        this.milledBefore[row][column] = false;
    }

    public void refresh(String position) {
        String[] positions = position.split("C");
        positions[0] = positions[0].replace("R", "");
        refresh(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
    }

    public boolean hasMills(Setting color) {
        if (inner[0].isColor(color) && inner[1].isColor(color) && inner[2].isColor(color) &&
                (!milledBefore[1][0] || !milledBefore[1][1] || !milledBefore[1][2])) {
            milledBefore[1][0] = true;
            milledBefore[1][1] = true;
            milledBefore[1][2] = true;
            return true;
        }
        else if (inner[2].isColor(color) && inner[3].isColor(color) && inner[4].isColor(color) &&
                (!milledBefore[1][2] || !milledBefore[2][2] || !milledBefore[3][2])) {
            milledBefore[1][2] = true;
            milledBefore[2][2] = true;
            milledBefore[3][2] = true;
            return true;
        }
        else if (inner[4].isColor(color) && inner[5].isColor(color) && inner[6].isColor(color) &&
                (!milledBefore[3][2] || !milledBefore[3][1] || !milledBefore[3][0])) {
            milledBefore[3][2] = true;
            milledBefore[3][1] = true;
            milledBefore[3][0] = true;
            return true;
        }
        else if (inner[6].isColor(color) && inner[7].isColor(color) && inner[0].isColor(color) &&
                (!milledBefore[3][0] || !milledBefore[2][0] || !milledBefore[1][0])) {
            milledBefore[3][0] = true;
            milledBefore[2][0] = true;
            milledBefore[1][0] = true;
            return true;
        }
        else if (outer[0].isColor(color) && outer[1].isColor(color) && outer[2].isColor(color) &&
                (!milledBefore[0][0] || !milledBefore[0][1] || !milledBefore[0][2])) {
            milledBefore[0][0] = true;
            milledBefore[0][1] = true;
            milledBefore[0][2] = true;
            return true;
        }
        else if (outer[2].isColor(color) && outer[3].isColor(color) && outer[4].isColor(color) &&
                (!milledBefore[0][2] || !milledBefore[2][3] || !milledBefore[4][2])) {
            milledBefore[0][2] = true;
            milledBefore[2][3] = true;
            milledBefore[4][2] = true;
            return true;
        }
        else if (outer[4].isColor(color) && outer[5].isColor(color) && outer[6].isColor(color) &&
                (!milledBefore[4][2] || !milledBefore[4][1] || !milledBefore[4][0])) {
            milledBefore[4][2] = true;
            milledBefore[4][1] = true;
            milledBefore[4][0] = true;
            return true;
        }
        else if (outer[6].isColor(color) && outer[7].isColor(color) && outer[0].isColor(color) &&
                (!milledBefore[4][0] || !milledBefore[2][0] || !milledBefore[0][0])) {
            milledBefore[4][0] = true;
            milledBefore[2][0] = true;
            milledBefore[0][0] = true;
            return true;
        }

        return false;
    }

    /**
     * moves one node to another if valid during the move phase of the game
     * @param sRow - the source row
     * @param sColumn - the source column
     * @param dRow - the destination row
     * @param dColumn - the destination column
     * @throws Exception - an exception is thrown if a non-valid move is made
     */
    public void move(int sRow, int sColumn, int dRow, int dColumn) throws Exception{
        if (board[sRow][sColumn].isColor(Setting.Blue) || board[sRow][sColumn].isColor(Setting.Red)) {
            if (board[dRow][dColumn].isValid()) {
                board[dRow][dColumn].setColor(board[sRow][sColumn].getColor());
                board[sRow][sColumn].setColor(Setting.Empty);
                milledBefore[dRow][dColumn] = false;
                milledBefore[sRow][sColumn] = false;
            }
            else
                throw new Exception("invalid move, node already occupied");
        }
        else
            throw new Exception("can't move an empty node");
    }
    
    
    public void move(Node n, Node d) {
    	d.setColor(n.getColor());
    	n.setColor(Setting.Empty);
    }

    /**
     * maps a 2-D array of circles to the 2-D array of nodes
     * of the board
     *
     * @param circles - the 2 - D array of circles
     */
    public void update(Circle[][] circles) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].toString().equals(prevState[i][j])) {
                    milledBefore[i][j] = false;
                }
            }

        for (int i = 0; i < circles.length; i++)
            for (int j = 0; j < circles[i].length; j++) {
                this.setNode(i, j, circles[i][j]);
            }

        record();
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
        System.out.println(b.getNode("R0C2").adjacentTo(b.getNode("R2C3")));
    }


}