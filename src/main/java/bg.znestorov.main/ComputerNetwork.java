package bg.znestorov.main;

import bg.znestorov.model.Computer;

import static bg.znestorov.model.NetworkPaths.*;

public class ComputerNetwork {

    private static final Integer N = 6;
    private static final Integer[] A = {0, 3, 4, 2, 6, 3};
    private static final Integer[] B = {3, 1, 3, 3, 3, 5};

    public static void main(String[] args) throws Exception {
        Computer[] computers = initNetwork(N);
        constructNetwork(computers, A, B);

        printNetworkPaths(computers);
        countOddNetworkPaths(computers);
    }

}