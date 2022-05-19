package UI;

import Filters.Filter;

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

        for(int i = 1; i < this.CL.length; i++){

            String[] str = this.CL[i].split(" ");

            if(str[i].equalsIgnoreCase("store"))
                store(str);
            if(str[i].equalsIgnoreCase("grocery"))
                grocery();
            if (str[i].equalsIgnoreCase("receipt"))
                receipt();
            if(str[i].equalsIgnoreCase(" "))
                all();
        }
        return null;
    }

    private Filter store(String[] strings){
        return null;
    }

    private Filter grocery(){
        return null;
    }

    private Filter receipt() {
        return null;
    }

    private Filter all() {
        return null;
    }
}
