package bg.znestorov.exceptions;

public class ConnectionsCountExceededException extends Exception {

    public ConnectionsCountExceededException() {
        super("Any pair of noes is connected via no more than 1000 links!");
    }

}