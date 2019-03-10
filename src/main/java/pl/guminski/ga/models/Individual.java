package pl.guminski.ga.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Individual {

    private Integer generation;
    private List<Integer> chromosome;
    private double fitness;

}
