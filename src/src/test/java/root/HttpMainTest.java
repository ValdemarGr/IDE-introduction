package root;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpMainTest {
    @Test
    public void someTest() {
        assertEquals("Run this test", HttpMain.FileHandler.getPath(), "/home/valde/data");
    }
}