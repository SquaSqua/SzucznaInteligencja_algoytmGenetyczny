public class Runner
{
    private static final String definitionFile = "C:/Users/Ewa Skórska/Documents/GitHub/SzucznaInteligencja_algoytmGenetyczny/GeneticAlgorithm/src/dataset_def/100_5_22_15.def";
    public static void main(String[] args)
    {
        Population pop = new Population(definitionFile, 1000, 100, 2, 2, 5);
        pop.evolution();
    }
}
