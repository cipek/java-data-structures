package DataStructures;


import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Cipson on 2017-10-27.
 */
/*
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
        int value, size;
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

    protected void leftRotate(Node x){
        Node y = x.right;
        x.right = y.left;
        if(y.left != nil)
            y.left.parent = x;
        y.parent = x.parent;
        if(x.parent == nil)
            root = y;
        else if(x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

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

    public void insert(int value){
        Node y = nil;
        Node x = root;
        Node z = new Node(value);
        while(x != nil){
            y= x;
            if(z.value < x.value)
                x = x.left;
            else
                x= x.right;
        }
        z.parent = y;
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

    protected void insertFixup(Node z){
        while(z.parent!= nil && !z.parent.black){ //Is red
            if(z.parent == z.parent.parent.left){
                Node y = z.parent.parent.right;
                if(y!= nil && !y.black){ //case 1
                    z.parent.black = true;
                    y.black = true;
                    z.parent.parent.black = false;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.right) { //case 2
                        z = z.parent;
                        leftRotate(z);

                    }
                    z.parent.black = true; //case 3
                    z.parent.parent.black = false;
                    rightRotate(z.parent.parent);
                }
            }
            else if(z.parent == z.parent.parent.right){//copy of first if with left and right exchange
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
        boolean originalColor = y.black;
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

    protected void deleteFixup(Node x){
        while(x != root && x.black){
            if(x == x.parent.left){
                Node w = x.parent.right;
                if(!w.black){
                    w.black = true;
                    x.parent.black = false;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if(w.left.black && w.right.black){
                    w.black = false;
                    x = x.parent;
                }
                else{
                    if(w.right.black){
                        w.left.black = true;
                        w.black = false;
                        rightRotate(w);
                        w = x.parent.right;
                    }
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

    protected Node minNode(Node current){
        Node minV = current;
        while (current.left != nil){
            minV = current.left;
            current = current.left;
        }
        return minV;
    }


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
