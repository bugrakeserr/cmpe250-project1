public class Node implements Comparable<Node> {
    String name;
    float gms;

    // Default constructor
    public Node() {

    }

    public Node(String name, float gms){
        this.name = name;
        this.gms = gms;
    }

    /**
     * Getter method for gms.
     * @return gms number.
     */
    public float getGms() {
        return gms;
    }

    /**
     * Getter method for name
     * @return name.
     */

    public String getName(){
        return name;
    }

    /**
     * Overriding compareTo method.
     * @param o the object to be compared.
     * @return 1 if bigger, 0 if equals, -1 if smaller.
     */
    @Override
    public int compareTo(Node o) {
        if(this.getGms() > o.getGms()) return 1;
        else if (this.getGms() < o.getGms()) return -1;
        else return 0;
    }
}
