package tests.junit;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class JUnitSampleTest {

    @Test
    public void testSimpleAddition() {
        int a = 5;
        int b = 3;
        assertEquals("Sum should be 8", 8, a + b);
    }
}
