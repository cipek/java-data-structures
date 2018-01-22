package DataStructures;

/**
 * Created by Cipson on 2018-01-19.
 */
public class StackMin {

    Node first;
    Node min; //Track min current value in the stack

    public class Node{
        int value;
        Node next;//points 'down' in the stack

        public Node(int value){
            this.value = value;
        }

    }

    public void push(int v){
        Node current = first;
        first = new Node(v);
        first.next = current;
        if(min == null)
            min = new Node(v);
        else if(min.value >= v){
            Node minCur = min;
            min = new Node(v);
            min.next = minCur;
        }
    }

    public int pop(){
        int temp = first.value;
        first = first.next;

        if(temp == min.value)
            min = min.next;

        return temp;
    }

    public int getMin(){
        return min.value;
    }

}