package fr.epita.assistants.aspects;

import fr.epita.assistants.features.Package;
import fr.epita.assistants.features.*;
import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.ArrayList;
import java.util.List;

public class Git implements Aspect {
    @Override
    public Type getType() {
        return Mandatory.Aspects.GIT;
    }

    @Override
    public List<Feature> getFeatureList() {
        List<Feature> ftrs = new ArrayList<>();
        ftrs.add(new Pull());
        ftrs.add(new Add());
        ftrs.add(new Commit());
        ftrs.add(new Push());
        return ftrs;
    }
}
