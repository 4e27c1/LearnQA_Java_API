package tests;

import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ex10 {
    @Test
    public void testLenth(){
        String str = "jnsdjkf";
        int l = str.length();


       assertTrue(l>=15, "Lenth is less than 15");
    }

}
