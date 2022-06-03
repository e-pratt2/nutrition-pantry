package Analysis;

import Database.*;
import Filters.FilterSet;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that executes analysis functions on the database, with a given filter.
 */
public class Analysis {
    /**
     * Stores every passing grocery, along with the receipt and store that grocery is associated with
     */
    private final ArrayList<Result> results;

    /**
     * For determining the timeframe this analysis spans. Set during the constructor to match range of dates in the
     * receipts.
     */
    LocalDate firstDate, lastDate;

    /**
     * Represents one store+receipt+grocery combination that passed all the filters.
     * Provides some rudimentary analysis of the relationship between stores <> receipts <> groceries.
     */
    private static class Result {
        private final Store store;
        private final Receipt receipt;
        private final Grocery grocery;

        /**
         * Greate a result around the given store, receipt, grocery combination
         * @param s the store to use
         * @param r the receipt to use, should be a member of the store
         * @param g the grocery to use, should be a member of the receipt
         */
        public Result(Store s, Receipt r, Grocery g) {
            this.store = s;
            this.receipt = r;
            this.grocery = g;
        }

        /**
         * Calculate the total price of this grocery based on the prices within store
         * @return the total price of this grocery on this receipt
         */
        double getTotalPrice() {
            return this.receipt.getQuantityOf(this.grocery)
                    * this.store.getPriceOf(this.grocery);
        }

        /**
         * Calculate the total number of servings of the grocery - that is, servings per container times number of containers
         * @return the total number of servings of the grocery for this receipt
         */
        double getServings() {
            return this.grocery.getServingsPerContainer()
                    * this.receipt.getQuantityOf(this.grocery);
        }

        /**
         * Get the total number of this grocery on the receipt
         * @return the quantity of this grocery as recorded by the receipt
         */
        double getQuantity() {
            return this.receipt.getQuantityOf(grocery);
        }


        /**
         * Get the total nutrition of this grocery on the receipt - that is, the grocery's nutrition times the quantity
         * of this grocery.
         * @return the total nutrition of this grocery for this receipt
         */
        Nutrition getTotalNutrition() {
            return this.grocery.getTotalNutrition()
                    .multiply(this.receipt.getQuantityOf(this.grocery));
        }
    }

    /**
     * Construct a new analysis session.
     * @param fs the filters to use during analysis
     */
    public Analysis(FilterSet fs) {
        this.results = new ArrayList<>();

        //Check each store, receipt, and grocery and only add them if they pass all three filters.
        for(Store s : SerializableDatabase.getInstance().getStores()) {
            if(!fs.getStore().accepts(s))
                continue;

            for(Receipt r : s.getReceipts()) {
                if(!fs.getReceipt().accepts(r))
                    continue;

                for(Grocery g : r.getGroceries()) {
                    if(!fs.getGrocery().accepts(g))
                        continue;

                    //Passed! Add this result to the list.
                    this.results.add(new Result(s, r, g));

                    //Check receipt date and update first/last date to match
                    if(firstDate == null || firstDate.isAfter(r.getDate()))
                        firstDate = r.getDate();
                    if(lastDate == null || lastDate.isBefore(r.getDate()))
                        lastDate = r.getDate();
                }
            }
        }
    }

    /**
     * Get the total price of all groceries in this analysis session
     * @return the total price
     */
    public double getTotalPrice() {
        double total = 0.0;

        for(Result r : this.results)
            total += r.getTotalPrice();

        return total;
    }

    /**
     * Get the total nutrition of all the groceries in this analysis session
     * @return the total nutrition
     */
    public Nutrition getTotalNutrition() {
        Nutrition n = new Nutrition();

        for(Result r : this.results)
            n.add(r.getTotalNutrition());

        return n;
    }

    /**
     * Get the total number of servings in this analysis session
     * @return the total number of servings
     */
    public double getTotalServings() {
        double total = 0.0;

        for(Result r : this.results)
            total += r.getServings();

        return total;
    }

    /**
     * Get the total number of grocery units in this analysis session - that is, buying 5 watermelons will be 5 units.
     * @return the total quantity of groceries
     */
    public double getTotalQuantity() {
        double total = 0.0;

        for(Result r : this.results)
            total += r.getQuantity();

        return total;
    }

    /**
     * Calculate the total nutrition divided by the total price.
     * @return the average nutrition per money unit
     */
    public Nutrition getAverageNutritionPerPrice() {
        double totalPrice = this.getTotalPrice();
        Nutrition totalNutrition = this.getTotalNutrition();

        return totalNutrition.multiply(1.0/totalPrice);
    }

    /**
     * Calculate the total servings divided by the total price.
     * @return the average number of servings per money unit
     */
    public double getAverageServingsPerPrice() {
        double servings = this.getTotalServings();
        double totalPrice = this.getTotalPrice();

        return servings / totalPrice;
    }

    /**
     * Calculate the total quantity divided by the total price - buying 5 watermelons counts as 5 quantity units.
     * @return the average quantity of groceries per money unit
     */
    public double getAverageQuantityPerPrice() {
        double quantity = this.getTotalQuantity();
        double totalPrice = this.getTotalPrice();

        return quantity / totalPrice;
    }

    /**
     * Get the total number of days spanned by this analysis session - the tiem difference between the first and last receipts.
     * @return the number of days
     */
    public long getNumberOfDays() {
        if(this.firstDate == null || this.lastDate == null)
            return 1;
        return ChronoUnit.DAYS.between(this.firstDate, this.lastDate) + 1;
    }

    /**
     * Calculate the total quantity divided by the number of days - buying 5 watermelons counts as 5 quantity units.
     * @return the average quantity of groceries per day
     */
    public double getAverageQuantityPerDay() {
        return this.getTotalQuantity() / this.getNumberOfDays();
    }

    /**
     * Calculate the total number of servings divided by the number of days
     * @return the average number of servings per day
     */
    public double getAverageServingsPerDay() {
        return this.getTotalServings() / this.getNumberOfDays();
    }

    /**
     * Calculate the total price divided by the number of days
     * @return the average price per day
     */
    public double getAveragePricePerDay() {
        return this.getTotalPrice() / this.getNumberOfDays();
    }

    /**
     * Calculate the total nutrition divided by the number of days
     * @return the average nutrition per day
     */
    public Nutrition getAverageNutritionPerDay() {
        double numberOfDays = getNumberOfDays();
        Nutrition totalNutrition = this.getTotalNutrition();

        return totalNutrition.multiply(1.0/numberOfDays);
    }

    /**
     * Get a list of all the stores that contributed to this analysis session. That is, all stores which passed, had at
     * least one receipt that passed, had at least one grocery pass.
     * @return the list of stores in this analysis session.
     */
    public List<Store> getStoresMatching() {
        ArrayList<Store> stores = new ArrayList<>();

        for(Result r : this.results)
            if(!stores.contains(r.store))
                stores.add(r.store);

        return stores;
    }

    /**
     * Get a list of all the groceries that contributed to this analysis session. That is, all groceries who pass and who's associated
     * store and receipt also passed.
     * @return the list of groceries in this analysis session.
     */
    public List<Grocery> getGroceriesMatching() {
        ArrayList<Grocery> groceries = new ArrayList<>();

        for(Result r : this.results)
            if(!groceries.contains(r.grocery))
                groceries.add(r.grocery);

        return groceries;
    }
}
