//ReverseString.idl
module ReverseApp{

interface Reverse {
    String reverseString(in string input);
};

};

// =============================

// 1. idlj -fall ReverseString.idl

// 2.Compile All Java
// cd C:\Users\Shreya\workspace\ReverseStringCORBAProject
// javac -d bin -cp src src\ReverseApp\*.java src\ReverseImpl.java
// src\ReverseServer.java src\ReverseClient.java

// 3.Start ORB Daemon (in new CMD)
// orbd -ORBInitialPort 1050

// 4. Run the Server (in new CMD)
// cd C:\Users\Shreya\workspace\ReverseStringCORBAProject
// java -cp bin ReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost

// 5. Run the Client (in new CMD terminal)
// cd C:\Users\Shreya\workspace\ReverseStringCORBAProject
// java -cp bin ReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost

// =========================

// ReverseImpl.java

import ReverseApp.ReversePOA;

public class ReverseImpl extends ReversePOA {
    public String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

}

// =========
// ReverseServer.java

import ReverseApp.ReverseHelper;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class ReverseServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            ReverseImpl reverseObj = new ReverseImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(reverseObj);
            ReverseApp.Reverse href = ReverseHelper.narrow(ref);

            PrintWriter out = new PrintWriter(new FileOutputStream("reverse.ref"));
            out.println(orb.object_to_string(href));
            out.close();

            System.out.println("Server started. Waiting...");
            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// =========

// ReverseClient.java

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
// =========
