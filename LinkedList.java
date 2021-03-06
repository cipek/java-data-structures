package DataStructures;

import java.util.Hashtable;

/**
 * Created by Cipson on 2017-04-11.
 */
public class LinkedList<T> {

    Node first;

    public class Node{
        T value;
        Node next;

        public Node(T v){
            value = v;
        }

    }

    public LinkedList(){
        first = null;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void insert(T v){
        Node temp = first;
        first = new Node(v);
        first.next = temp;
    }

    //Return value if exist. Null if there isn't given value
    public T delete(){
        if(!isEmpty()) {
            Node temp = first;
            first = first.next;
            return temp.value;
        }
        return null;
    }

    //Return value if exist. Null if there isn't given value
    public T search(T key){
        Node x = first;
        while(x!= null & x.value != key)
            x = x.next;
        if(x == null)
            return null;
        else
            return x.value;
    }

    //Reverse whole list
    public void reverseList(){
        Node previous = null;
        Node current = first;
        Node next = null;
        while(current != null){
                next = current.next;
                current.next = previous;
                previous = current;
                current = next;
        }
        first = previous;
    }

    //Reverse first half of the list
    public void reverseHalfList(){
        Node previous = null;
        Node current = first;
        Node next;
        Node fast = first;
        Node start = first;
        while(fast != null){
            if(fast.next != null)
                fast = fast.next.next;
            else
                fast = null;

            next = current.next;
            current.next = previous;
            previous = current;
            current = next;

        }
        start.next = current;
        first = previous;
    }

    //Print middle element of the list
    public void printMiddle() {
        Node slow_ptr = first;
        Node fast_ptr = first;
        if (first != null)
        {
            while (fast_ptr != null && fast_ptr.next != null)
            {
                fast_ptr = fast_ptr.next.next;
                slow_ptr = slow_ptr.next;
            }
            System.out.println("The middle element is [" +
                    slow_ptr.value + "] \n");
        }
    }

    public void printList(){
        Node temp = first;
        while(temp!=null){
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    //Remove duplicates from the list
    public void removeDuplicates(){
        //value, position
        Hashtable table = new Hashtable();
        Node previous = null;
        Node current = first;
        while(current!=null){
            if(table.containsKey(current.value)){
                previous.next = current.next;
            }
            else{
                table.put(current.value, true);
                previous = current;
            }
            current = current.next;
        }

    }

    //Removes give number of elements from the end of the list
    //Recursion approach
    public void returnKElementFromTheEnd(int k){
        Node n = first;
        kElementRec(first, k);
    }

    private int kElementRec(Node n, int k){
        if(n==null)
            return 0;
        int currentVal = kElementRec(n.next, k) + 1;

        if(currentVal == k)
            System.out.println(n.value);

        return currentVal;

    }

    //Moves elements with smaller value than x to the front
    //and bigger elements to the end of the list
    public void partitionListAroundXValue(int x){

        Node n = first;
        Node smallerFirst = null;
        Node smallerCurrent = null;
        Node biggerFirst = null;
        Node biggerCurrent = null;
        while(n != null){
            Node next = n.next;
            n.next=null;
            if(n.value.toString().compareTo("" +x) < 0){
                if(smallerCurrent == null){
                    smallerCurrent = n;
                    smallerFirst = smallerCurrent;
                }else{
                    smallerCurrent.next = n;
                    smallerCurrent = n;
                }
            }
            else{
                if(biggerCurrent == null){
                    biggerCurrent = n;
                    biggerFirst = biggerCurrent;
                }else{
                    biggerCurrent.next = n;
                    biggerCurrent = n;
                }
            }
            n = next;
        }

        if(smallerFirst == null)
            first = biggerFirst;

        smallerCurrent.next = biggerFirst;
        first = smallerFirst;
    }

    //Checks if the the elements in the list form the palindrome
    public boolean isPalindrome(){

        //Stack is used because of its LIFO design
        Stack<T> half = new Stack<>();
        Node slow = first;
        Node fast = first;
        //Finds the middle of the list and store first half in the stack
        while(fast != null && fast.next != null){
            half.push(slow.value);
            slow = slow.next;
            fast = fast.next.next;
        }

        //In case list has odd number of elements
        if(fast!= null)
            slow = slow.next;

        //Compares first nd second half of the list
        while(slow!= null){
            T temp = half.pop();
            if(!slow.value.toString().equals(temp.toString()))
                return false;
            slow = slow.next;
        }

        return true;
    }

}
