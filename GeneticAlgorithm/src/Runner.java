import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Runner
{
    private static final String definitionFile = "src/dataset_def/100_5_22_15.def";
    public static void main(String[] args)
    {

        Population pop = new Population(definitionFile, 100, 100, 0.1, 0.01, 5);
        evolution(pop);
    }

    private static void evolution(Population population)
    {
        String results = "results.csv";
        population.initialize();

        try
        {
            PrintWriter out = new PrintWriter(results);
            out.println("nr populacji, czas minimalny, czas maksymalny, czas sredni");
            for(int i = 0; i < population.getGenerations(); i++)
            {
                out.println(population.nextGeneration(i));
            }
            out.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Nie da się utworzyć pliku!");
        }
    }
}
