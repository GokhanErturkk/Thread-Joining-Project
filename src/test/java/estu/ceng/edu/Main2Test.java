package estu.ceng.edu;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class Main2Test {

    @Test
    public void testWithInput2TxtAtProjectRoot() throws Exception {
        // Specify the path to input2.txt
        Path inputPath = new File("input2.txt").getAbsoluteFile().toPath();
        Main main = new Main();
        main.path = inputPath.toString();

        // Redirect output to capture the printed text
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        // Start the application
        main.startApp();

        // Wait for all nodes to complete
        for (Node node : main.nodes.values()) {
            node.join();
        }

        // Restore original stdout
        System.setOut(originalOut);

        // Get the captured output
        String result = output.toString().replace("\r\n", "\n");

        // Expected output for the given input2.txt
        String[] expectedLines = {
            "NodeH is being started",
            "NodeF is being started",
            "NodeI is being started",
            "NodeG is being started",
            "NodeA is being started",
            "NodeD is being started",
            "NodeB is waiting for A",
            "NodeW is waiting for F,B",
            "NodeT is waiting for G,H,I",
            "NodeC is waiting for A,D",
            "NodeG is completed",
            "NodeF is completed",
            "NodeI is completed",
            "NodeD is completed",
            "NodeH is completed",
            "NodeT is being started",
            "NodeA is completed",
            "NodeB is being started",
            "NodeC is being started",
            "NodeB is completed",
            "NodeW is being started",
            "NodeT is completed",
            "NodeC is completed",
            "NodeW is completed"
        };

        // Assert the expected lines appear in the captured output
        for (String line : expectedLines) {
            assertTrue("Expected output to contain: " + line, result.contains(line));
        }

        // Optional but useful order checks
        assertTrue(result.indexOf("NodeB is being started") > result.indexOf("NodeA is completed"));
        assertTrue(result.indexOf("NodeC is being started") > result.indexOf("NodeD is completed"));
        assertTrue(result.indexOf("NodeC is being started") > result.indexOf("NodeA is completed"));
        assertTrue(result.indexOf("NodeW is being started") > result.indexOf("NodeB is completed"));
        assertTrue(result.indexOf("NodeW is being started") > result.indexOf("NodeF is completed"));
        assertTrue(result.indexOf("NodeT is being started") > result.indexOf("NodeI is completed"));
        assertTrue(result.indexOf("NodeT is being started") > result.indexOf("NodeH is completed"));
        assertTrue(result.indexOf("NodeT is being started") > result.indexOf("NodeG is completed"));
    }
}
