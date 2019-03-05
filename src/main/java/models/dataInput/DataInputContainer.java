package models.dataInput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataInputContainer {

    private String problemName;
    private ThiefData thiefData;
    private List<NodeCoord> nodeCoordList;
    private List<Item> items;

}
