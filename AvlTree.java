import java.io.BufferedWriter;
import java.io.IOException;

public class AvlTree extends Node{

    // Construct the tree.
    public AvlTree(){
        root = null;
    }

    /**
     * Insert into the tree. The duplicates are ignored.
     * @param x is the item to insert
     * @param myWriter is the item to write to file.
      */


    public void insert(Node x, BufferedWriter myWriter) throws IOException {
        root = insert(x, root, myWriter);
    }

    /**
     *   Remove item from the tree.
     *   @param x is the item to be removed.
     */

    public void remove(Node x){
        root = remove(x, root);
    }

    /**
     * Method which is called by public remove method.
     * @param x the item to remove.
     * @param t is the root node of the subtree.
     * @return the new root of subtree.
     */

    private AvlNode<Node> remove (Node x, AvlNode<Node> t){
        if(t == null)
            return t;
        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return balance( t );
    }

    /**
     * Method to check who replaces the element that leaves the tree.
     * @param x the node that is left the tree.
     * @return the name of the replacing element.
     */
    public String leave( Node x ){return leave(x, root);}

    /**
     * Path from root to the target element.
     * @param x the target node.
     * @return array of nodes that are in the path.
     */

    public Node[] path(Node x){return path(x, root);}

    /**
     * @param x the node to know its rank.
     * @return the rank of the node.
     */
    public int rank(Node x){return rank(x, root);}

    /**
     * method to write to the file nodes that have the same rank with their information .
     * @param x the node that its rank is the reference.
     * @param myWriter writes the information of the node that has the same rank with x to the file.
     * @throws IOException
     */
    public void sameRank(Node x, BufferedWriter myWriter) throws IOException {sameRank(x, root, myWriter);}

    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Keeps the balance of the avl tree.
     * @param t is the root of the subtree.
     * @return subtree with the balanced form.
     */

    private AvlNode<Node> balance( AvlNode<Node> t )
    {

        if( t == null )
            return t;

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }

