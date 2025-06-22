package message;

public class Message {
    private String type;         // "PRODUCER" oder "USER"
    private String association;  // "COMMUNITY" oder "GRID"
    private double kwh;
    private String datetime;

    public Message() {}

    public Message(String type, String association, double kwh, String datetime) {
        this.type = type;
        this.association = association;
        this.kwh = kwh;
        this.datetime = datetime;
    }

    // Getter & Setter

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "[" + type + "] (" + association + ") " + kwh + " kWh @ " + datetime;
    }
}
