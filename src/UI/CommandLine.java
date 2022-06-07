package UI;

import Database.Grocery;
import Database.Nutrition;
import Database.Receipt;
import Database.Store;
import Filters.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import Analysis.Analysis;

/**
 * Commandline, parsing and executing the users input during an analysis session.
 */
public class CommandLine {

    private String[] lines;

    /**
     * A string to help the user know what's possible within the custom command line syntax.
     * Includes some helpful tips as well as the list of possible filters and analysis types.
     * Has ANSI-styling built in for emphasis.
     */
    private static final String HELP_MESSAGE =
        ConsoleStyle.bold("analysis syntax: ") + "analysis-type [, grocery [filters...]] [, store [filters...]] [, receipt [filters...]]\n" +
        ConsoleStyle.bold("analysis types:  \n") +
        "\t* totals:             total-nutrition,             total-servings,             total-quantity,             total-price\n" +
        "\t* average by price:   average-nutrition-per-price, average-servings-per-price, average-quantity-per-price\n" +
        "\t* average by time:    average-nutrition-per-day,   average-servings-per-day,   average-quantity-per-day,   average-price-per-day\n" +
        "\t* lists:              list-groceries,              list-stores\n" +
        "\t (" + ConsoleStyle.bold("Tip:").blue() + " Use abbreviations! \"nutrition/day\" works just like \"average-nutrition-per-day\")\n" +
        ConsoleStyle.bold("grocery filters: ") + "name <underscore_separated_name>\n" +
        ConsoleStyle.bold("store filters:   ") + "name <underscore_separated_name>\n" +
        ConsoleStyle.bold("receipt filters: ") + "between <start date> <end date>";

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
        //Print out a symbol to indicate command mode
        System.out.print(ConsoleStyle.bold(">> "));
        //Get the user input
        Scanner kb = UIHelpers.getScanner();
        String str = kb.nextLine();
        //Parse into lines, based on a comma prefixed and suffixed with zero or more spaces
        this.lines = str.split(" *, *");
    }

    /**
     * Parse the filter syntax. Specified as a comma separated set of space-separated filter lists of all three kinds.
     * @return a set of filters, each parsed from the user input. Missing filters will be replaced with AlwaysPass filters.
     * @throws CommandSyntaxException on unrecognized user input
     */
    public FilterSet parseFilter(){
        //Start with "empty" filters that always accept objects.
        Filter<Store> storeFilter = Filter.AlwaysPass;
        Filter<Grocery> groceryFilter = Filter.AlwaysPass;
        Filter<Receipt> receiptFilter = Filter.AlwaysPass;

        //Starting from the *second* line, parse filters
        for(int i = 1; i < this.lines.length; i++){
            //Parse into tokens, text separated by zero or more spaces
            String[] str = this.lines[i].split(" +");

            //first token is "store", parse a store section, etc.
            if(str[0].equalsIgnoreCase("store"))
                storeFilter = parseStoreFilter(str);
            else if(str[0].equalsIgnoreCase("grocery"))
                groceryFilter = parseGroceryFilter(str);
            else if(str[0].equalsIgnoreCase("receipt"))
                receiptFilter = parseReceiptFilter(str);
            else
                //Unknown filter section, throw a helpful error
                throw new CommandSyntaxException("Unrecognized section " + str[0]);
        }
        //Bind all three into a filterset and return
        return new FilterSet(storeFilter, groceryFilter, receiptFilter);
    }

    /**
     * Parse the given split string to a store filter chain, decorating previous filters with new ones.
     * @param str tokenized input representing a set of filters.
     * @return a filter chain matching the user's input.
     * @throws CommandSyntaxException on unrecognized user input
     */
    private Filter<Store> parseStoreFilter(String[] str){
        //Begin with an "empty" filter
        Filter<Store> f = Filter.AlwaysPass;
        //For each token starting from the second...
        for(int i = 1; i < str.length; i += 2){
            //Name filter - parse next token as the name to check against
            if(str[i].equalsIgnoreCase("name"))
                //the "_" character serves as a placeholder for the space character, correct this here
                f = new StoreNameFilter(f, str[i+1].replace('_', ' '));
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
        //Begin with an "empty" filter
        Filter<Grocery> f = Filter.AlwaysPass;
        //For each token starting from the second...
        for(int i = 1; i < str.length; i += 2){
            if(str[i].equalsIgnoreCase("name"))
                //the "_" character serves as a placeholder for the space character, correct this here
                f = new GroceryNameFilter(f, str[i+1].replace('_', ' '));
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
        //Begin with an "empty" filter
        Filter<Receipt> f = Filter.AlwaysPass;
        //For each token starting from the second...
        for(int i = 1; i < str.length; i += 3){
            if(str[i].equalsIgnoreCase("between")){
                try {
                    //Parse the next two tokens as dates. Will throw on bad format.
                    LocalDate begin = LocalDate.parse(str[i + 1], DateTimeFormatter.ISO_LOCAL_DATE);
                    LocalDate end = LocalDate.parse(str[i + 2], DateTimeFormatter.ISO_LOCAL_DATE);

                    //Compose the filter
                    f = new ReceiptDateFilter(f, begin, end);
                    //On bad format, throw a syntax exception.
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
     * Print out a price within the command line session.
     * @param d the price to show, unit being dollars
     */
    private void prettyPrintPrice(double d) {
        System.out.println("<< $" + d);
    }

    /**
     * Print out a nutrition quantity in the command line session.
     * @param n the nutrition object containing the data to print
     */
    private void prettyPrintNutrition(Nutrition n) {
        System.out.println("<< " + n);
    }

    /**
     * Print out a number of servings within the command line session.
     * @param servings the number to print, unit being servings
     */
    private void prettyPrintServings(double servings) {
        System.out.println("<< " + servings + " servings.");
    }

    /**
     * Print out a quantity of groceries in the command line session.
     * @param quantity the number to print, unit being groceries
     */
    private void prettyPrintQuantity(double quantity) {
        System.out.println("<< " + quantity + " groceries.");
    }

    /**
     * Print out a list of objects in the command line session
     * @param list the list of objects to print
     * @param stringify a function to map the objects to strings. E::toString would work.
     * @param <E> The type of object within the list
     */
    private <E> void prettyPrintList(List<E> list, Function<E, String> stringify) {
        for(E obj : list)
            System.out.println("<< * " + stringify.apply(obj));
    }

    /**
     * Execute the given analysis type, based on the given filters. Defers to the Analysis module to do all the
     * hard work.
     * @param filters the filters to use during analysis
     * @return true if the commandline should continue running, false if exit was requested.
     * @throws CommandSyntaxException on unrecognized command.
     */
    public boolean execute(FilterSet filters) {
        //Match to the provided commands, with some aliases mapping to the same commands.
        switch(this.lines[0]) {
            case "nutrition":
            case "total-nutrition":
                this.prettyPrintNutrition(new Analysis(filters).getTotalNutrition());
                return true;
            case "price":
            case "total-price":
                this.prettyPrintPrice(new Analysis(filters).getTotalPrice());
                return true;
            case "servings":
            case "total-servings":
                this.prettyPrintServings(new Analysis(filters).getTotalServings());
                return true;
            case "quantity":
            case "total-quantity":
                this.prettyPrintQuantity(new Analysis(filters).getTotalQuantity());
                return true;
            case "nutrition/$":
            case "nutrition/price":
            case "average-nutrition-per-price":
                this.prettyPrintNutrition(new Analysis(filters).getAverageNutritionPerPrice());
                return true;
            case "servings/$":
            case "servings/price":
            case "average-servings-per-price":
                this.prettyPrintServings(new Analysis(filters).getAverageServingsPerPrice());
                return true;
            case "quantity/$":
            case "quantity/price":
            case "average-quantity-per-price":
                this.prettyPrintQuantity(new Analysis(filters).getAverageQuantityPerPrice());
                return true;
            case "nutrition/day":
            case "average-nutrition-per-day":
                this.prettyPrintNutrition(new Analysis(filters).getAverageNutritionPerDay());
                return true;
            case "servings/day":
            case "average-servings-per-day":
                this.prettyPrintServings(new Analysis(filters).getAverageServingsPerDay());
                return true;
            case "quantity/day":
            case "average-quantity-per-day":
                this.prettyPrintQuantity(new Analysis(filters).getAverageQuantityPerDay());
                return true;
            case "$/day":
            case "price/day":
            case "average-price-per-day":
                this.prettyPrintPrice(new Analysis(filters).getAveragePricePerDay());
                return true;
            case "stores-matching":
            case "list-stores":
                this.prettyPrintList(new Analysis(filters).getStoresMatching(), Store::getName);
                return true;
            case "list-groceries":
            case "groceries-matching":
                this.prettyPrintList(new Analysis(filters).getGroceriesMatching(), Grocery::getName);
                return true;
            case "help":
                System.out.println(HELP_MESSAGE);
                return true;
            case "exit":
                //Indicate to end the loop by returning false
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
        //Get, split,
        this.fetchInput();
        //parse, run!
        return this.execute(this.parseFilter());
    }
}
