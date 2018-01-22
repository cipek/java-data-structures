package DataStructures;

import java.util.*;
import java.util.Stack;

/**
 * Created by Cipson on 2018-01-19.
 * Queue built with usage of two stacks
 */
public class QueueFrom2Stacks<T> {

    java.util.Stack<T> newest = new Stack<T>();//stores elements to add to
    java.util.Stack<T> oldest = new Stack<T>();//remove elements from

    public void add(T value){
        newest.push(value);
    }

    //Move elements form newest stack to the oldest stack
    private void shift(){
        if(oldest.isEmpty())
            while (!newest.isEmpty())
                oldest.push(newest.pop());
    }

    public T remove(){
        shift();
        return oldest.pop();
    }

}
