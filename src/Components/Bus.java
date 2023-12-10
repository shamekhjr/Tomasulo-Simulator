package Components;

public class Bus {
    boolean populated;
    Double value;
    String tag;

    public Bus() {
        populated = false;
        value = null;
        tag = null;
    }

    public Bus(boolean populated, Double value, String tag) {
        this.populated = populated;
        this.value = value;
        this.tag = tag;
    }

    public boolean isPopulated() {
        return populated;
    }

    public void setPopulated(boolean populated) {
        this.populated = populated;
    }

    public Double getValue() {
        return isPopulated() ? value : null;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getTag() {
        return isPopulated() ? tag : null;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void publish(String tag, Double value) {
        this.tag = tag;
        this.value = value;
        populated = true;
    }
}
