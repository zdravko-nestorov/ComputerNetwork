package bg.znestorov.exceptions;

public class NodesCountExceededException extends Exception {

    public NodesCountExceededException() {
        super("The number of nodes should be within the range [0..90000]!");
    }

}