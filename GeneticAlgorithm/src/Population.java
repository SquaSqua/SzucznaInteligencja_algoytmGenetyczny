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

    public Population(int pop_size)
    {
        this.pop_size = pop_size;
        population = new Schedule[pop_size];
    }

    public void initialize(String definitionFile)
    {
        MSRCPSPIO reader;

        for(int i = 0; i < pop_size; i++)
        {
            reader = new MSRCPSPIO();
            Schedule sch = reader.readDefinition(definitionFile);
            schedule(sch);
            ScheduleBuilder builder = new ForwardScheduleBuilder(sch.getSuccesors());
            builder.build(sch);
            builder.buildTimestamps(sch);
//            System.out.println(sch.getTasks()[0] + " + " + sch.getTasks()[0].getResourceId());
            population[i] = sch;
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
            for (Resource res : capRes)
            {
                System.out.print(res.getId() + ", ");
            }
            System.out.println("Koniec capRes");
//            System.out.println(capRes);
            int random = rand.nextInt(capRes.size());
            tasks[i].setResourceId(capRes.get(random).getId());
//            System.out.println(random);
//            System.out.println("i: " + i + ", " + tasks[i].getResourceId());
        }
    }


}
