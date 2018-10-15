package bg.znestorov.exceptions;

public class ComputersCountExceededException extends Exception {

    public ComputersCountExceededException() {
        super("The number of nodes should be within the range [0..90000]!");
    }

}