package bg.znestorov.model;

import bg.znestorov.exceptions.CycleTreeTopology;
import bg.znestorov.exceptions.NotCorrectInputParamsException;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class NetworkPaths {

    public static Computer[] initNetwork(int n) {

        // Check if the network has any connections
        if (n <= 0) {
            return null;
        }

        Computer[] computers = new Computer[n + 1];
        for (int i = 0; i <= n; i++) {
            computers[i] = new Computer(String.valueOf(i));
        }

        return computers;
    }

    public static void constructNetwork(Computer[] computers, Integer[] a, Integer[] b) throws NotCorrectInputParamsException {

        // Check if the input parameters are correct
        if (ArrayUtils.isEmpty(computers) || ArrayUtils.isEmpty(a) || ArrayUtils.isEmpty(b) || computers.length != a.length + 1 || computers.length != b.length + 1) {
            throw new NotCorrectInputParamsException("computers or connections are not correct!");
        }

        // Construct the computer network connections
        for (int i = 0; i < a.length; i++) {
            computers[a[i]].addChild(computers[b[i]]);
        }
    }

    public static List<List<Computer>> getNetworkPathsByComputer(Computer computer) throws CycleTreeTopology {

        List<List<Computer>> networkPaths = new ArrayList<>();

        // Check if the head node exists
        if (computer == null) {
            return networkPaths;
        }

        return getNetworkPathsByComputer(computer, null, new HashSet<>());
    }

    private static List<List<Computer>> getNetworkPathsByComputer(Computer currentComputer, Computer parentComputer, Set<Computer> visitedComputers) throws CycleTreeTopology {

        // In case the element has been already visited, the tree is a graph (there is a cycle inside)
        if (!visitedComputers.add(parentComputer)) {
            throw new CycleTreeTopology(parentComputer.toString());
        }

        List<List<Computer>> networkPaths = new ArrayList<>();
        List<Computer> childComputers = currentComputer.getChildren();

        // If a leaf node is found, a List only containing this leaf node will be created and returned inside the list collection
        // Leaf is a computer with no children or with only one, which is its parent (because of the non-directional tree)
        if (childComputers.size() == 0
                || (childComputers.size() == 1 && childComputers.get(0) == parentComputer)) {
            List<Computer> leafList = new LinkedList<>();
            leafList.add(currentComputer);
            networkPaths.add(leafList);

            // Remove the visited parent computers as we reached the end of this path
            visitedComputers.remove(parentComputer);
        } else {

            // Get all children of the current node and get their paths recursively
            for (Computer childComputer : childComputers) {

                // As this is a non-directional tree, we should remove the already iterated children
                if (childComputer != parentComputer) {
                    List<List<Computer>> nodeLists = getNetworkPathsByComputer(childComputer, currentComputer, visitedComputers);

                    // The parent of this leaf computer will be added to the beginning of the list, which will again be put into a list collection
                    for (List<Computer> computerList : nodeLists) {
                        computerList.add(0, currentComputer);
                        networkPaths.add(computerList);

                        // Remove the visited parent computers as we reached the end of this path and go upstream
                        visitedComputers.remove(parentComputer);
                    }
                }
            }
        }

        return networkPaths;
    }

    public static void printNetworkPaths(Computer[] computers) throws CycleTreeTopology {

        if (!ArrayUtils.isEmpty(computers)) {
            for (Computer computer : computers) {
                List<List<Computer>> networkPaths = getNetworkPathsByComputer(computer);
                printNetworkPathsPerComputer(networkPaths);
            }
        }
    }

    private static void printNetworkPathsPerComputer(List<List<Computer>> networkPaths) {

        for (List<Computer> networkPath : networkPaths) {
            for (int count = 0; count < networkPath.size(); count++) {
                System.out.print(networkPath.get(count).getId());
                if (count != networkPath.size() - 1) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    public static void countOddNetworkPaths(Computer[] computers) throws CycleTreeTopology {

        int count = 0;
        if (!ArrayUtils.isEmpty(computers)) {
            for (Computer computer : computers) {
                List<List<Computer>> networkPaths = getNetworkPathsByComputer(computer);
                count += countOddNetworkPathsPerComputer(networkPaths);
            }
        }

        System.out.println("The number of odd connections in the network is: " + count);
    }

    private static int countOddNetworkPathsPerComputer(List<List<Computer>> networkPaths) {

        int count = 0;
        for (List<Computer> networkPath : networkPaths) {
            if (networkPath.size() / 2 == 0) {
                count++;
            }
        }

        return count;
    }

}