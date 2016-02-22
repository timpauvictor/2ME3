package model;

/**
 *
 */
public class Node {

    private Setting setting = Setting.Empty;

    private Node right;
    private Node down;
    private Node left;
    private Node up;

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
    private Node(Setting setting) {
        this.setting = setting;
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
        this.setting = setting;
    }

    /**
     * retrives the current color
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
                return "Empty";
            case Red:
                return "Red";
            case Blue:
                return "Blue";
        }
        return "";
    }

    /**
     * @return a string contain the color of the current node plus its adjacent nodess
     */
    @Override
    public String toString() {
        String str = "";
        str += this.value() + " : ";
        str += "Right: " + this.right.value() + ", ";
        str += "Down: " + this.down.value() + ", ";
        str += "Left: " + this.left.value() + ", ";
        str += "Up: " + this.up.value() + "\n";
        return str;
    }
}