package GamesTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

// class to maintain all lists
public class Lists {

    // keep track of all lists user has
    public static TreeMap<String, ArrayList<Integer>> lists = new TreeMap<>();

    public static void addToList(String list, Integer gameId){
        lists.get(list).add(gameId);
    }

    // used for combo box and making buttons
    public static String[] getCurrentLists(){
        String [] listsArr = new String[lists.size()];
        int counter = 0;
        for (String list: lists.keySet()){
            listsArr[counter] = list;
            counter+=1;
        }
        return listsArr;
    }
}
