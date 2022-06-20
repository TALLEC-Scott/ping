package fr.epita.assistants.aspects;

import fr.epita.assistants.features.Cleanup;
import fr.epita.assistants.features.Dist;
import fr.epita.assistants.features.Search;
import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.ArrayList;
import java.util.List;

public class Any implements Aspect {
    @Override
    public Type getType() {
        return Mandatory.Aspects.ANY;
    }

    @Override
    public List<Feature> getFeatureList() {
        List<Feature> ftrs = new ArrayList<>();
        ftrs.add(new Cleanup());
        ftrs.add(new Dist());
        ftrs.add(new Search());
        return ftrs;
    }
}
