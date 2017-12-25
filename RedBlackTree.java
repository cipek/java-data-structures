package DataStructures;


import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Cipson on 2017-10-27.
1. Every node is either red or black.
2. The root is black.
3. Every leaf (NIL) is black.
4. If a node is red, then both its children are black.
5. For each node, all simple paths
 */
public class RedBlackTree {

    protected Node nil = new Node();

    protected Node root = nil; //used as the pointer to nth

    public class Node{
        int value, rank; //Size is not used in RedBlackTree. Only by RBTreeRank and indicates how many nodes are below given node
        Node left, right, parent;
        boolean black;

        public Node(){
            value = -1;
            black = true;
        }

        public Node(int v){
            value = v;
        }

        public int getValue(){
            return value;
        }
    }

    //Right node of the 'x' node becomes parent and 'x' is its left child
    //
    //   x          y
    //    \   ->   /
    //     y      x
    protected void leftRotate(Node x){
        Node y = x.right;
        x.right = y.left; //turn y's left subtree into x's right subtree
        if(y.left != nil)
            y.left.parent = x;
        y.parent = x.parent; //link x's parent to y
        if(x.parent == nil)
            root = y;
        else if(x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x; //put x on y's left
        x.parent = y;
    }

    //Left node of the 'x' node becomes parent and 'x' is its right child
    //
    //    x      y
    //   /   ->   \
    //  y          x
    protected void rightRotate(Node x){
        Node y = x.left;
        x.left = y.right;
        if(y.right != nil)
            y.right.parent = x;
        y.parent = x.parent;
        if(x.parent == nil)
            root = y;
        else if(x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.right = x;
        x.parent = y;
    }

    //Insert new element
    public void insert(int value){
        Node y = nil; //parent of the new node
        Node x = root;
        Node z = new Node(value);
        //Insert new element to empty spot- unbalance at this point
        while(x != nil){
            y= x;
            if(z.value < x.value)
                x = x.left;
            else
                x= x.right;
        }
        z.parent = y;
        //Link parent to new node
        if(y == nil)
            root = z;
        else if(z.value < y.value)
            y.left = z;
        else
            y.right = z;
        z.left = nil;
        z.right = nil;
        z.black = false;
        insertFixup(z);
    }

    //Balance tree after insertion
    protected void insertFixup(Node z){
        while(z.parent!= nil && !z.parent.black){ //Is red
            if(z.parent == z.parent.parent.left){
                Node y = z.parent.parent.right;
                //case 1- node z and its parent are red ->
                //-> recolor parent to black and move z pointer up the tree
                if(y!= nil && !y.black){
                    z.parent.black = true;
                    y.black = true;
                    z.parent.parent.black = false;
                    z = z.parent.parent;
                }
                else {
                    //case 2 - if z is right child of its parent do left rotate on z's parent
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);

                    }
                    //case 3- z has to be left child. Recolor nodes and do right rotate
                    z.parent.black = true;
                    z.parent.parent.black = false;
                    rightRotate(z.parent.parent);
                }
            }
            else if(z.parent == z.parent.parent.right){ //same as 'if' clause, with 'left' and 'right' exchange
                Node y = z.parent.parent.left;
                if(y!= nil && !y.black){ //case 1
                    z.parent.black = true;
                    y.black = true;
                    z.parent.parent.black = false;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.left) { //case 2
                        z = z.parent;
                        rightRotate(z);

                    }
                    z.parent.black = true; //case 3
                    z.parent.parent.black = false;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.black = true;

    }


    public void delete(int v){
        Node z = search(root, v);
        Node y = z;
        Node x;
        boolean originalColor = y.black; //stores ordinal color of the node
        //If it has just one child, replace them
        if(z.left == nil ){
            x = z.right;
            transplant(z, z.right);
        }
        else if(z.right == nil){
            x = z.left;
            transplant(z, z.left);
        }
        else{
            y = minNode(z.right);
            originalColor = y.black;
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else{
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.black = z.black;
        }
        if(originalColor)
            deleteFixup(x);

    }

    //Find node with given value 'v'
    protected Node search(Node current, int v){
        while(current != nil) {
            if (current.value == v)
                return current;
            else if (current.value > v)
                current = current.left;
            else
                current = current.right;
        }
        return nil;
    }

    //Fix tree (balance) after deletion
    protected void deleteFixup(Node x){
        while(x != root && x.black){
            if(x == x.parent.left){
                Node w = x.parent.right;
                //Case 1- if right child of x's parent is red
                if(!w.black){
                    w.black = true;
                    x.parent.black = false;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                //Case 2- if both children are black, change w to red
                if(w.left.black && w.right.black){
                    w.black = false;
                    x = x.parent;
                }
                else{
                    //Case 3- if just right child is black
                    if(w.right.black){
                        w.left.black = true;
                        w.black = false;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    //Case 4
                    w.black = x.parent.black;
                    x.parent.black = true;
                    w.right.black = true;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            else{
                Node w = x.parent.left;
                if(!w.black){
                    w.black = true;
                    x.parent.black = false;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if(w.right.black && w.left.black){
                    w.black = false;
                    x = x.parent;
                }
                else{
                    if(w.left.black){
                        w.right.black = true;
                        w.black = false;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.black = x.parent.black;
                    x.parent.black = true;
                    w.left.black = true;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.black = true;

    }

    //Replace node u with node v (u is deleted from the tree)
    protected void transplant(Node u, Node v){
        if(u.parent == nil)
            root = v;
        else if(u == u.parent.left)
            u.parent.left = v;
        else u.parent.right = v;

        v.parent = u.parent;
    }

    //Find min element from given node
    protected Node minNode(Node current){
        Node minV = current;
        while (current.left != nil){
            minV = current.left;
            current = current.left;
        }
        return minV;
    }


    //Print tree level by level
    public void printTree() {

        Queue<Node> currentLevel = new LinkedList<Node>();
        Queue<Node> nextLevel = new LinkedList<Node>();

        currentLevel.add(root);

        while (!currentLevel.isEmpty()) {
            for (Node currentNode : currentLevel) {
                if (currentNode.left != nil) {
                    nextLevel.add(currentNode.left);
                }
                if (currentNode.right != nil) {
                    nextLevel.add(currentNode.right);
                }
                System.out.print(currentNode.value + " ");
                if(currentNode.black)
                    System.out.print("B" + " , ");
                else
                    System.out.print("R" + " , ");
            }
            System.out.println();
            currentLevel = nextLevel;
            nextLevel = new LinkedList<Node>();
        }
        System.out.print("\n");

    }

    public void displayInorder(){
        if(root!=null){
            displayInorder(root.left);
            System.out.print(" " + root.value);
            displayInorder(root.right);
            System.out.print("\n");
        }
    }

    protected void displayInorder(Node current){
        if(current!=null){
            displayInorder(current.left);
            System.out.print(" " + current.value);
            displayInorder(current.right);
        }
    }

}
