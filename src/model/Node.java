package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class Node {

    private Setting setting = Setting.Empty;

    private Node right;
    private Node down;
    private Node left;
    private Node up;

    private boolean isRed;
    private boolean isBlue;
    private boolean isEmpty = true;

    private String id = "";
    private Node[] adj = {right, down, left, up};

    /**
     * default recursive constructor for a Node
     * generates 4 adjacent nodes
     */
    public Node(String id) {
        this.right = new Node(Setting.None);
        this.down = new Node(Setting.None);
        this.left = new Node(Setting.None);
        this.up = new Node(Setting.None);
        this.id = id;
        this.adj = new Node[]{right, down, left, up};
    }

    /**
     * terminating constructor for an adjacent Node
     * only
     * @param setting - the color of the terminating node
     */
    public Node(Setting setting) {
        this.setting = setting;
    }
    
    
    public Node(Circle input){
    	setColor(input);
    }

    public String getId() {
        return this.id;
    }
    /**
     * updates one adjacent node of the current node
     * @param n - the new adjacent node
     * @param direction - the direction to be updated
     */
    public void addVertex(Node n, Adjacency direction) {
        switch(direction) {
            case Right:
                this.right = n;
                n.left = this;
                break;
            case Down:
                this.down = n;
                n.up = this;
                break;
            case Left:
                this.left = n;
                n.right = this;
                break;
            case Up:
                this.up = n;
                n.down = this;
                break;
        }
        this.updateAdj();
        n.updateAdj();
    }

    public void updateAdj() {
        this.adj[0] = this.right;
        this.adj[1] = this.down;
        this.adj[2] = this.left;
        this.adj[3] = this.up;
    }
    /**
     * changes the color of the current node
     * @param setting - the new color
     */
    public void setColor(Setting setting) {
        switch (setting) {
            case Red:
                isRed = true;
                isEmpty = false;
            case Blue:

        }
        this.setting = setting;
    }

    /**
     * overloaded method to accept a new color
     * based on the properties of a circle
     * @param circle - the new color object
     */
    public void setColor(Circle circle) {
        if (circle.getFill() == Color.RED) {
            isRed = true;
            isEmpty = false;
            this.setting = Setting.Red;
        } else if (circle.getFill() == Color.BLUE) {
            isBlue = true;
            isEmpty = false;
            this.setting = Setting.Blue;
        } else {
            isBlue = false;
            isRed = false;
            isEmpty = true;
            this.setting = Setting.Empty;
        }
    }

    /**
     * adjacency check for checking whether a node contains
     * another node in its adjacency list
     * @param other - the other node
     * @return true if node is contained, false otherwise
     */
    public boolean adjacentTo(Node other) {
        for (Node n : adj) {
            if (n != null)
                if (n.getId().equals(other.getId()))
                    return true;
        }
        return false;
    }

    /**
     * overloaded method to determine if node is adjacent to a
     * color
     * @param setting - if adjacent to this color
     * @return true if the node has a node with setting's color, false otherwise
     */
    public boolean adjacentTo(Setting setting) {
        for (Node n : adj)
            if (n != null)
                if (n.getColor() == setting)
                    return true;
        return false;
    }

    public ArrayList<Node> getAdjacent() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Node n : adj)
            if (n != null)
                if (n.getColor() != Setting.None)
                    nodes.add(n);
        return nodes;
    }

    /**
     * temporary method for check if another node has been laid
     * over this one
     * @return whether the current node is legal or not
     */
    public boolean isLegal() {
        return !(isBlue && isRed);
    }

    /**
     * check if the current node is empty
     * @return true if node can accept a setting
     */

    public boolean isValid() { return isEmpty; }
    /**
     * retrieves the current color
     * @return the current color
     */

    public Setting getColor() {
        return this.setting;
    }

    public boolean isColor(Setting color) { return this.setting == color;}

    /**
     * @return the string of the current color setting
     */
    public String value() {
        switch (this.setting) {
            case None:
                return "None";
            case Empty:
                return "Black";
            case Red:
                return "Red";
            case Blue:
                return "Blue";
        }
        return "";
    }

    /**
     * @return a string containing the color of the current node plus its adjacent nodes
     */
    @Override
    public String toString() {
        String str = "";
        str += this.value() + " | ";
        str += "Right: " + right.value() + ", ";
        str += "Down: " + down.value() + ", ";
        str += "Left: " + left.value() + ", ";
        str += "Up: " + up.value();
        return str;
    }

}