import io.jenetics.BitGene;
import io.jenetics.Genotype;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Phenotype {
    @Getter
    private final List<Subject> subjects;

    public Phenotype(final Genotype<BitGene> gt, List<Subject> subjectsList) {
        subjects = new ArrayList<>();
        for (int i = 0; i < gt.geneCount(); ++i) {
            if (gt.chromosome().get(i).booleanValue()) {
                subjects.add(subjectsList.get(i));
            }
        }
    }

    @Override
    public String toString() {
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
        for (Subject subject: subjects) {
            //increase slot counter
            for (Slots slot : subject.getSlots()) {
                usedSlots.replace(slot, usedSlots.get(slot) + 1);
            }

            //increase group ects
            groupEcts.replace(subject.getGroup(), groupEcts.get(subject.getGroup()) + subject.getEcts());

            //increase worth
            worth += subject.getWorth();
        }
        worth = worth / subjects.size();

        String string = "";

        string += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        string += "worth: " + worth + "\n";
        string += "-------------------------------------------------------------------\n";
        string += "subjects:\n";
        for (Subject subject: subjects) {
            string += subject.getName() + ", ";
        }
        string += "\n";
        string += "-------------------------------------------------------------------\n";
        string += "ects groups:\n";
        for(Groups group: Groups.values()) {
            string += "" + group + ": " + groupEcts.get(group) + "\n";
        }
        string += "-------------------------------------------------------------------\n";
        string += "used slots:\n";
        for(Slots slot: Slots.values()) {
            if(usedSlots.get(slot) > 0) {
                string += "" + slot + " " + usedSlots.get(slot) + "\n";
            }
        }
        string += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";

        return string;
    }
}
