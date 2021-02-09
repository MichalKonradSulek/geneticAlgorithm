import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

import java.util.*;


public class GeneticAlgorithm {

    public static void main(final String[] args) {
        //Generation of example
        ArrayList<Subject> availableSubjects  = createSubjects();
        Map<Groups, Integer> ectsLimits = createLimits();

        //Evaluator veneration
        Evaluator evaluator = new Evaluator(availableSubjects, ectsLimits);

        //Genotype factory
        final Factory<Genotype<BitGene>> gtf = Genotype.of(BitChromosome.of(availableSubjects.size(), 0.5));

        //Calculations engine
        final Engine<BitGene,Float> engine = Engine.builder(evaluator::eval, gtf)
                .alterers(new SinglePointCrossover<>(0.2), new Mutator<>(0.15))
                .populationSize(100)
                .survivorsSelector(new TournamentSelector<>(3))
                .build();

        //Generation of n results
        for(int i = 0; i < 5; ++i) {
            final Genotype<BitGene> result = engine.stream().limit(1000).collect(EvolutionResult.toBestGenotype());
            Phenotype bestOne = new Phenotype(result, availableSubjects);
            System.out.println("Best result: " + evaluator.eval(result));
            System.out.println(bestOne);
        }
    }

    private static Map<Groups, Integer> createLimits() {
        HashMap<Groups, Integer> limits = new HashMap<>();
        limits.put(Groups.BASIC, 10);
        limits.put(Groups.ADVANCED, 8);
        limits.put(Groups.OPTIONAL, 8);
        limits.put(Groups.HUMANISTIC, 4);
        return limits;
    }

    private static ArrayList<Subject> createSubjects() {
        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("APSG", Arrays.asList(Slots.MONDAY_1, Slots.MONDAY_2), Groups.BASIC, 4, 100));
        subjects.add(new Subject("AM", Arrays.asList(Slots.MONDAY_2, Slots.MONDAY_3), Groups.BASIC, 5, 100));
        subjects.add(new Subject("EADS", Arrays.asList(Slots.TUESDAY_2, Slots.FRIDAY_1), Groups.BASIC, 4, 100));
        subjects.add(new Subject("ALHE", Arrays.asList(Slots.WEDNESDAY_1, Slots.WEDNESDAY_3), Groups.BASIC, 3, 100));
        subjects.add(new Subject("AISDE", Arrays.asList(Slots.WEDNESDAY_4), Groups.BASIC, 2, 100));
        subjects.add(new Subject("AISDI", Arrays.asList(Slots.THURSDAY_1, Slots.THURSDAY_2, Slots.THURSDAY_5), Groups.BASIC, 7, 100));
        subjects.add(new Subject("BSO", Arrays.asList(Slots.THURSDAY_3, Slots.THURSDAY_4), Groups.BASIC, 3, 100));
        subjects.add(new Subject("BSS", Arrays.asList(Slots.FRIDAY_3, Slots.FRIDAY_4), Groups.BASIC, 4, 100));

        subjects.add(new Subject("AMHE", Arrays.asList(Slots.MONDAY_3, Slots.FRIDAY_2), Groups.ADVANCED, 4, 100));
        subjects.add(new Subject("AIR", Arrays.asList(Slots.TUESDAY_2, Slots.THURSDAY_1), Groups.ADVANCED, 5, 100));
        subjects.add(new Subject("ORACL", Arrays.asList(Slots.FRIDAY_2, Slots.FRIDAY_3, Slots.FRIDAY_4), Groups.ADVANCED, 6, 100));
        subjects.add(new Subject("EBDE", Arrays.asList(Slots.WEDNESDAY_3, Slots.WEDNESDAY_4), Groups.ADVANCED, 4, 100));
        subjects.add(new Subject("BUA", Arrays.asList(Slots.TUESDAY_4), Groups.ADVANCED, 2, 100));
        subjects.add(new Subject("BD1", Arrays.asList(Slots.MONDAY_4, Slots.THURSDAY_1), Groups.ADVANCED, 4, 100));

        subjects.add(new Subject("BCYB", Arrays.asList(Slots.FRIDAY_1), Groups.OPTIONAL, 2, 100));
        subjects.add(new Subject("CHA", Arrays.asList(Slots.MONDAY_1, Slots.THURSDAY_4), Groups.OPTIONAL, 4, 100));
        subjects.add(new Subject("ECOTE", Arrays.asList(Slots.WEDNESDAY_1, Slots.WEDNESDAY_2, Slots.THURSDAY_2), Groups.OPTIONAL, 6, 100));
        subjects.add(new Subject("CPOO", Arrays.asList(Slots.TUESDAY_3, Slots.THURSDAY_4), Groups.OPTIONAL, 3, 100));
        subjects.add(new Subject("CPSF", Arrays.asList(Slots.THURSDAY_3), Groups.OPTIONAL, 2, 100));
        subjects.add(new Subject("CPU", Arrays.asList(Slots.MONDAY_3, Slots.THURSDAY_3), Groups.OPTIONAL, 4, 100));

        subjects.add(new Subject("HDA", Arrays.asList(Slots.WEDNESDAY_3, Slots.WEDNESDAY_4), Groups.HUMANISTIC, 4, 100));
        subjects.add(new Subject("HPE", Arrays.asList(Slots.MONDAY_4), Groups.HUMANISTIC, 3, 100));
        subjects.add(new Subject("HGU", Arrays.asList(Slots.FRIDAY_4), Groups.HUMANISTIC, 2, 100));
        subjects.add(new Subject("HPP", Arrays.asList(Slots.TUESDAY_1), Groups.HUMANISTIC, 2, 100));

        return subjects;
    }
}
