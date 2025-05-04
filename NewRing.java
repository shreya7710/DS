
//NewRing.java
import java.util.*;

public class NewRing {
    static int n;
    static boolean[] active;
    static int[] processId;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        active = new boolean[n];
        processId = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID for Process " + (i + 1) + ": ");
            processId[i] = sc.nextInt();
            active[i] = true;
        }

        // Fail the highest ID process (simulating coordinator failure)
        int maxId = -1, maxIndex = -1;
        for (int i = 0; i < n; i++) {
            if (processId[i] > maxId) {
                maxId = processId[i];
                maxIndex = i;
            }
        }
        active[maxIndex] = false;
        System.out.println("Process " + maxId + " (Coordinator) has failed.");

        while (true) {
            System.out.println("\n1. Start Election\n2. Quit");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter initiator process number (1 to " + n + "): ");
                int initiator = sc.nextInt() - 1;

                if (!active[initiator]) {
                    System.out.println("Process " + processId[initiator] + " is inactive. Cannot initiate election.");
                } else {
                    startElection(initiator);
                }
            } else {
                break;
            }
        }
        sc.close();
    }

    static void startElection(int initiator) {
        int current = initiator;
        List<Integer> electionList = new ArrayList<>();
        System.out.println("Election started by Process " + processId[initiator]);

        do {
            current = (current + 1) % n;
            if (active[current]) {
                System.out.println("Process " + processId[(current - 1 + n) % n] + " sends message to Process "
                        + processId[current]);
                electionList.add(processId[current]);
            }
        } while (current != initiator);

        electionList.add(processId[initiator]);
        int newCoordinator = Collections.max(electionList);

        System.out.println("\nNew Coordinator selected: Process " + newCoordinator);
        circulateCoordinator(newCoordinator);
    }

    static void circulateCoordinator(int coordinatorId) {
        int start = -1;
        for (int i = 0; i < n; i++) {
            if (processId[i] == coordinatorId) {
                start = i;
                break;
            }
        }

        int i = (start + 1) % n;
        while (i != start) {
            if (active[i]) {
                System.out.println("Coordinator message sent to Process " + processId[i]);
            }
            i = (i + 1) % n;
        }

        System.out.println("All active processes updated with new coordinator: " + coordinatorId);
    }
}
