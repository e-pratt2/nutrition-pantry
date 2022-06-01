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

/**
 * Commandline, parsing and executing the users input during an analysis session.
 */
public class CommandLine {

    private String[] lines;

    //Todo: allow quoted strings?
    //private Pattern tokenSplit = Pattern.compile("[^\\s\"']*|\"([^\"]*)\"|'([^']*)'");

    /**
     * Construct the commandline - prints out helpful tips before starting.
     */
    public CommandLine() {
        System.out.println("Type " + ConsoleStyle.bold("help").blue()
                + " for information, or type " + ConsoleStyle.bold("exit").blue()
                + " to return to menu.");
    }

    /**
     * Prompt, get, and split the user's input from System.in.
     */
    public void fetchInput() {
        System.out.print(ConsoleStyle.bold(">> "));
        Scanner kb = UIHelpers.getScanner();
        String str = kb.nextLine();
        this.lines = str.split(" *, *");
    }

    /**
     * Parse the filter syntax. Specified as a comma separated set of space-separated filter lists of all three kinds.
     * @return a set of filters, each parsed from the user input. Missing filters will be replaced with AlwaysPass filters.
     * @throws CommandSyntaxException on unrecognized user input
     */
    public FilterSet parseFilter(){
        Filter<Store> storeFilter = Filter.AlwaysPass;
        Filter<Grocery> groceryFilter = Filter.AlwaysPass;
        Filter<Receipt> receiptFilter = Filter.AlwaysPass;

        for(int i = 1; i < this.lines.length; i++){

            String[] str = this.lines[i].split(" +");

            if(str[0].equalsIgnoreCase("store"))
                storeFilter = parseStoreFilter(str);
            else if(str[0].equalsIgnoreCase("grocery"))
                groceryFilter = parseGroceryFilter(str);
            else if(str[0].equalsIgnoreCase("receipt"))
                receiptFilter = parseReceiptFilter(str);
            else
                throw new CommandSyntaxException("Unrecognized section " + str[0]);
        }
        return new FilterSet(storeFilter, groceryFilter, receiptFilter);
    }

    /**
     * Parse the given split string to a store filter chain, decorating previous filters with new ones.
     * @param str tokenized input representing a set of filters.
     * @return a filter chain matching the user's input.
     * @throws CommandSyntaxException on unrecognized user input
     */
    private Filter<Store> parseStoreFilter(String[] str){
        Filter<Store> f = Filter.AlwaysPass;
        for(int i = 1; i < str.length; i += 2){
            if(str[i].equalsIgnoreCase("name"))
                f = new StoreNameFilter(f, str[i+1]);
            else
                throw new CommandSyntaxException("Unrecognized filter " + str[i]);
        }
        return f;
    }

    /**
     * Parse the given split string to a grocery filter chain, decorating previous filters with new ones.
     * @param str tokenized input representing a set of filters.
     * @return a filter chain matching the user's input.
     * @throws CommandSyntaxException on unrecognized user input
     */
    private Filter<Grocery> parseGroceryFilter(String[] str){
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

    /**
     * Parse the given split string to a receipt filter chain, decorating previous filters with new ones.
     * @param str tokenized input representing a set of filters.
     * @return a filter chain matching the user's input.
     * @throws CommandSyntaxException on unrecognized user input
     */
    private Filter<Receipt> parseReceiptFilter(String[] str) {
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

    /**
     * Execute the given analysis type, based on the given filters.
     * @param filters the filters to use during analysis
     * @return true if the commandline should continue running, false if exit was requested.
     * @throws CommandSyntaxException on unrecognized command.
     */
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
                System.out.println("" +
                        ConsoleStyle.bold("analysis syntax: ") + "analysis-type [, grocery [filters...]] [, store [filters...]] [, receipt [filters...]]\n" +
                        ConsoleStyle.bold("analysis types:  ") + "total-nutrition, average-nutrition, total-price, average-price\n" +
                        ConsoleStyle.bold("grocery filters: ") + "name <grocery name>\n" +
                        ConsoleStyle.bold("store filters:   ") + "name <store name>\n" +
                        ConsoleStyle.bold("receipt filters: ") + "between <start date> <end date>");
                return true;
            case "exit":
                return false;
            default:
                throw new CommandSyntaxException("Unrecognized analysis type " + this.lines[0]);
        }
    }

    /**
     * Helper method, representing one iteration of a fetch-decode-execute loop
     * @return true if the loop should continue, false if exit was requested.
     * @throws CommandSyntaxException if any stage of the execute cycle encountered bad syntax.
     */
    public boolean fetchAndExecute() {
        this.fetchInput();
        return this.execute(this.parseFilter());
    }
}
