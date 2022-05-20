package UI;

import Database.Grocery;
import Database.Receipt;
import Database.Store;
import Filters.Filter;
import Filters.GroceryNameFilter;
import Filters.ReceiptDateFilter;
import Filters.StoreNameFilter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ComandLine {

    private String[] CL;

    public ComandLine(){
        Scanner kb = new Scanner(System.in);
        String str = kb.nextLine();
        this.CL = str.split(" *, *");
    }

    public Filter chooseFilter(){
        Filter filter;

        for(int i = 1; i < this.CL.length; i++){

            String[] str = this.CL[i].split(" +");

            if(str[0].equalsIgnoreCase("store"))
                filter = store(str);
            if(str[0].equalsIgnoreCase("grocery"))
                filter = grocery(str);
            if (str[0].equalsIgnoreCase("receipt"))
                filter = receipt(str);
            if(str[0].equalsIgnoreCase(" "))
                filter = all();
        }
        return filter = new Filter();//TODO: figure out how to decorate the filter outputted
    }

    private Filter<Store> store(String[] strings){
        Filter<Store> f = Filter.AlwaysPass;
        for(int i = 1; i < strings.length; i += 2){
            if(strings[i].equalsIgnoreCase("name"))
                f = new StoreNameFilter(f, strings[i+1]);
        }
        return f;
    }

    private Filter<Grocery> grocery(String[] str){
        Filter<Grocery> f = Filter.AlwaysPass;
        for(int i = 1; i < str.length; i += 2){
            if(str[i].equalsIgnoreCase("name"))
                f = new GroceryNameFilter(f, str[i+1]);
        }
        return f;
    }

    private Filter<Receipt> receipt(String[] str) {
        Filter<Receipt> f = Filter.AlwaysPass;
        for(int i = 1; i < str.length; i += 3){
            if(str[i].equalsIgnoreCase("between")){
                LocalDate begin = LocalDate.parse(str[i+1], DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate end = LocalDate.parse(str[i+2], DateTimeFormatter.ISO_LOCAL_DATE);

                f = new ReceiptDateFilter(f, begin, end);
            }
        }
        return f;
    }

    private Filter all() {
        return null;//TODO: figure out how to call the generic passAll filter
    }
}
