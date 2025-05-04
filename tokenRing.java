
import java.util.Scanner;

public class tokenRing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Number of Nodes you want in the Ring: ");
        int n = sc.nextInt();

        System.out.println("\nRing Formed is as below:");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
        }
        System.out.println("0\n");

        int token = 0; // Start with process 0
        int choice;

        do {
            System.out.println("Enter Sender: ");
            int sender = sc.nextInt();

            System.out.println("Enter Receiver: ");
            int receiver = sc.nextInt();
            sc.nextLine(); // Consume leftover newline

            System.out.print("Enter Data to be Sent: ");
            String data = sc.nextLine();
            System.out.println();

            // Show token passing
            System.out.print("Token Passing: ");
            int i = token;
            while (i != sender) {
                System.out.print(i + " -> ");
                i = (i + 1) % n;
            }
            System.out.println(sender);
            System.out.println("Token reached Sender: " + sender);

            // Sender enters critical section
            System.out.println("Sender " + sender + " is ENTERING Critical Section...");
            System.out.println("Sender " + sender + " is Sending Data: \"" + data + "\"");
            System.out.println("Sender " + sender + " EXITED Critical Section.");
            System.out.println();

            // Data forwarding from sender to receiver
            int current = sender;
            while (current != receiver) {
                current = (current + 1) % n;
                System.out.println("Data \"" + data + "\" forwarded by: " + current);
            }

            System.out.println("Receiver " + receiver + " RECEIVED the Data: \"" + data + "\"\n");

            // Token moves to next process after sender
            token = (sender + 1) % n;

            System.out.print("Do you want to send data again? (yes=1 / no=0): ");
            choice = sc.nextInt();
            System.out.println();

        } while (choice == 1);

        sc.close();
    }
}
