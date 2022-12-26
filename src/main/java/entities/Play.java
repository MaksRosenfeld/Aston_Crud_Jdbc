package entities;

import lombok.Data;

@Data
public class Play extends Entity {
    private int id;
    private Playground playground;
    private Game game;
    private double price;
    private int amount;
}
