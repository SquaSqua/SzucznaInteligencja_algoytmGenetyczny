import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Runner
{
//    private static final String definitionFile = "src/dataset_def/100_5_22_15.def";
//    private static final String definitionFile = "src/dataset_def/200_10_50_15.def";
//        private static final String definitionFile = "src/dataset_def/100_10_26_15.def";
//    private static final String definitionFile = "src/dataset_def/200_20_97_9.def";
    private static final String definitionFile = "src/dataset_def/200_40_133_15.def";
    public static void main(String[] args)
    {

//        Population pop = new Population(definitionFile, 1, 100, 0.5, 0.01, 0.05);
//        evolutionTournament(pop);
//        evolutionRoulette(pop);
        Population pop1;
        for(int i = 0; i < 5; i++)
        {
            pop1 = new Population(definitionFile, 1, 100, 0.5, 0.01, 0.05);
            greedyPop(pop1);
        }


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

    public static void greedyPop(Population population)
    {
        String results = "results.csv";
        population.initialize();

        try
        {
            PrintWriter out = new PrintWriter(results);
            out.println("nr populacji, najlepszy osobnik, najgorszy osobnik, sredni osobnik");
            for(int i = 0; i < population.getGenerations(); i++)
            {
                out.println(population.statistics(1));
            }
            System.out.println(population.minOfAll);
            out.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Nie da się utworzyć pliku!");
        }
    }

}
