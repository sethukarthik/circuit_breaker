package main;

public class Foo {
    private CircuitBreakerState state;
    private int failedRequests;
    private final int threshold;
    private final int timeout; //time to wait before going to partially open state
    private long lastOpenTime;

    public Foo() {
        state = CircuitBreakerState.CLOSED;
        failedRequests = 0;
        threshold = 2;
        timeout = 5; //5 seconds
        lastOpenTime = 0;
    }

    public enum CircuitBreakerState {
        CLOSED,
        OPEN,
        PARTIALLY_OPEN
    }

    public String sendRequest(String request) {
        if (state == CircuitBreakerState.OPEN) {
            if (System.currentTimeMillis() - lastOpenTime > timeout * 1000) {
                state = CircuitBreakerState.PARTIALLY_OPEN;
                failedRequests = 0;
            } else {
                return "main.Service Unavailable";
            }
        }
        try {
            String response = Service.send(request);
            if (state == CircuitBreakerState.PARTIALLY_OPEN) {
                failedRequests++;
                if (failedRequests >= threshold) {
                    state = CircuitBreakerState.OPEN;
                    lastOpenTime = System.currentTimeMillis();
                }
            }
            return response;
        } catch (Exception e) {
            failedRequests++;
            if (state == CircuitBreakerState.CLOSED && failedRequests >= threshold) {
                state = CircuitBreakerState.OPEN;
                lastOpenTime = System.currentTimeMillis();
            }
            return "main.Service Unavailable";
        }
    }

    public boolean isHealthy() {
        return state == CircuitBreakerState.CLOSED;
    }

    public CircuitBreakerState getState() {
        return state;
    }
}
