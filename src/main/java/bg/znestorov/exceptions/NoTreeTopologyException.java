package bg.znestorov.exceptions;

public class NoTreeTopologyException extends Exception {

    public NoTreeTopologyException() {
        super("The network hasn't got a tree topology!");
    }

}