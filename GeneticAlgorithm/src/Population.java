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
    Schedule[] population;
    int pop_size;
    int cross_prob;
    int mut_prob;

    public Population(int pop_size, int cross_prob, int mut_prob)
    {
        this.pop_size = pop_size;
        population = new Schedule[pop_size];
        this.cross_prob = cross_prob;
        this.mut_prob = mut_prob;
    }

    public void initialize(String definitionFile)
    {
        MSRCPSPIO reader= new MSRCPSPIO();
        Schedule sch = reader.readDefinition(definitionFile);
        for(int i = 0; i < pop_size; i++)
        {
            Schedule sch2 = new Schedule(sch);
            schedule(sch2);
            ScheduleBuilder builder = new ForwardScheduleBuilder(sch2.getSuccesors());
            builder.buildTimestamps(sch2);
//            System.out.println(sch.getTasks()[0] + " + " + sch.getTasks()[0].getResourceId());
            population[i] = sch2;
        }
//        showPopulation();
    }

    public void showPopulation()
    {
        for(int z = 0; z < pop_size; z++)
        {
            Task[] tasks = population[z].getTasks();
            for(Task task : tasks)
            {
                System.out.print(task.getId() + " ");
            }
            System.out.println();
            for(int j = 0; j < tasks.length; j++)
            {
                if(j < 9)
                {
                    System.out.print(tasks[j].getResourceId() + " ");
                }
                else
                {
                    System.out.print(tasks[j].getResourceId() + "  ");
                }
            }
            System.out.println();
            for(int i = 0; i < tasks.length*3 - 9; i++)
            {
                System.out.print("_");
            }
            System.out.println();
        }
    }

    public void schedule(Schedule schedule)
    {
        Random rand = new Random();
        Task[] tasks = schedule.getTasks();
        for(int i = 0; i < tasks.length; i++)
        {
            List<Resource> capRes = schedule.getCapableResources(tasks[i]);
            int random = rand.nextInt(capRes.size());
            tasks[i].setResourceId(capRes.get(random).getId());
        }
    }

    public Schedule[] crossing_over(Schedule sch1, Schedule sch2)//tu będę zwracać tablicę dwóch Scheduli
    {
        //check if there will be a crossing
        Random rand = new Random();
        if(rand.nextDouble() < cross_prob)
        {
            //if yes, then find an index of crossing point

        }



        //return those 2 crossed individuals in an array

        //if no then return 2 parents in an array
    }
}
