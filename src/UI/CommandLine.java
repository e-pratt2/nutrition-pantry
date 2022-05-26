package UI;

import Database.Grocery;
import Database.Receipt;
import Database.Store;
import Filters.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

import Analysis.analysis;

public class CommandLine {

    private String[] lines;

    //Todo: allow quoted strings?
    //private Pattern tokenSplit = Pattern.compile("[^\\s\"']*|\"([^\"]*)\"|'([^']*)'");

    public CommandLine() {
        System.out.println("Type in 'help' for information:");
    }

    public void fetchInput() {
        System.out.print("-> ");
        Scanner kb = UIHelpers.getScanner();
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

    public boolean execute(FilterSet filters) {
        switch(this.lines[0]) {
            case "total-nutrition":
                analysis.totalNutrition(filters);
                return true;
            case "avg-nutrition":
            case "average-nutrition":
                analysis.AvgNutrition(filters);
                return true;
            case "total-price":
                analysis.totalPrice(filters);
                return true;
            case "avg-price":
            case "average-price":
                analysis.avgPrice(filters);
                return true;
            case "help":
                System.out.println("Analysis syntax: analysis-type [, grocery [filters...]] [, store [filters...]] [, receipt [filters...]]\n" +
                        "analysis types:  total-nutrition, average-nutrition, total-price, average-price\n" +
                        "grocery filters: name <grocery name>\n" +
                        "store filters:   name <store name>\n" +
                        "receipt filters: between <start date> <end date>");
                return true;
            case "exit":
                return false;
            default:
                System.out.println("Unrecognized analysis type " + this.lines[0]);
                return true;
        }
    }

    public boolean fetchAndExecute() {
        this.fetchInput();
        return this.execute(this.parseFilter());
    }
}
