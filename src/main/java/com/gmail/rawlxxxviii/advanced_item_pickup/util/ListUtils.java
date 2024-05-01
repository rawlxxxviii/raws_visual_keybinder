package com.gmail.rawlxxxviii.advanced_item_pickup.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ListUtils {

    public static <T> List<List<T>> splitList(List<T> inputList, int count){

        List<List<T>> result = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            var item = inputList.get(i);

            if(i % count == 0){ // new list
                var newList = new ArrayList<T>();
                newList.add(item);
                result.add(newList);
            }else{
                result.get(result.size()-1).add(item);
            }
        }
        return  result;

    }
    public static <T> List<List<T>> splitList2(List<T> inputList, int count){

        List<List<T>> result = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i+=count) {
            result.add(
              inputList.subList(i,i+count)
            );
        }
        return result;

    }
}
