package bg.znestorov.model;

import java.util.Arrays;

class InputParams {

    private String name;
    private int connectionCount;
    private int[] a;
    private int[] b;

    InputParams(String name, int connectionCount, int[] a, int[] b) {
        this.name = name;
        this.connectionCount = connectionCount;
        this.a = a;
        this.b = b;
    }

    String getName() {
        int lastSlashIndex = name.lastIndexOf("/");
        if (lastSlashIndex > 0) {
            return name.substring(lastSlashIndex + 1);
        } else {
            return name;
        }
    }

    int getConnectionCount() {
        return connectionCount;
    }

    int[] getA() {
        return a;
    }

    int[] getB() {
        return b;
    }

    @Override
    public String toString() {
        return "InputParams{" +
                "name='" + name + '\'' +
                ", connectionCount=" + connectionCount +
                ", a=" + Arrays.toString(a) +
                ", b=" + Arrays.toString(b) +
                '}';
    }

}