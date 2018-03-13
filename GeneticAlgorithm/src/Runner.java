public class Runner
{
    private static final String definitionFile = "C:/Users/Ewa Skórska/Documents/GitHub/SzucznaInteligencja_algoytmGenetyczny/GeneticAlgorithm/src/dataset_def/100_5_22_15.def";
    public static void main(String[] args)
    {
        Population pop = new Population(definitionFile, 1000, 100, 0, 0, 5);
        evolution(pop);
    }

    public static void evolution(Population population)
    {
        population.initialize();
//        population.showPopulation();
//        System.out.println("Po ewolucji");
        for(int i = 0; i < population.getGenerations(); i++)
        {
            population.nextGeneration();
        }
//        population.showPopulation();
    }
}
