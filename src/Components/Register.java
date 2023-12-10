package Components;

public class Register {
    private Double value;
    private String name;
    private String q;

    public Register() {
        value = 0.0;
        name = "";
        q = null;
    }

    public Register(String name, String q) {
        this.name = name;
        this.q = q;
        this.value = 0.0;
    }

    public Register(Double value, String name, String q) {
        this.value = value;
        this.name = name;
        this.q = q;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
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

    public void setAll(Double value, String q) {
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
