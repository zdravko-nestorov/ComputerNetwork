package bg.znestorov.model;

import bg.znestorov.exceptions.ComputersCountExceededException;
import bg.znestorov.exceptions.ConnectionsCountExceededException;
import bg.znestorov.exceptions.CycleTreeTopology;
import bg.znestorov.exceptions.NotCorrectInputParamsException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class NetworkConnections {

    private static final Integer COUNT_TEST_CASES = 10;
    private static final String TEST_CASE = "src/resources/tc%d.txt";

    private static final String TABLE_LINE = "------------------------------------------------------------------------------";
    private static final String TABLE_HEADER = "| Test Case | Node Count | Max Node Links |   Result   | Execution Time (ms) |";
    private static final String TABLE_ROW = "| %-9s | %-10d | %-14d | %-10d | %-19d |";

    public static void analyzeNetworkPerformanceTestCases() throws Exception {

        // Analyze the network for each test case
        printNetworkAnalysesHeader();
        for (int i = 1; i <= COUNT_TEST_CASES; i++) {
            analyzeNetworkPerformance(String.format(TEST_CASE, i));
        }
    }

    private static void analyzeNetworkPerformance(String filePath) throws Exception {

        // Validate and initialize the input parameters
        InputParams inputParams = getInputParams(filePath);

        // Analyze the network start time
        StopWatch timer = new StopWatch();
        timer.start();

        // Construct the network, using the input parameters
        Computer[] computers = initNetwork(inputParams.getConnectionCount());
        constructNetwork(computers, inputParams.getA(), inputParams.getB());

        Set<Connection> network = getNetwork(computers);
        timer.stop();
        printNetworkAnalyses(inputParams, network, timer);
    }

    private static InputParams getInputParams(String filePath) throws NotCorrectInputParamsException, IOException, ComputersCountExceededException {

        String[] params = new String(Files.readAllBytes(Paths.get(filePath))).trim().split(System.lineSeparator());

        // Check if any parameters are passed
        if (ArrayUtils.isEmpty(params)) {
            throw new NotCorrectInputParamsException("There are no input parameters found!");
        }

        // Check if all input parameters are numeric
        if (!Stream.of(params).allMatch(StringUtils::isNumeric)) {
            throw new NotCorrectInputParamsException("All input parameters should be numeric!");
        }

        // Check the connections count
        int connectionsCount = Integer.valueOf(params[0]) - 1;
        if (connectionsCount < 0 || connectionsCount > 90000) {
            throw new ComputersCountExceededException();
        }

        // Check the size of the input parameters (first element contains the number of computers)
        if (params.length != connectionsCount * 2 + 1) {
            throw new NotCorrectInputParamsException("The count of the input parameters is not correct!");
        }

        // Retrieve the arrays describing the links in the network
        if (connectionsCount != 0) {
            int[] a = Stream.of(Arrays.copyOfRange(params, 1, connectionsCount + 1)).mapToInt(Integer::parseInt).toArray();
            int[] b = Stream.of(Arrays.copyOfRange(params, connectionsCount + 1, params.length)).mapToInt(Integer::parseInt).toArray();

            return new InputParams(filePath, connectionsCount, a, b);
        } else {
            return new InputParams(filePath, connectionsCount, new int[]{}, new int[]{});
        }
    }

    private static Computer[] initNetwork(int n) {

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

    private static void constructNetwork(Computer[] computers, int[] a, int[] b) throws NotCorrectInputParamsException {

        // In case no computers are found, just do nothing
        if (ArrayUtils.isEmpty(computers) && ArrayUtils.isEmpty(a) && ArrayUtils.isEmpty(b)) {
            return;
        }

        // Check if the input parameters are correct
        if (computers.length != a.length + 1 || computers.length != b.length + 1) {
            throw new NotCorrectInputParamsException("Computers or connections are not correct!");
        }

        // Construct the computer network connections
        for (int i = 0; i < a.length; i++) {
            computers[a[i]].addChild(computers[b[i]]);
        }
    }

    private static Set<Connection> getNetwork(Computer[] computers) throws CycleTreeTopology, ConnectionsCountExceededException {

        Set<Connection> globalNetwork = new LinkedHashSet<>();

        // Iterate all computers and find their network connections
        if (!ArrayUtils.isEmpty(computers)) {
            for (Computer computer : computers) {
                globalNetwork.addAll(getNetwork(computer));
            }
        }

        return globalNetwork;
    }

    private static Set<Connection> getNetwork(Computer computer) throws CycleTreeTopology, ConnectionsCountExceededException {

        // Check if the head node exists
        if (computer == null) {
            return new LinkedHashSet<>();
        }

        return getNetwork(computer, computer, null, 0, new HashSet<>());
    }

    private static Set<Connection> getNetwork(Computer root, Computer current, Computer parent, int distance, Set<Computer> visited)
            throws CycleTreeTopology, ConnectionsCountExceededException {

        // In case the element has been already visited, the tree is a graph (there is a cycle inside)
        if (!visited.add(parent)) {
            throw new CycleTreeTopology(parent.toString());
        }

        // Check if the maximum number of connections is exceeded
        if (distance > 1000) {
            throw new ConnectionsCountExceededException();
        }

        Set<Connection> network = new LinkedHashSet<>();
        List<Computer> childComputers = current.getChildren();

        // If a leaf computer is found, a connection only containing the root and this leaf computer will be created and returned inside the network
        // Leaf is a computer with no children or with only one, which is its parent (because of the non-directional tree)
        if (childComputers.size() == 0 || (childComputers.size() == 1 && childComputers.get(0) == parent)) {
            initConnection(network, visited, root, current, parent, distance);
        } else {

            // Get all children of the current node and get their paths recursively
            for (Computer childComputer : childComputers) {

                // As this is a non-directional tree, we should remove the already iterated children
                if (childComputer != parent) {

                    // Add the child network to the global one (increase the distance/depth with one)
                    Set<Connection> childNetwork = getNetwork(root, childComputer, current, ++distance, visited);
                    network.addAll(childNetwork);

                    // A connection only containing the root and current computer will be created (decrease the distance/depth with one)
                    initConnection(network, visited, root, current, parent, --distance);
                }
            }
        }

        return network;
    }

    private static void initConnection(Set<Connection> network, Set<Computer> visited, Computer root, Computer current, Computer parent, int distance) {

        // Add the Connection to the network list
        if (root != current && distance % 2 == 1) {
            network.add(new Connection(root, current, distance));
        }

        // Remove the visited parent computers as we reached the end of this path or go upstream
        visited.remove(parent);
    }

    private static void printNetworkAnalysesHeader() {
        System.out.println(TABLE_LINE
                + System.lineSeparator()
                + TABLE_HEADER
                + System.lineSeparator()
                + TABLE_LINE);
    }

    private static void printNetworkAnalyses(InputParams inputParams, Set<Connection> network, StopWatch timer) {
        System.out.println(String.format(TABLE_ROW,
                inputParams.getName(),
                inputParams.getConnectionCount() + 1,
                getMaxNetworkConnections(network),
                network.size(),
                timer.getTime()));
    }

    private static Integer getMaxNetworkConnections(Set<Connection> network) {
        return network.stream()
                .mapToInt(Connection::getDistance)
                .max()
                .orElse(0);
    }

    @SuppressWarnings("unused")
    private static void printNetworkConnections(Set<Connection> network) {
        network.forEach(connection -> System.out.println(connection.toString()));
    }

}