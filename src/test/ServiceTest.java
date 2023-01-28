package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.*;

public class ServiceTest {
    private Service service = new Service();
    private Foo foo = new Foo();

    @Test
    void testInitialState() {
        assertEquals(CircuitBreakerState.CLOSED, foo.getState());
    }

    @Test
    void testOpenState() {
        service.setHealthy(false);
        for (int i = 0; i < 2; i++) {
            foo.sendRequest("dummy request");
        }
        assertEquals(CircuitBreakerState.OPEN, foo.getState());
    }

    @Test
    void testPartiallyOpenState() throws InterruptedException {
        service.setHealthy(false);
        for (int i = 0; i < 2; i++) {
            foo.sendRequest("dummy request");
        }
        Thread.sleep(5000);
        assertEquals(CircuitBreakerState.PARTIALLY_OPEN, foo.getState());
    }

    @Test
    void testClosedState() throws InterruptedException {
        service.setHealthy(false);
        for (int i = 0; i < 2; i++) {
            foo.sendRequest("dummy request");
        }
        Thread.sleep(5000);
        service.setHealthy(true);
        for (int i = 0; i < 2; i++) {
            foo.sendRequest("dummy request");
        }
        assertEquals(CircuitBreakerState.CLOSED, foo.getState());
    }

    @Test
    void testReturnOpenState() throws InterruptedException {
        service.setHealthy(false);
        for (int i = 0; i < 2; i++) {
            foo.sendRequest("dummy request");
        }
        Thread.sleep(5000);
        service.setHealthy(false);
        for (int i = 0; i < 2; i++) {
            foo.sendRequest("dummy request");
        }
        assertEquals(CircuitBreakerState.OPEN, foo.getState());
    }


}
