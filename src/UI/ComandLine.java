package UI;

import Filters.Filter;
import Filters.GroceryNameFilter;
import Filters.ReceiptDateFilter;
import Filters.StoreNameFilter;

import java.time.LocalDate;
import java.util.Scanner;

public class ComandLine {

    private String[] CL;

    public ComandLine(){
        Scanner kb = new Scanner(System.in);
        String str = kb.nextLine();
        this.CL = str.split(",");
    }

    public Filter chooseFilter(){
        Filter filter;
        Filter f1;
        Filter f2;
        Filter f3;
        Filter f4;

        for(int i = 1; i < this.CL.length; i++){

            String[] str = this.CL[i].split(" ");

            if(str[i].equalsIgnoreCase("store"))
                f1 = new Filter(store(str));
            if(str[i].equalsIgnoreCase("grocery"))
                f2 = new Filter(grocery(str));
            if (str[i].equalsIgnoreCase("receipt"))
                f3 = new Filter(receipt(str));
            if(str[i].equalsIgnoreCase(" "))
                f4 = new Filter(all());
        }
        return filter = new Filter();//TODO: figure out how to decorate the filter outputted
    }

    private Filter store(String[] strings){
        for(int i = 1; i < strings.length; i += 2){
            if(strings[i].equalsIgnoreCase("name"))
                return new StoreNameFilter(strings[i+1]);
        }
        return null;
    }

    private Filter grocery(String[] str){
        for(int i = 1; i < str.length; i += 2){
            if(str[i].equalsIgnoreCase("name"))
                return new GroceryNameFilter(str[i+1]);
        }
        return null;
    }

    private Filter receipt(String[] str) {
        for(int i = 1; i < str.length; i += 3){
            if(str[i].equalsIgnoreCase("between"))
                return null;
                //return new ReceiptDateFilter(str[i+1], str[i+2]);
                //TODO: figure out how to cast strings as a date
        }
        return null;
    }

    private Filter all() {
        return null;//TODO: figure out how to call the generic passAll filter
    }
}
