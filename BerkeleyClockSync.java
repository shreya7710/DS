import java.util.*;

class ClockNode {
    private int time;
    private String name;

    public ClockNode(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void adjustTime(int offset) {
        this.time = this.time + offset;
    }
}

class BerkeleyClockSync {
    private List<ClockNode> nodes;

    public BerkeleyClockSync(List<ClockNode> nodes) {
        this.nodes = nodes;
    }

    public void synchronize() {
        ClockNode coordinator = nodes.get(0); // Assume first node is coordinator
        System.out.println("Coordinator: " + coordinator.getName());

        int sum = 0;
        for (ClockNode node : nodes) {
            sum += node.getTime();
        }

        int average = sum / nodes.size();
        System.out.println("Average Time Calculated by Coordinator: " + average);

        for (ClockNode node : nodes) {
            int offset = average - node.getTime();
            node.adjustTime(offset);
            System.out.println("Offset for " + node.getName() + ": " + offset);
        }
    }

    public void printTimes(String title) {
        System.out.println("\n" + title);
        for (ClockNode node : nodes) {
            System.out.println(node.getName() + ": " + node.getTime());
        }
    }

    public static void main(String[] args) {
        List<ClockNode> nodes = new ArrayList<>();
        nodes.add(new ClockNode("Node-1", 100)); // Coordinator
        nodes.add(new ClockNode("Node-2", 200));
        nodes.add(new ClockNode("Node-3", 150));
        nodes.add(new ClockNode("Node-4", 180));

        BerkeleyClockSync sync = new BerkeleyClockSync(nodes);

        sync.printTimes("Clock Times Before Synchronization:");
        sync.synchronize();
        sync.printTimes("Clock Times After Synchronization:");
    }
}
