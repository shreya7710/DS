import ReverseApp.Reverse;
import ReverseApp.ReverseHelper;
import org.omg.CORBA.ORB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class ReverseClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            BufferedReader br = new BufferedReader(new FileReader("reverse.ref"));
            String ior = br.readLine();
            br.close();

            org.omg.CORBA.Object objRef = orb.string_to_object(ior);
            Reverse reverseObj = ReverseHelper.narrow(objRef);

            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a string to reverse: ");
            String input = userInputReader.readLine();

            String output = reverseObj.reverseString(input);

            System.out.println("Input: " + input);
            System.out.println("Reversed Output: " + output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
