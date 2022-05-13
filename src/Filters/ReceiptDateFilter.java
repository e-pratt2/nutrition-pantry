package Filters;

import Database.Receipt;

import java.time.LocalDate;

public class ReceiptDateFilter implements Filter<Receipt> {
    private LocalDate start, end;

    public ReceiptDateFilter(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
    @Override
    public boolean accepts(Receipt receipt) {
        LocalDate receiptDate = receipt.getDate();
        return (this.start.isBefore(receiptDate) && this.end.isAfter(receiptDate));
    }
}
