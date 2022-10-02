package homework.microprocessors.beans;

public class ClockSpeed {
    private final Double min;
    private Double max;

    public ClockSpeed(double min) {
        this.min = min;
    }

    public ClockSpeed(double min, Double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if ((min % 1) == 0)
            sb.append(min.intValue());
        else
            sb.append(min);
        if (max != null) {
            sb.append(" - ");
            if (max % 1 == 0)
                sb.append(max.intValue());
            else
                sb.append(max);
        }
        return sb.toString();
    }
}
