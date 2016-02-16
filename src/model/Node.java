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
     *
     */
    public Node() {
        this.right = new Node(Setting.None);
        this.down = new Node(Setting.None);
        this.left = new Node(Setting.None);
        this.up = new Node(Setting.None);
    }

    /**
     * @param setting
     */
    public Node(Setting setting) {
        this.setting = setting;
    }

    /**
     *
     * @param n
     * @param direction
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
     *
     */
    public void setColor(Setting setting) {
        this.setting = setting;
    }

    /**
     *
     * @return
     */
    public boolean isRed() {
        return this.setting == Setting.Red;
    }

    /**
     *
     * @return
     */
    public boolean isBlue() {
        return this.setting == Setting.Blue;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return this.setting == Setting.Empty;
    }

    /**
     *
     * @return
     */
    public boolean isNone() {
        return this.setting == Setting.None;
    }

    /**
     *
     * @return
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
     *
     * @return
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