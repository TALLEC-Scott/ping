package fr.epita.assistants.aspects;

import fr.epita.assistants.features.*;
import fr.epita.assistants.features.Package;
import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.ArrayList;
import java.util.List;

public class Maven implements Aspect {
    @Override
    public Type getType() {
        return Mandatory.Aspects.MAVEN;
    }

    @Override
    public List<Feature> getFeatureList() {
        List<Feature> ftrs = new ArrayList<>();
        ftrs.add(new Compile());
        ftrs.add(new Clean());
        ftrs.add(new Test());
        ftrs.add(new Package());
        ftrs.add(new Install());
        ftrs.add(new Exec());
        ftrs.add(new Tree());
        return ftrs;
    }
}
