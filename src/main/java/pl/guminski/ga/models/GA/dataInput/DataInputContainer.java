package pl.guminski.ga.models.GA.dataInput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataInputContainer implements Cloneable{

    private String problemName;
    private ThiefData thiefData;
    private List<NodeCoord> nodeCoordList;
    private List<Item> items;

    public Object cloneObject() throws CloneNotSupportedException {
        return super.clone();
    }

}
