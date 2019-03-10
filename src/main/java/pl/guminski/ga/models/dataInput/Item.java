package pl.guminski.ga.models.dataInput;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private long index;
    private long profit;
    private long weight;
    private long node;

}