    /**
     * Internal method to insert into the tree.
     * @param x the node to insert.
     * @param t the root of the subtree.
     * @param myWriter writes to file related information about insertion.
     * @return new root of the subtree.
     * @throws IOException
     */
    private AvlNode<Node> insert(Node x, AvlNode<Node> t, BufferedWriter myWriter) throws IOException {
        if( t == null )
            return new AvlNode<Node>( x, null, null );

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 ) {
            myWriter.write(t.element.getName() + " welcomed " + x.getName() +"\n");
            t.left = insert(x, t.left, myWriter);
        }
        else if( compareResult > 0 ) {
            myWriter.write(t.element.getName() + " welcomed " + x.getName() + "\n");
            t.right = insert(x, t.right, myWriter);
        }
        else
            ;  // Duplicate; do nothing
        return balance( t );
    }

    /**
     * Finds the minimum element in the subtree.
     * @param t the root of the subtree.
     * @return the minimum root of the given subtree.
     */
    private AvlNode<Node> findMin( AvlNode<Node> t )
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    /**
     * Height of the root of the subtree.
     * @param t the root of the subtree.
     * @return the height of the given element.
     */
    private int height( AvlNode<Node> t )
    {
        return t == null ? -1 : t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */

    private AvlNode<Node> rotateWithLeftChild( AvlNode<Node> k2 )
    {
        AvlNode<Node> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */

    private AvlNode<Node> rotateWithRightChild( AvlNode<Node> k1 )
    {
        AvlNode<Node> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */

    private AvlNode<Node> doubleWithLeftChild( AvlNode<Node> k3 )
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */

    private AvlNode<Node> doubleWithRightChild( AvlNode<Node> k1 )
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }



    private static class AvlNode<Node>
    {
        // Constructors
        AvlNode( Node theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( Node theElement, AvlNode<Node> lt, AvlNode<Node> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        Node          element;      // The data in the node
        AvlNode<Node>  left;         // Left child
        AvlNode<Node>  right;        // Right child
        int               height;       // Height
    }


    /** The tree root. */

    private AvlNode<Node> root;


    /**
     * Internal method to public leave method.
     * @param x the node to leave the root.
     * @param t the root of the subtree.
     * @return name of the replacing element.
     */
    private String leave(Node x, AvlNode<Node> t){
        while( t != null )
        {
            int compareResult = x.compareTo( t.element );

            if( compareResult < 0 )
                t = t.left;
            else if( compareResult > 0 )
                t = t.right;
            else{
                if(t.right == null && t.left == null)
                    return "nobody";
                else if(t.right == null && t.left != null)
                    return t.left.element.getName();
                else if(t.right != null && t.left == null)
                    return t.right.element.getName();
                else{
                    t = t.right;
                    while( t.left != null )
                        t = t.left;
                    return t.element.getName();
                }
            }
        }
        return null;
    }

    /**
     * Internal method to public path method.
     * @param x the node to get reached.
     * @param t the root of the subtree.
     * @return array of nodes that are in the path.
     */
    private Node[] path(Node x , AvlNode<Node> t){
        int index = 0;
        int size = height(t) + 1;
        Node[] superiors = new Node[size];
        while( t != null ) {
            int compareResult = x.compareTo(t.element);
            superiors[index++] = t.element;
            if (compareResult < 0) {t = t.left;}
            else if (compareResult > 0) {t = t.right;}
            else {break;}
        }
        Node[] trueSuperiors = new Node[index];
        int newSize = index;
        for (int i=0; i<newSize; i++){
            trueSuperiors[i] = superiors[--index];
        }
        return trueSuperiors;
    }

    /**
     * Search the common parent node for given two nodes.
     * @param x the first node.
     * @param y the second node.
     * @return the node that is superior of other two.
     */
    public Node target(Node x, Node y){
        Node[] pathOfX = path(x);
        Node[] pathOfY = path(y);
        for(int i=0; i<pathOfX.length; i++){
            for(int j=0; j<pathOfY.length; j++){
                if(pathOfX[i].equals(pathOfY[j]))
                    return pathOfX[i];
            }
        }
        return null;
    }

    /**
     * Internal rank method.
     * @param x the node whose rank matters.
     * @param t the root of the subtree.
     * @return the rank of the given node.
     */
    private int rank(Node x, AvlNode<Node> t) {
        int rank= 0;
        while (t != null) {
            int compareResult = x.compareTo(t.element);

            if (compareResult < 0) {
                rank++;
                t = t.left;
            }
            else if (compareResult > 0) {
                rank++;
                t = t.right;
            }
            else {
                break;
            }
        }
        return rank;
    }

    /**
     * Writes related information of nodes that have the same rank to the file.
     * @param x the reference node for the number of rank.
     * @param t the root of the subtree.
     * @param myWriter writes to the file.
     * @throws IOException
     */
    private void sameRank(Node x, AvlNode<Node> t, BufferedWriter myWriter) throws IOException {
        int rank = rank(x);
        if (t != null){
            sameRank(x, t.left, myWriter);
            sameRank(x, t.right, myWriter);
            int number = rank(t.element);
            if(rank == number){
                myWriter.write(" " + t.element.getName() + " " + String.format("%.3f", t.element.getGms()));
            }
        }
    }
    private static class Pair<A, B> {
        A first;
        B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }

    /**
     * Finds the maximum number of independent(one is not superior or inferior of others) nodes in an avl tree.
     * @return the maximum number.
     */
    public int findMaxIndependentNodes() {
        Pair <Integer, Integer> pairNodes = findMaxIndependentNodes(root);
        return Math.max(pairNodes.first, pairNodes.second);
    }


    /**
     * Internal method for finding maximum number of independent nodes in an avl tree.
     * @param t the root of the subtree.
     * @return pair that consists of two integers(Number of independents with and without root).
     */
    private Pair<Integer, Integer> findMaxIndependentNodes(AvlNode<Node> t) {
        if (t == null) {
            return new Pair<>(0, 0);
        }

        Pair<Integer, Integer> left = findMaxIndependentNodes(t.left);
        Pair<Integer, Integer> right = findMaxIndependentNodes(t.right);

        int withoutCurrent = Math.max(left.first, left.second) + Math.max(right.first, right.second);
        int withCurrent = 1 + left.second + right.second;

        return new Pair<>(withCurrent, withoutCurrent);
    }
}
