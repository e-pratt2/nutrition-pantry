package Filters;

import Database.Receipt;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A filter to test receipts, accepting them if their date lies between a specified range.
 */
public class ReceiptDateFilter extends Filter<Receipt> {
    private final LocalDate start;
    private final LocalDate end;

    /**
     * Decorate a filter with a new receipt date filter.
     * @param child the filter to decorate. This filter will only pass if it's check matches and the child filter matches. May be null.
     * @param start the beginning of the date range, exclusive
     * @param end the ending of the date range, exclusive
     * @throws IllegalArgumentException start or end is null.
     */
    public ReceiptDateFilter(Filter<Receipt> child, LocalDate start, LocalDate end) {
        super(child);

        if(start == null || end == null)
            throw new IllegalArgumentException("bad param ReceiptDateFilter");
        this.start = start;
        this.end = end;
    }

    /**
     * Create a date filter without decorating.
     * @param start the beginning of the date range, exclusive
     * @param end the ending of the date range, exclusive
     * @throws IllegalArgumentException start or end is null.
     */
    public ReceiptDateFilter(LocalDate start, LocalDate end) {
        if(start == null || end == null)
            throw new IllegalArgumentException("bad param ReceiptDateFilter");
        this.start = start;
        this.end = end;
    }


    /**
     * Test the given receipt against this filter and it's children.
     * @param receipt the receipt to check against.
     * @return true if the receipt is within the given date range and this filter's children also accept it.
     * @throws IllegalArgumentException receipt is null.
     */
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
