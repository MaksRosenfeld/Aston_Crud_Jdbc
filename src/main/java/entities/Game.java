package entities;

import lombok.Data;

@Data
public class Game extends Entity {
    private int id;
    private String name;
    private boolean exclusive;
}
