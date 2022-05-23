package UI;

import Database.Grocery;
import Database.Receipt;
import Database.Store;
import Filters.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import Analysis.analysis;

public class CommandLine {

    private String[] lines;

    public CommandLine(){
        Scanner kb = new Scanner(System.in);
        String str = kb.nextLine();
        this.lines = str.split(" *, *");
    }

    public FilterSet parseFilter(){
        Filter<Store> storeFilter = Filter.AlwaysPass;
        Filter<Grocery> groceryFilter = Filter.AlwaysPass;
        Filter<Receipt> receiptFilter = Filter.AlwaysPass;

        for(int i = 1; i < this.lines.length; i++){

            String[] str = this.lines[i].split(" +");

            if(str[0].equalsIgnoreCase("store"))
                storeFilter = store(str);
            else if(str[0].equalsIgnoreCase("grocery"))
                groceryFilter = grocery(str);
            else if(str[0].equalsIgnoreCase("receipt"))
                receiptFilter = receipt(str);
            else
                throw new CommandSyntaxException("Unrecognized section " + str[0]);
        }
        return new FilterSet(storeFilter, groceryFilter, receiptFilter);
    }

    private Filter<Store> store(String[] str){
        Filter<Store> f = Filter.AlwaysPass;
        for(int i = 1; i < str.length; i += 2){
            if(str[i].equalsIgnoreCase("name"))
                f = new StoreNameFilter(f, str[i+1]);
            else
                throw new CommandSyntaxException("Unrecognized filter " + str[i]);
        }
        return f;
    }

    private Filter<Grocery> grocery(String[] str){
        Filter<Grocery> f = Filter.AlwaysPass;
        for(int i = 1; i < str.length; i += 2){
            if(str[i].equalsIgnoreCase("name"))
                f = new GroceryNameFilter(f, str[i+1]);
            else{
                throw new CommandSyntaxException("Unrecognized filter " + str[i]);
            }
        }
        return f;
    }

    private Filter<Receipt> receipt(String[] str) {
        Filter<Receipt> f = Filter.AlwaysPass;
        for(int i = 1; i < str.length; i += 3){
            if(str[i].equalsIgnoreCase("between")){
                try {
                    LocalDate begin = LocalDate.parse(str[i + 1], DateTimeFormatter.ISO_LOCAL_DATE);
                    LocalDate end = LocalDate.parse(str[i + 2], DateTimeFormatter.ISO_LOCAL_DATE);

                    f = new ReceiptDateFilter(f, begin, end);
                } catch(DateTimeParseException e) {
                    throw new CommandSyntaxException(e.getMessage());
                }
            } else {
                throw new CommandSyntaxException("Unrecognized filter " + str[i]);
            }
        }
        return f;
    }

    public void execute(FilterSet filters) {
        switch(this.lines[0]) {
            case "total-nutrition":
                analysis.totalNutrition(filters);
                break;
            case "avg-nutrition":
            case "average-nutrition":
                analysis.AvgNutrition(filters);
                break;
            case "total-price":
                analysis.totalPrice(filters);
                break;
            case "avg-price":
            case "average-price":
                analysis.avgPrice(filters);
                break;
            default:
                throw new CommandSyntaxException("Unrecognized analysis type " + this.lines[0]);
        }
    }
}
