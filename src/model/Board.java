
public class Board {

    private Node[] outer = new Node[8];
    private Node[] inner = new Node[8];
    // 8 * (n % 3)
    public Board() {
        for (int i = 0; i < inner.length; i++) {
            inner[i] = new Node();
            outer[i] = new Node();
        }
        this.square(inner);
        this.square(outer);
        this.connect(outer,inner);
    }


    public Node[] getInner() {
        return this.inner;
    }

    public void square (Node[] b) {
        Adjacency a = Adjacency.Right;
        for (int i = 0; i < b.length; i++) {
            if (i % 2 == 0 && i != 0) {
                a = a.getNext();
            }
            if (i == b.length - 1) {
                b[i].addVertex(b[0],a);
            }
            else {
                b[i].addVertex(b[i + 1], a);
            }
        }
    }
    public void connect(Node[] a, Node[] b) {
        Adjacency adj = Adjacency.Down;
        for (int i = 1; i < a.length; i += 2) {
            a[i].addVertex(b[i], adj);
            adj = adj.getNext();
        }
    }

    public static void main(String[] args) {
        Board b = new Board();
        for (Node n : b.getInner()) {
            System.out.println(n);
        }
    }
}