package application;

import java.util.*;

enum Setting {
    None, Empty, Red, Blue
}

enum Adjacency {
    Right, Down, Left, Up;
    public Adjacency getNext() {
        return values()[(ordinal() + 1) % values().length];
    }
}


public class Node {

    private Setting setting = Setting.Empty;

    private Node right;
    private Node down;
    private Node left;
    private Node up;

    private Node[] adj = {right, down, left, up};

    public Node() {
        this.right = new Node(Setting.None);
        this.down = new Node(Setting.None);
        this.left = new Node(Setting.None);
        this.up = new Node(Setting.None);
    }

    public Node(Setting setting) {
        this.setting = setting;
    }

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

    public void setNone() {
        this.setting = Setting.None;
    }

    public void setRed() {
        this.setting = Setting.Red;
    }

    public void setBlue() {
        this.setting = Setting.Blue;
    }

    public void setEmpty() {
        this.setting = Setting.Empty;
    }

    public boolean isRed() {
        return this.setting == Setting.Red;
    }

    public boolean isBlue() {
        return this.setting == Setting.Blue;
    }

    public boolean isEmpty() {
        return this.setting == Setting.Empty;
    }

    public boolean isNone() {
        return this.setting == Setting.None;
    }


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

    @Override
    public String toString() {
        String str = "";
        str += this.value() + " : ";
        str += "Right: " + this.right.value() + ", ";
        str += "Down: " + this.down.value() + ", ";
        str += "Left: " + this.left.value() + ", ";
        str += "Up: " + this.up.value();
        return str;
    }

    public static void main(String[] args) {
        Node n = new Node();
        Node m = new Node();
        n.addVertex(m, Adjacency.Right);
        System.out.println(n);
        System.out.println(m);
    }

}