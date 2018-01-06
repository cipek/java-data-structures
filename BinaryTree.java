package DataStructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Cipson on 2017-04-17.
 * Simple unbalanced binary search tree
 */

public class BinaryTree {

    Node root;

    public class Node{
        int value;
        Node left, right;

        public Node(int v){
            value = v;
        }

        public int getValue(){
            return value;
        }

        public Node getLeft(){
            return left;
        }

        public Node getRight(){
            return right;
        }
    }

    public BinaryTree(){
        root =null;
    }

    public boolean isEmpty(){
        return root==null;
    }

    public void insert(int v){
        if(isEmpty())
            root = new Node(v);
        else{
            Node current = root;
            Node parent;
            //Search position to insert new element
            while(current != null){
                parent = current;
                if(current.value <= v){
                    current = current.right;
                    if(current == null) {
                        parent.right = new Node(v);
                        return;
                    }
                }
                else{
                    current = current.left;
                    if(current == null) {
                        parent.left = new Node(v);
                        return;
                    }
                }
            }
        }
    }

    //Search for given value and return node with given value
    //If there isn't, returns null
    public Node search(int value){
        Node current = root;

        while(current != null){
            if(current.value == value)
                return current;
            else if(current.value > value)
                current = current.left;
            else
                current = current.right;
        }

        return null;
    }

    public void delete(int value){
        deleteRec(value, root);
    }

    private Node deleteRec(int value, Node current){
        //if Null means that it's end of the tree
        if(current == null)
            return current;

        //find the value otherwise
        if(current.value > value)
            current.left = deleteRec(value, current.left);
        else if(current.value < value)
            current.right = deleteRec(value, current.right);

        else{
            //If one of children is null then return the other
            if(current.left == null)
                return current.right;
            else if(current.right == null)
                return current.left;

            //None of the child is null so need to find the smallest value in the right sub tree
            //and set it as new value
            current.value = minValue(current.right);
            //Finally delete the node which we moved
            current.right = deleteRec( current.value, current.right);
        }

        return current;
    }

    //Find min value from given node
    public int minValue(Node current){
        int minV = current.value;
        while (current.left != null){
            minV = current.left.value;
            current = current.left;
        }
        return minV;
    }

    //Find max value from given node
    public int maxValue(Node current){
        int maxV = current.value;
        while (current.right != null){
            maxV = current.right.value;
            current = current.right;
        }
        return maxV;
    }

    //Find min value in the whole tree
    public int minValue(){
        Node current = root;
        int minV = current.value;
        while (current.left != null){
            minV = current.left.value;
            current = current.left;
        }
        return minV;
    }

    //Find max value in the whole tree
    public int maxValue(){
        Node current = root;
        int maxV = current.value;
        while (current.right != null){
            maxV = current.right.value;
            current = current.right;
        }
        return maxV;
    }

    public void displayInorder(){
        if(root!=null){
            displayInorder(root.left);
            System.out.print(" " + root.value);
            displayInorder(root.right);
            System.out.print("\n");
        }
    }

    private void displayInorder(Node current){
        if(current!=null){
            displayInorder(current.left);
            System.out.print(" " + current.value);
            displayInorder(current.right);
        }
    }


    public void displayPreorder(){
        if(root!=null){
            System.out.print(" " + root.value);
            displayInorder(root.left);
            displayInorder(root.right);
            System.out.print("\n");
        }
    }

    private void displayPreorder(Node current){
        if(current!=null){
            System.out.print(" " + current.value);
            displayInorder(current.left);
            displayInorder(current.right);
        }
    }


    public void displayPostorder(){
        if(root!=null){
            displayInorder(root.left);
            displayInorder(root.right);
            System.out.print(" " + root.value);
            System.out.print("\n");
        }
    }

    private void displayPostorder(Node current){
        if(current!=null){
            displayInorder(current.left);
            displayInorder(current.right);
            System.out.print(" " + current.value);
        }
    }

    //Search for element in breath first pattern
    //Returns visited nodes
    public ArrayList<Node> breadthFirstSearch(int id){
        if(isEmpty())
            return null;

        Queue<Node> queue = new LinkedList<Node>();
        ArrayList<Node> visited = new ArrayList<>();

        Node temp = root;
        queue.add(temp);
        while(!queue.isEmpty()) {
            Node current = queue.remove();

            if(current.value == id)
                return visited;

            if(current.left != null)
                queue.add(current.left);

            if(current.right != null)
                queue.add(current.right);

            visited.add(current);
        }

        return visited;

    }

    //Search for element in depth first pattern
    //Returns visited nodes
    public static ArrayList<Node> depthFirstSearch(Node root, int id){

        if(root==null)
            return null;

        Stack<Node> stack = new Stack<>();
        ArrayList<Node> visited = new ArrayList<>();


        Node temp = root;
        stack.add(temp);

        while(!stack.empty()){
            Node current = stack.pop();

            if(current.value == id)
                return visited;

            if(current.right != null)
                stack.add(current.right);

            if(current.left != null)
                stack.add(current.left);

            visited.add(current);
        }

        return visited;

    }

    public void printTree() {

        Queue<Node> currentLevel = new LinkedList<Node>();
        Queue<Node> nextLevel = new LinkedList<Node>();

        currentLevel.add(root);

        while (!currentLevel.isEmpty()) {
            for (Node currentNode : currentLevel) {
                if (currentNode.left != null) {
                    nextLevel.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    nextLevel.add(currentNode.right);
                }
                System.out.print(currentNode.value + " ");
            }
            System.out.println();
            currentLevel = nextLevel;
            nextLevel = new LinkedList<Node>();
        }
        System.out.print("\n");

    }

    //Prints all paths in the tree- from root to leaves
    public void printAllPaths(){
        printAllPathsRec(root, "");
    }

    private void printAllPathsRec(Node node, String s){
        if(node == null)
            return;

        if(node.left == null && node.right == null){
            System.out.println(s + " " + node.value);
            return;
        }

        printAllPathsRec(node.left, s + " " + node.value);
        printAllPathsRec(node.right, s + " " + node.value);

    }

    //Calculates sum of all elements in the tree
    public int findSum(){
        return findSumRec(root);
    }

    private int findSumRec(Node node){
        if(node == null)
            return 0;
        else
            return node.value + findSumRec(node.left) + findSumRec(node.right);
    }

}
