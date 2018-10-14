package bg.znestorov.exceptions;

public class LinksCountExceededException extends Exception {

    public LinksCountExceededException() {
        super("Any pair of noes is connected via no more than 1000 links!");
    }

}