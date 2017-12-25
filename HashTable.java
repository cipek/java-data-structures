package DataStructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cipson on 2017-10-27.
 * Simple hash table with constant basic rank
 */
public class HashTable{

    private int tableSize = 10; //Table rank. Edit as needed
    private List<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>(tableSize); //List of lists

    public HashTable(){
        //Initialize all lists
        for (int i=0; i<tableSize;i++) {
            table.add(i, new ArrayList<Integer>());
        }
    }

    //The simplest hash function using table rank and modulo operator
    private int hashFunction(int key){
        return key % tableSize;
    }

    public void insert(int key){
        table.get(hashFunction(key)).add(key);
    }

    public int search(int key){
        return table.get(hashFunction(key)).get(table.get(hashFunction(key)).indexOf(key));
    }

    public void delete(int key){
        table.get(hashFunction(key)).remove(Integer.valueOf(hashFunction(key)));
    }
    
    public void printTable(){
        for(int i=0;i<tableSize;i++){
            for (Integer x: table.get(i)) {
                System.out.print(x+ " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

}
