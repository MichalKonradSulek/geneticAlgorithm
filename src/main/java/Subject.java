import lombok.Getter;

import java.util.List;

@Getter
public class Subject {
    private final String name;
    private final List<Slots> slots;
    private final Groups group;
    private final int ects;
    private final int worth;

    public Subject(String name, List<Slots> slots, Groups group, int ects, int worth) {
        this.name = name;
        this.slots = slots;
        this.group = group;
        this.ects = ects;
        this.worth = worth;
    }
}
