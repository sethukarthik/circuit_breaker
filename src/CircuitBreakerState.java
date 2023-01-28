public class CircuitBreakerState {
    public static final CircuitBreakerState CLOSED = new CircuitBreakerState("CLOSED");
    public static final CircuitBreakerState OPEN = new CircuitBreakerState("OPEN");
    public static final CircuitBreakerState PARTIALLY_OPEN = new CircuitBreakerState("PARTIALLY_OPEN");
    private final String name;
    private CircuitBreakerState(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
