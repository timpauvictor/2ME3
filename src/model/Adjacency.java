package model;

public enum Adjacency {
    Right, Down, Left, Up;

    public Adjacency getNext() {
        return values()[(ordinal() + 1) % values().length];
    }
}
