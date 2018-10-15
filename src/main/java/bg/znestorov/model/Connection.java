package bg.znestorov.model;

import java.util.Objects;

public class Connection {

    private Computer computer1;
    private Computer computer2;
    private int distance;

    public Connection(Computer computer1, Computer computer2, int distance) {
        this.computer1 = computer1;
        this.computer2 = computer2;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Connection that = (Connection) o;
        return distance == that.distance &&
                ((Objects.equals(computer1, that.computer1) && Objects.equals(computer2, that.computer2))
                        || (Objects.equals(computer1, that.computer2) && Objects.equals(computer2, that.computer1)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance) + Objects.hash(computer1, computer2) + Objects.hash(computer2, computer1);
    }

    @Override
    public String toString() {
        return computer1 + " <-- " + distance + " --> " + computer2;
    }

}