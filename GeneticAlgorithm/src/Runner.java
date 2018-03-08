import runners.MSRCPSPGARunner;

import java.util.logging.Logger;

public class Runner {

    private static final Logger LOGGER = Logger.getLogger(MSRCPSPGARunner.class.getName());
    private static final String definitionFile = "C:/Users/Ewa Skórska/Documents/GitHub/SzucznaInteligencja_algoytmGenetyczny/GeneticAlgorithm/src/dataset_def/100_5_22_15.def";
    public static void main(String[] args)
    {
        Population pop = new Population(100);
        pop.initialize(definitionFile);
    }
}
