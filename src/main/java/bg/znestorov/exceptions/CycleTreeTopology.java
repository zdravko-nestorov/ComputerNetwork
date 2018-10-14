package bg.znestorov.exceptions;

public class CycleTreeTopology extends Exception {

    public CycleTreeTopology(String msg) {
        super("There are pairs of nodes (directly or indirectly) connected by links, and links form cycles: " + msg);
    }

}