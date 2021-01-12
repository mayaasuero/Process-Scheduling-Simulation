/**
 * Implements: shortest job remaining (pre-emqueueive shortest job first)
 * Runs the process with the shortest burst length at all times
 * 
 */

public class PreSJF {
    private Process[] process_table;
    private Process[] queue;
    private Process bursting;
    private int clock;
    private boolean isDone;
    private int next_pt;
    private int nextEmpty_queue;
    private Process ready;

    public PreSJF(int num_of_procces, Process[] pt){
        this.process_table = pt;
        this.queue = new Process[num_of_procces+1];
        this.bursting = null;
        this.clock = -1;
        this.isDone = false;
        this.next_pt = 0;
        this.nextEmpty_queue = 0;
        this.ready = null;
    }

    /**
     * pre-emptive SJF process starts here
     */
    public Process[] SJFprocessing(){
        String run ="-";
        String ready ="-";
        int q_start = 0;

        System.out.println("- - - - - - - - - -");
        System.out.println("\nPre-emptive Algorithm\n");

        System.out.printf("%-8s %-10s %-8s %-15s\n", "Time", "Running", "Ready", "Queue");
        System.out.printf("%-8s %-10s %-8s %-15s\n", "----", "-------", "-----", "-----");

        while(this.isDone == false){
            this.clock++;

            // auto-set processes with burst time == 0 to end time = 0
            while(this.next_pt < this.process_table.length && this.process_table[next_pt].getBurst_time() == 0){
                this.process_table[next_pt].setEndTime(this.clock-1);
                this.next_pt++;
            }


            // add to queue as process arrives
            while(this.next_pt < this.process_table.length && this.clock == this.process_table[next_pt].getArrival_time()){
                this.queue[nextEmpty_queue] = this.process_table[next_pt];
                this.next_pt++;
                this.nextEmpty_queue++;
            }

            // sort if queue has at least 2 processes
            if (this.nextEmpty_queue > 1){
                sortQueue();
            }

            // picks a process to burst
            if(this.isBurstingEmpty() == true && this.nextEmpty_queue > 0){
                //checks if queue is not empty and no process is bursting, then start a process
                this.bursting = this.queue[0];
                run = this.bursting.getName();
                moveQueued();
                this.nextEmpty_queue--;               
            }

            // bursting process (time)--
            if(this.isBurstingEmpty() == false && this.bursting.getBurst_time() > 0){

                // check if bursting has less burst_time than queue[0]
                if(this.nextEmpty_queue > 0 && this.bursting.getBurst_time() > this.queue[0].getBurst_time()){
                    Process temp = this.bursting;
                    this.bursting = this.queue[0];
                    this.queue[0] = temp;

                    sortQueue();
                }

                this.bursting.burst();

                try{
                    run = this.bursting.getName();
                }
                catch(Exception e){
                    run = "-";
                }
            }

            // assigns ready process or state
            if(this.bursting != null && this.queue[0] != null && this.bursting.getBurst_time()==0 && this.bursting.getBurst_time() < this.queue[0].getBurst_time()){
                this.ready = this.queue[0];
            }

            // prints processes & state
            if(this.nextEmpty_queue > 0){
                if(this.ready == null){
                    q_start = 0;
                    System.out.printf("%-8s %-10s %-8s %-15s\n", clock, run, "-", printQueue(q_start));
                }
                else if(this.queue[1] !=null ){
                    q_start = 1;
                    System.out.printf("%-8s %-10s %-8s %-15s\n", clock, run, this.ready.getName(), printQueue(q_start));
                }
                else{
                    System.out.printf("%-8s %-10s %-8s %-15s\n", clock, run, this.ready.getName(), "-");
                }
            }
            else{
                System.out.printf("%-8s %-10s %-8s %-15s\n", clock, run, "-", "-");
            }

            this.ready = null;

            // when bursting process has ended
            if(this.isBurstingEmpty() == false && this.bursting.getBurst_time() == 0){
                this.bursting.setEndTime(this.clock);
                this.bursting = null;
                run = "-";
            }

            if(this.next_pt == process_table.length && this.bursting == null && this.nextEmpty_queue == 0){
                this.isDone = true;
            }
        }
        return process_table;
    }

     /**
     * Checks if a process is bursting or running
     * @return true if a process is running, false if nothing is running
     */
    private boolean isBurstingEmpty(){
        if(this.bursting == null){
            return true;
        }
        else{
            return false;
        }
    }

    private void moveQueued(){
        for(int i = 0; i < queue.length-1; i++){
            this.queue[i] = this.queue[i+1];
        }
        this.queue[this.nextEmpty_queue] = null;
    }
    
    public Process[] getProcess_Table(){
        return this.process_table;
    }

    /**
     * Insertion sort for queue and places the process with shortest burst time first
     */
    private void sortQueue(){
        for(int i=0; i<this.nextEmpty_queue; i++){
            for(int j = i+1; j<this.nextEmpty_queue; j++){
                if(this.queue[i].getBurst_time() > this.queue[j].getBurst_time()){
                    Process temp = this.queue[i];
                    this.queue[i] = this.queue[j];
                    this.queue[j] = temp;
                }
            }
        }
    }

    private String printQueue(int start){
        String queue = "";
        for(int i = start; i < nextEmpty_queue; i++){
            queue = queue + this.queue[i].getName() + " ";
        }
        return queue;
    }
}
