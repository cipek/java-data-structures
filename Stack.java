package DataStructures;

/**
 * Created by Cipson on 2017-04-10.
 */
public class Stack<T> {

    Node first;

    public class Node{
        T value;
        Node next;//points 'down' in the stack
    }

    public void push(T v){
        Node current = first;
        first = new Node();
        first.value = v;
        first.next = current;
    }

    public T pop(){
        T temp = first.value;
        first = first.next;
        return temp;
    }

}
