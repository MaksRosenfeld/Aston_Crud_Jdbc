package entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Play implements Entity {
    private int id;
    private Playground playground;
    private Game game;
    private double price;
    private int amount;
}
