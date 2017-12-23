package DataStructures;

/**
 * Created by Cipson on 2017-04-11.
 */
public class DoubleLinkedList<T> {

    public class Node{
        T value;
        Node next;
        Node previous;

        public Node(T v) {
            value = v;
        }

    }

    Node head;

    public DoubleLinkedList(){
        head = null;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void insert(T v){
            Node temp = head;
            head = new Node(v);
            head.next = temp;
            if(head.next != null)
                head.next.previous = head;
            head.previous = null;
    }

    public T remove(){
        if(!isEmpty()) {
            Node temp = head;
            head = head.next;
            if (head != null)
                head.previous = null;
            return temp.value;
        }
        else
            return null;
    }


}
