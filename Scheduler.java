/**
 * Maya Asuero
 * 
 * Scheduler Class
 * Purpose: accepts inputs, prints outputs
 * 
 *
 */

import java.util.Scanner;
class Scheduler{
    public static void main(String[]args){
        Scanner input = new Scanner(System.in);

        // sets PROCESS TABLE
        int num_of_processes = input.nextInt();
        input.nextLine();

        Process process_table[] = new Process[num_of_processes];
        Process[] fcfs_unprocessed = new Process[num_of_processes];
        Process[] sjf_unprocessed = new Process[num_of_processes];


        for(int i=0; i<num_of_processes; i++){

            String name  = input.next();
            int arrival_time = input.nextInt();
            int burst_time = input.nextInt();
            input.nextLine();
            
            process_table[i] = createProcess(name, arrival_time, burst_time);
            fcfs_unprocessed[i] = createProcess(name, arrival_time, burst_time);
            sjf_unprocessed[i] = createProcess(name, arrival_time, burst_time);
        }
        input.close();
        sort(fcfs_unprocessed);
        sort(sjf_unprocessed);
        System.out.println();

        System.out.println();

        /*
         *
         * NON-PREEMPTIVE ALGORITHM
         */
        FCFS new_fcfs = new FCFS(num_of_processes, fcfs_unprocessed);
        Process[] fcfs_processed = new_fcfs.processing();
        
        
        /*
         *
         * PRE-EMPTIVE ALGORITHM
         */
        PreSJF sjf = new PreSJF(num_of_processes, sjf_unprocessed);
        Process[] sjf_processed = sjf.SJFprocessing();
        
        System.out.println("- - - - - - - - - -");

        System.out.println("\nTurnaround time per process");
        System.out.println("\nFCFS:");
        printProcessed(fcfs_processed, num_of_processes);

        System.out.println("Shortest Remaining Time:");
        printProcessed(sjf_processed, num_of_processes);

        System.out.println("\nAverage Turnaround Time");
        double average_fcfs = averageTurnaroundTime(fcfs_processed);
        System.out.print("FCFS: ");
        System.out.printf("%.2f", average_fcfs);

        double average_sjf = averageTurnaroundTime(sjf_processed);
        System.out.print("\nShortest Remaining Time: ");
        System.out.printf("%.2f", average_sjf);
    }

    /**
     * Creates one process object
     * INPUT FORMAT:
     * Name_of_process Arrival_time Burst_time
     */
    private static Process createProcess(String name, int arrival_time, int burst_time){
        Process new_process = new Process(name, arrival_time, burst_time);
        return new_process;
    }

    /**
     * Insertion sort for array of processes
     * @param pt
     */
    private static void sort(Process[] pt){
        for(int i=0; i<pt.length-1; i++){
            for(int j = i+1; j<pt.length; j++){
                if(pt[i].getArrival_time() > pt[j].getArrival_time()){
                    Process temp = pt[i];
                    pt[i] = pt[j];
                    pt[j] = temp;
                }
            }
        }
    }

    private static void printProcessed(Process[] process_table, int num_of_processes){
        for(int i=0; i<process_table.length; i++){
            process_table[i].solveTurnaround();
            System.out.println(process_table[i].getName() + " " + process_table[i].getTurnaround());
        }
    }

    private static double averageTurnaroundTime(Process[] processes){
        double sum = 0;
        for(int i = 0; i<processes.length; i++){
            sum = sum + (double)processes[i].getTurnaround();
        }
        double ave = (double) (sum/processes.length);
        return ave;
    }
}

