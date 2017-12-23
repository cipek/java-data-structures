package DataStructures;

import java.lang.reflect.Array;

/**
 * Created by Cipson on 2017-10-26.
 */
public class PriorityQueue{

    private int heapSize = 0;
    private Node [] array;

    public class Node{
        int key;
        String value;

        Node(int k, String v){
            key = k;
            value = v;
        }

        public void setKey(int k){
            key = k;
        }
    }

    public PriorityQueue(){
        array = new Node [0];
    }

    public String maximum(){
        return array[0].value;
    }

    public int extractMax() throws Throwable {
        if(heapSize < 0)
            throw new Throwable("heap underflow"); //error
        int max = array[0].key;
        array[0] = array[heapSize-1];
        heapSize--;
        moveDown(array, 0);
        return max;
    }

    private void increaseKey(int i, int key) throws Throwable {
        if(key < array[i].key)
            throw new Throwable("new key is smaller than current key”"); //error
        array[i].key = key;
        while(i>0 && array[parent(i)].key < array[i].key){
            Node temp = array[i];
            array[i] = array[parent(i)];
            array[parent(i)] = temp;
            i = parent(i);
        }
    }

    public void insert(int key, String value) throws Throwable {
        heapSize++;
        if(array.length < heapSize){
            Node [] tempArray = new Node[heapSize];
            System.arraycopy(array, 0, tempArray, 0, array.length);
            array = tempArray;
        }
        array[heapSize-1] = new Node(Integer.MIN_VALUE, value);
        increaseKey(heapSize-1, key);
    }

    private int parent(int i){
        if(i>0)
            return ((i-1)/2);
        else
            return 0;
    }

    private int left(int i){return (2*i + 1);}

    private int right(int i){return (2*i + 2);}

    //Move higher value down in the heap
    private void moveDown(Node[] array, int i){
        int l = left(i);
        int r = right(i);
        int largest;
        //Find if any of children is bigger than parent
        if(l < heapSize && array[l].key > array[i].key)
            largest = l;
        else
            largest = i;
        if(r < heapSize && array[r].key > array[largest].key)
            largest = r;
        //if it is then move it in the heap and check further- if next move is needed
        if(largest != i){
            Node temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;
            moveDown(array, largest);
        }
    }

}
