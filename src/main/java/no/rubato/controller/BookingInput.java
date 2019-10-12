package no.rubato.controller;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class BookingInput {
    @NotNull(message = "Start date is required")
    private Date fromDate;

    @NotNull(message = "End date is required")
    private Date toDate;

    @NotNull(message = "Band is required")
    private long bandId;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public long getBandId() {
        return bandId;
    }

    public void setBandId(long bandId) {
        this.bandId = bandId;
    }
}
