import io.jenetics.BitGene;
import io.jenetics.Genotype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluator {
    private final ArrayList<Subject> subjects;
    private final Map<Groups, Integer> ectsLimits;

    public Evaluator(ArrayList<Subject> subjects, Map<Groups, Integer> ectsLimits) {
        this.subjects = subjects;
        this.ectsLimits = ectsLimits;
    }

    public float eval(final Genotype<BitGene> gt) {
        Phenotype phenotype = new Phenotype(gt, subjects);
        return evaluatePhenotype(phenotype);
    }

    private float evaluatePhenotype(Phenotype phenotype) {
        Map<Slots, Integer> usedSlots = new HashMap<>();
        Map<Groups, Integer> groupEcts = new HashMap<>();
        float worth = 0;

        for (Slots slot : Slots.values()) {
            usedSlots.put(slot, 0);
        }
        for (Groups group : Groups.values()) {
            groupEcts.put(group, 0);
        }

        //read genotype
        for (Subject subject: phenotype.getSubjects()) {
            //increase slot counter
            for (Slots slot : subject.getSlots()) {
                usedSlots.replace(slot, usedSlots.get(slot) + 1);
            }

            //increase group ects
            groupEcts.replace(subject.getGroup(), groupEcts.get(subject.getGroup()) + subject.getEcts());

            //increase worth
            worth += subject.getWorth();
        }
        worth = worth / phenotype.getSubjects().size();

        //calculate repeating slots
        int repeatingSlots = 0;
        for (Slots slot : Slots.values()) {
            if(usedSlots.get(slot) > 1) {
                repeatingSlots += usedSlots.get(slot) - 1;
            }
        }

        //calculate missing ects
        float groupPenalty = 1;
        for (Groups group : Groups.values()) {
            if(groupEcts.get(group) <= ectsLimits.get(group)) {
                groupPenalty += (ectsLimits.get(group) - groupEcts.get(group)) * 2;
            }
            else {
                groupPenalty += (groupEcts.get(group) - ectsLimits.get(group)) / 10.;
            }
        }

        return worth / (repeatingSlots + 1) / groupPenalty;
    }


}
