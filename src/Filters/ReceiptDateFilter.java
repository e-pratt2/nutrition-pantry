package Filters;

import Database.Grocery;
import Database.Receipt;

import java.time.LocalDate;

public class ReceiptDateFilter extends Filter<Receipt> {
    private LocalDate start, end;

    public ReceiptDateFilter(Filter<Receipt> child, LocalDate start, LocalDate end) {
        super(child);
        this.start = start;
        this.end = end;
    }

    public ReceiptDateFilter(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
    @Override
    public boolean accepts(Receipt receipt) {
        LocalDate receiptDate = receipt.getDate();
        return (this.start.isBefore(receiptDate) && this.end.isAfter(receiptDate)) && super.accepts(receipt);
    }

    @Override
    public String toString() {
        return "Receipt Date{" + start + " to " + end +"} ->" + super.toString();
    }
}
