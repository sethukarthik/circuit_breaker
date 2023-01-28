package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.*;

public class FooTest {

    private Foo foo = new Foo();

    @Test
    public void testInitialState() {
        assertEquals(CircuitBreakerState.CLOSED, foo.getState());
        assertTrue(foo.isHealthy());
    }

    @Test
    public void testOpenState() {
        for (int i = 0; i < 3; i++) {
            foo.sendRequest("dummy request");
        }
        assertEquals(CircuitBreakerState.OPEN, foo.getState());
        assertFalse(foo.isHealthy());
    }

    @Test
    public void testPartiallyOpenState() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            foo.sendRequest("dummy request");
        }
        assertEquals(CircuitBreakerState.OPEN, foo.getState());
        Thread.sleep(5000);
        assertEquals(CircuitBreakerState.PARTIALLY_OPEN, foo.getState());
        assertFalse(foo.isHealthy());
    }

    @Test
    public void testClosedState() {
        for (int i = 0; i < 3; i++) {
            foo.sendRequest("dummy request");
        }
        assertEquals(CircuitBreakerState.OPEN, foo.getState());
        foo.sendRequest("dummy request");
        assertEquals(CircuitBreakerState.CLOSED, foo.getState());
        assertTrue(foo.isHealthy());
    }

    @Test
    public void testUnavailableService() {
        for (int i = 0; i < 3; i++) {
            foo.sendRequest("dummy request");
        }
        assertEquals("Service Unavailable", foo.sendRequest("dummy request"));
    }

    @Test
    public void testAvailableService() {
        String response = foo.sendRequest("dummy request");
        assertNotEquals("Service Unavailable", response);
    }


}
