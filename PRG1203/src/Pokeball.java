
public class Pokeball {
    private String name;
    private double multiplier;

    public Pokeball(String name, double multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    public String getName() {
        return name;
    }

    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        return name + " (x" + multiplier + ")";
    }
}
