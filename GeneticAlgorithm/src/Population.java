import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evaluation.DurationEvaluator;
import msrcpsp.io.MSRCPSPIO;
import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;
import msrcpsp.scheduling.schedule_builders.ForwardScheduleBuilder;
import msrcpsp.scheduling.schedule_builders.ScheduleBuilder;
import java.util.List;
import java.util.Random;

public class Population
{
    private Schedule[] population;
    private int generations;
    private int pop_size;
    private double cross_prob;
    private double mut_prob;
    private int tournamentSize;
    private String definitionFile;

    public Population(String definitionFile, int generations, int pop_size, double cross_prob, double mut_prob, int tournamentSize)
    {
        this.generations = generations;
        this.pop_size = pop_size;
        population = new Schedule[pop_size];
        this.cross_prob = cross_prob;
        this.mut_prob = mut_prob;
        this.tournamentSize = tournamentSize;
        this.definitionFile = definitionFile;
    }

    public void initialize()
    {
        MSRCPSPIO reader = new MSRCPSPIO();
        Schedule sch = reader.readDefinition(definitionFile);
        for(int i = 0; i < pop_size; i++)
        {
            Schedule sch2 = new Schedule(sch);
            schedule(sch2);
            ScheduleBuilder builder = new ForwardScheduleBuilder(sch2.getSuccesors());
            builder.buildTimestamps(sch2);
            population[i] = sch2;
        }
    }

//    public void showPopulation()
//    {
//        for(int z = 0; z < pop_size; z++)
//        {
//            Task[] tasks = population[z].getTasks();
//            for(Task task : tasks)
//            {
//                System.out.print(task.getId() + " ");
//            }
//            System.out.println();
//            for(int j = 0; j < tasks.length; j++)
//            {
//                if(j < 9)
//                {
//                    System.out.print(tasks[j].getResourceId() + " ");
//                }
//                else
//                {
//                    System.out.print(tasks[j].getResourceId() + "  ");
//                }
//            }
//            System.out.println();
//            for(int i = 0; i < tasks.length*3 - 9; i++)
//            {
//                System.out.print("_");
//            }
//            System.out.println();
//        }
//    }

//    public void showIndividual(Schedule schedule)
//    {
//        Task[] tasks = schedule.getTasks();
//        for(Task task : tasks)
//        {
//            System.out.print(task.getId() + " ");
//        }
//        System.out.println();
//        for(int j = 0; j < tasks.length; j++)
//        {
//            if(j < 9)
//            {
//                System.out.print(tasks[j].getResourceId() + " ");
//            }
//            else
//            {
//                System.out.print(tasks[j].getResourceId() + "  ");
//            }
//        }
//    }

    private void schedule(Schedule schedule)
    {
        Random rand = new Random();
        Task[] tasks = schedule.getTasks();
        for (Task task : tasks) {
            List<Resource> capRes = schedule.getCapableResources(task);
            int random = rand.nextInt(capRes.size());
            task.setResourceId(capRes.get(random).getId());
        }
    }

    private Schedule[] crossing_over(Schedule sch1, Schedule sch2)
    {
        //check if there will be a crossing
        Random rand = new Random();
        Schedule[] result;
        if(rand.nextDouble() < cross_prob)
        {
            //if yes, then find an index of crossing point
            int cross_ind = rand.nextInt(sch1.getTasks().length);
            Schedule schedule1 = new Schedule(sch1);
            Schedule schedule2 = new Schedule(sch2);
            for(int i = 0; i < cross_ind; i ++)
            {
                schedule1.getTasks()[i].setResourceId(sch1.getTasks()[i].getResourceId());
                schedule2.getTasks()[i].setResourceId(sch2.getTasks()[i].getResourceId());
            }
            for(int i = cross_ind; i < schedule1.getTasks().length; i ++)
            {
                schedule1.getTasks()[i].setResourceId(sch2.getTasks()[i].getResourceId());
                schedule2.getTasks()[i].setResourceId(sch1.getTasks()[i].getResourceId());
            }
            ScheduleBuilder builder1 = new ForwardScheduleBuilder(schedule1.getSuccesors());
            builder1.buildTimestamps(schedule1);
            ScheduleBuilder builder2 = new ForwardScheduleBuilder(schedule2.getSuccesors());
            builder2.buildTimestamps(schedule2);

            //return those 2 crossed individuals in an array
            result = new Schedule[]{schedule1, schedule2};
        }
        else
        {
            //if no then return 2 parents in an array
            result = new Schedule[]{sch1, sch2};
        }
        return result;
    }

    private Schedule mutation(Schedule sch1)
    {
        Random rand = new Random();
        Schedule schedule = new Schedule(sch1);
        for(int i = 0; i < sch1.getTasks().length; i++)
        {
            if(rand.nextDouble() < mut_prob)
            {
                Task task = sch1.getTasks()[i];
                List<Resource> capRes = sch1.getCapableResources(task);
                schedule.getTasks()[i].setResourceId(capRes.get(rand.nextInt(capRes.size())).getId());
            }
            else
            {
                schedule.getTasks()[i].setResourceId(sch1.getTasks()[i].getResourceId());
            }
        }
        ScheduleBuilder builder = new ForwardScheduleBuilder(schedule.getSuccesors());
        builder.buildTimestamps(schedule);
        return schedule;
    }

    private Schedule tournament()
    {
        Schedule[] tournament = new Schedule[tournamentSize];
        Schedule winner;
        double lowestDuration;
        Random random = new Random();
        for(int i = 0; i < tournamentSize; i++)
        {
            tournament[i] = new Schedule(population[random.nextInt(pop_size)]);
        }
        winner = tournament[0];
        BaseEvaluator evaluator1 = new DurationEvaluator(winner);
        lowestDuration = evaluator1.evaluate();
        if(tournamentSize > 1)
        {
            for(int i = 1; i < tournamentSize; i++)
            {
                BaseEvaluator evaluatorTemp = new DurationEvaluator(tournament[i]);
                double nextDuration = evaluatorTemp.evaluate();
                if(lowestDuration > nextDuration)
                {
                    lowestDuration = nextDuration;
                    winner = tournament[i];
                }
            }
        }
        return winner;
    }

    public String nextGeneration(int pop_number)
    {
        Schedule[] newPopulation = new Schedule[pop_size];
        for(int i = 0; i < pop_size; )
        {
            Schedule sch1 = tournament();
            Schedule sch2 = tournament();
            Schedule[] children = crossing_over(sch1, sch2);
            newPopulation[i++] = mutation(children[0]);
            if(i < pop_size)
                newPopulation[i++] = mutation(children[1]);
        }
        population = newPopulation;
        return statistics(pop_number);
    }

    private String statistics(int pop_number)
    {
        double minDuration = Integer.MAX_VALUE;
        double maxDuration = 0;
        double avgDuration = 0;

        for(Schedule sch : population)
        {
            BaseEvaluator evaluator = new DurationEvaluator(sch);
            double duration = evaluator.evaluate();
            if(duration < minDuration)
            {
                minDuration = duration;
            }
            if(duration > maxDuration)
            {
                maxDuration = duration;
            }
            avgDuration += duration;
        }
        return pop_number + ", " + minDuration + "," + maxDuration + "," + avgDuration/pop_size;
    }

    public int getGenerations()
    {
        return generations;
    }
}
