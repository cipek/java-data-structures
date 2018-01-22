package DataStructures;

import java.util.ArrayList;
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

    public Node getRoot(){return root;}

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

    public boolean isBalanced(){
        if(checkHeight(root) != -1)
            return true;
        else
            return false;
    }

    //-1 means it is not balanced
    private int checkHeight(Node current){
        if(current == null)
            return 0;

        int right = checkHeight(current.right);
        if(right == -1)
            return -1;

        int left = checkHeight(current.left);
        if(left == -1)
            return -1;

        int diff = Math.abs(left - right);
        if(diff > 1)
            return -1;
        else
            return Math.max(left, right) + 1;

    }

    //Creates balanced tree from the ascending array
    public void creteTreeFromArray(int[] array){
        root = creteTreeFromArrayRec(array, 0, array.length - 1 );
    }

    private Node creteTreeFromArrayRec(int[] array, int start, int end){
        if(start > end)
            return null;

        int mid = (end + start)/2;
        Node temp = new Node(array[mid]);
        temp.left = creteTreeFromArrayRec(array, start, mid - 1);
        temp.right = creteTreeFromArrayRec(array, mid + 1, end);

        return temp;
    }

    public void printAllLevels(){
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        printAllLevels(root, lists, 0);
        for (ArrayList<Integer> list:
             lists) {
            for (Integer x:
                 list) {
                System.out.print(x + ",");
            }
            System.out.print("\n");
        }
    }

    private void printAllLevels(Node node, ArrayList<ArrayList<Integer>> lists, int level){
        if(node == null)
            return;

        ArrayList<Integer> list = null;
        if(lists.size() == level){
            list = new ArrayList<>();
            lists.add(list);
        }
        else{
            list = lists.get(level);
        }
        list.add(node.getValue());

        printAllLevels(node.left, lists, level+1);
        printAllLevels(node.right, lists, level+1);
    }

    //Checks if the tree is a binary search tree
    public boolean checkBST(){
        return checkBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean checkBST(Node n, int min, int max){
        if(n== null)
            return true;

        if(n.getValue() < min || n.getValue() > max)
            return false;

        if(!checkBST(n.getLeft(), min, n.getValue()) ||
                !checkBST(n.getRight(), n.getValue(), max))
            return false;

        return true;
    }

    public int getCommonAncestor(int x, int y){
        return getCommonAncestor(root, x, y);
    }

    private boolean checkSide(Node current, int search){
        if(current == null)
            return false;
        if(current.getValue() == search)
            return true;

        return checkSide(current.left, search) || checkSide(current.right, search);
    }

    private int getCommonAncestor(Node current, int x, int y){
        if(current == null)
            return Integer.MIN_VALUE;
        if(current.getValue() == x || current.getValue() == y)
            return current.getValue();

        boolean is_x_on_left = checkSide(current.left, x);
        boolean is_y_on_left = checkSide(current.left, y);

        if(is_x_on_left != is_y_on_left)
            return current.getValue();

        if(is_x_on_left)
            return getCommonAncestor(current.getLeft(), x, y);
        else
            return getCommonAncestor(current.getRight(), x ,y);

    }

    //Checks if t2 is the subtree of current root
    public boolean containsSubtree(BinaryTree t2){
        if(t2.getRoot() == null)
            return true;

        return firstMatch(root, t2.getRoot());
    }

    //Looks for values match
    private boolean firstMatch(Node t1, Node t2){
        if(t1 == null)
            return false;
        //If find match, check the rest of the tree
        if(t1.getValue() == t2.getValue())
            if(checkMatch(t1, t2))
                return true;

        return (firstMatch(t1.getLeft(), t2) || firstMatch(t1.getRight(), t2));
    }

    private boolean checkMatch(Node t1, Node t2){
        //If both empty nothing left in the subtree
        if(t1 == null && t2 == null)
            return true;

        //if one, not both are empty
        if(t1 == null || t2 == null)
            return false;

        //Doesn't match
        if(t1.getValue() != t2.getValue())
            return false;

        return checkMatch(t1.getLeft(), t2.getLeft()) && checkMatch(t1.getRight(), t2.getRight());
    }

}
