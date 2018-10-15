package bg.znestorov.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Computer {

    private String id;
    private List<Computer> children;

    Computer(String id) {
        this.id = id;
        this.children = new LinkedList<>();
    }

    String getId() {
        return this.id;
    }

    List<Computer> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    void addChild(Computer child) {
        this.children.add(child);
        child.children.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Computer computer = (Computer) o;
        return Objects.equals(id, computer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Computer[" + id + "]";
    }

}