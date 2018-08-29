package io.brainyapps.barista.data.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ItemsIntConverter{

    @TypeConverter
    public String fromItemsInt(List<Integer> items) {
       // return items.stream().map(s->String.valueOf(s)).collect(Collectors.joining(","));
        if (items == null) {
            return "";
        } else {
            String listString = items.toString();
            return listString.substring(1, listString.length() - 1);
        }
    }

    @TypeConverter
    public List<Integer> toItemsInt(String data) {
       // return Arrays.asList(data.split(","));
        /*return Arrays.stream(data.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());*/

        ArrayList<Integer> myList = new ArrayList<Integer>();

        if (data.length() > 0) {
            for (String field : data.split(", "))
                myList.add(Integer.parseInt(field));

        }
        return myList;
    }
}
//for(String s : strList) intList.add(Integer.valueOf(s));
//for(Integer i : intList) stringList.add(String.valueOf(i));

//List<Integer> myList = new List<Integer>();
//for(int index = 0 ; index<5 ; index++)
//        myList.add(Integer.parseInt(s[index]);

//ArrayList<Integer> lst = new ArrayList<Integer>();
//for (String field : number.split(" +"))
//        lst.add(Integer.parseInt(field));

    //Scanner scanner = new Scanner(number);
    //List<Integer> list = new ArrayList<Integer>();
    //    while (scanner.hasNextInt()) {
    //      list.add(scanner.nextInt());
    //    }