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
