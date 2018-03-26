import msrcpsp.scheduling.schedule_builders.ForwardScheduleBuilder;
import msrcpsp.scheduling.schedule_builders.ScheduleBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Runner
{
    private static final String definitionFile = "src/dataset_def/100_5_22_15.def";
    public static void main(String[] args)
    {

        Population pop = new Population(definitionFile, 1, 3, 0, 0, 0.14);
//        evolutionTournament(pop);
        evolutionRoulette(pop);

    }

    private static void evolutionTournament(Population population)
    {
        String results = "results.csv";
        population.initialize();

        try
        {
            PrintWriter out = new PrintWriter(results);
            out.println("nr populacji, najlepszy osobnik, najgorszy osobnik, sredni osobnik");
            for(int i = 0; i < population.getGenerations(); i++)
            {
                out.println(population.nextGenerationTournament(i));
            }
            System.out.println("Najlepszy w ewolucji: " + population.minOfAll);
            out.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Nie da się utworzyć pliku!");
        }
    }

    private static void evolutionRoulette(Population population)
    {
        String results = "results.csv";
        population.initialize();
        try
        {
            PrintWriter out = new PrintWriter(results);
            out.println("nr populacji, najlepszy osobnik, najgorszy osobnik, sredni osobnik");
            for(int i = 0; i < population.getGenerations(); i++)
            {
                out.println(population.nextGenerationRoulette(i));
            }
            System.out.println("Najlepszy w ewolucji: " + population.minOfAll);
            out.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Nie da się utworzyć pliku!");
        }
    }
}
