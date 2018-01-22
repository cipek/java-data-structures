package DataStructures;

/**
 * Created by Cipson on 2017-04-10.
 */
public class Stack<T> {

    Node first;
    Node min;

    public class Node{
        T value;
        Node next;//points 'down' in the stack
    }

    public void push(T v){
        Node current = first;
        first = new Node();
        first.value = v;
        first.next = current;
        if(min == null)
            min = new Node();

    }

    public T pop(){
        T temp = first.value;
        first = first.next;
        return temp;
    }

}
