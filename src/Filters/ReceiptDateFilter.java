package Filters;

import Database.Grocery;
import Database.Receipt;

import java.time.LocalDate;
import java.util.Objects;

public class ReceiptDateFilter extends Filter<Receipt> {
    private LocalDate start, end;

    public ReceiptDateFilter(Filter<Receipt> child, LocalDate start, LocalDate end) {
        super(child);

        if(start == null || end == null)
            throw new IllegalArgumentException("bad param ReceiptDateFilter");
        this.start = start;
        this.end = end;
    }

    public ReceiptDateFilter(LocalDate start, LocalDate end) {
        if(start == null || end == null)
            throw new IllegalArgumentException("bad param ReceiptDateFilter");
        this.start = start;
        this.end = end;
    }
    @Override
    public boolean accepts(Receipt receipt) {

        if(receipt == null)
            throw new IllegalArgumentException("bad param accepts in receipt filter");
        LocalDate receiptDate = receipt.getDate();
        return (this.start.isBefore(receiptDate) && this.end.isAfter(receiptDate)) && super.accepts(receipt);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ReceiptDateFilter))
            return false;

        ReceiptDateFilter other = (ReceiptDateFilter) obj;

        return other.start.equals(this.start) && other.end.equals(this.end) && Objects.equals(other.child, this.child);
    }

    @Override
    public String toString() {
        return "Receipt Date{" + start + " to " + end +"} ->" + super.toString();
    }
}
