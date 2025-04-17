package estu.ceng.edu;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testWithInputTxtAtProjectRoot() throws Exception {
        Path inputPath = new File("input1.txt").getAbsoluteFile().toPath(); // adjust filename if needed
        Main main = new Main();
        main.path = inputPath.toString();
    
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));
    
        main.startApp();
    
        for (Node node : main.nodes.values()) {
            node.join();
        }
    
        System.setOut(originalOut);
        String result = output.toString().replace("\r\n", "\n");
    
        String[] expectedLines = {
            "NodeA is being started",
            "NodeB is waiting for A",
            "NodeC is waiting for A,D",
            "NodeD is being started",
            "NodeE is waiting for A,B,C,D",
            "NodeF is being started",
            "NodeG is being started",
            "NodeA is completed",
            "NodeB is being started",
            "NodeB is completed",
            "NodeC is being started",
            "NodeC is completed",
            "NodeD is completed",
            "NodeE is being started",
            "NodeE is completed",
            "NodeF is completed",
            "NodeG is completed"
        };
    
        for (String line : expectedLines) {
            assertTrue("Expected output to contain: " + line, result.contains(line));
        }
    
        // Optional but useful order checks
        assertTrue(result.indexOf("NodeB is being started") > result.indexOf("NodeA is completed"));
        assertTrue(result.indexOf("NodeC is being started") > result.indexOf("NodeA is completed"));
        assertTrue(result.indexOf("NodeC is being started") > result.indexOf("NodeD is completed"));
        assertTrue(result.indexOf("NodeE is being started") > result.indexOf("NodeB is completed"));
        assertTrue(result.indexOf("NodeE is being started") > result.indexOf("NodeC is completed"));
        assertTrue(result.indexOf("NodeE is being started") > result.indexOf("NodeA is completed"));
        assertTrue(result.indexOf("NodeE is being started") > result.indexOf("NodeB is completed"));
    }
    
}
