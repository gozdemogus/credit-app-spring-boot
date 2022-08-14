package gradproject.demo.entity;

public enum CreditResult {

    CONFIRMED("CONFIRMED"),
    REJECTED("REJECTED");

    private String type;

    CreditResult(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}