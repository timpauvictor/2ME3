package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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


    private Node[] adj = {right, down, left, up};

    /**
     * default recursive constructor for a Node
     * generates 4 adjacent nodes
     */
    public Node() {
        this.right = new Node(Setting.None);
        this.down = new Node(Setting.None);
        this.left = new Node(Setting.None);
        this.up = new Node(Setting.None);
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
            return;
        }
    }

    /**
     * temporary method for check if another node has been laid
     * over this one
     * @return whether the current node is legal or not
     */
    public boolean isLegal() {
        return isEmpty;
    }

    /**
     * retrieves the current color
     * @return the current color
     */
    public Setting getColor() {
        return this.setting;
    }

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