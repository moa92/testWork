import java.util.Objects;

public class ResponseFieldsGeneral {
    private String dateOfRequest;
    private String observationDate;
    private String valueForObservationDate;

    public ResponseFieldsGeneral(String dateOfRequest, String observationDate, String valueForObservationDate) {
        this.dateOfRequest = dateOfRequest;
        this.observationDate = observationDate;
        this.valueForObservationDate = valueForObservationDate;
    }

    public ResponseFieldsGeneral() {

    }

    public String getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(String dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public String getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(String observationDate) {
        this.observationDate = observationDate;
    }

    public String getValueForObservationDate() {
        return valueForObservationDate;
    }

    public void setValueForObservationDate(String valueForObservationDate) {
        this.valueForObservationDate = valueForObservationDate;
    }

    @Override
    public String toString() {
        return "ResponseFieldsGeneral [valueForObservationDate=" + valueForObservationDate + ", observationDate=" + observationDate
                + ", dateOfRequest=" + dateOfRequest + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseFieldsGeneral)) return false;
        ResponseFieldsGeneral that = (ResponseFieldsGeneral) o;
        return dateOfRequest.equals(that.dateOfRequest) &&
                observationDate.equals(that.observationDate) &&
                valueForObservationDate.equals(that.valueForObservationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfRequest, observationDate, valueForObservationDate);
    }
}
