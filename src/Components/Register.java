package Components;

public class Register {
    private double value;
    private String name;
    private String q;

    public Register() {
        value = 0;
        name = "";
        q = "";
    }

    public Register(String name, String q) {
        this.name = name;
        this.q = q;
        this.value = 0;
    }

    public Register(double value, String name, String q) {
        this.value = value;
        this.name = name;
        this.q = q;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;

    }

    public void setAll(double value, String q) {
        this.value = value;
        this.q = q;
    }

    public String toString() {
        return "Register{" +
                "value=" + value +
                ", name='" + name + '\'' +
                ", q='" + q + '\'' +
                '}';
    }
}
