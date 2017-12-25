package DataStructures;

/**
 * Created by Cipson on 2017-04-10.
 */
public class Queue<T> {

    Node last, first;

    public class Node{
        T value;
        Node next;//points back in the queue

        public Node(T v){
            value = v;
        }
    }

    public boolean isEmpty(){
        return first == null;
    }

    //Add element to the end of the queue
    public void enqueue(T v){
        if(first == null){
            first =  new Node(v);
            last = first;
        }
        else {
            //add new element to the end of the queue
            last.next = new Node(v);
            //move pointer to point to the new element
            last = last.next;
        }
    }

    //Remove and return first element in the queue
    public T dequeue(){
        Node temp = first;
        first = first.next;
        return temp.value;
    }

}
