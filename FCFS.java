/**
 * First Come, First Served
 * Non-preemptive algorithm
 */
public class FCFS {
    private Process[] processes;
    private Process[] queue;
    private Process bursting;
    private int clock;
    private boolean isDone;
    private int next_pt;
    private int nextEmpty_queue;
    private Process ready;

    public FCFS(int num_of_process, Process[] pt){
        this.processes = pt;
        this.queue = new Process[num_of_process+1];
        this.bursting = null;
        this.clock = 0;
        this.isDone = false;
        this.next_pt = 0;
        this.nextEmpty_queue = 0;
        this.ready = null;
    }

    /**
     * conduct fcfs algorithm with processes
     */
    public Process[] processing(){
        String run = "-";
        String ready_name = "-";
        int q_start = 0;

        System.out.println("- - - - - - - - - -");
        System.out.println("Non-preemptive algorithm\n");

        System.out.printf("%-8s %-10s %-8s %-15s\n", "Time", "Running", "Ready", "Queue");
        System.out.printf("%-8s %-10s %-8s %-15s\n", "----", "-------", "-----", "-----");

        while(this.isDone == false){
            
            // if burst time is == 0, then autoset end time to 0 & move to next to queue
            while(this.next_pt < this.processes.length && this.processes[next_pt].getBurst_time() == 0){
                this.processes[next_pt].setEndTime(this.clock-1);
                next_pt++;
            }

            // once arrived, add to queue
            while(next_pt < this.processes.length && this.clock == this.processes[next_pt].getArrival_time()){
                this.queue[nextEmpty_queue] = this.processes[next_pt];
                this.nextEmpty_queue++;
                this.next_pt++;
            }

            // no process bursting, at least 1 queue
            // transfer queued process to bursting
            if(this.isBurstingEmpty() == true && nextEmpty_queue > 0){
                this.bursting = this.queue[0];
                run = this.bursting.getName();
                moveQueued();
                this.nextEmpty_queue--;
            }
        
            // a process is bursting, and burst time is > 0
            // decrement burst time with process
            if(this.isBurstingEmpty() == false && this.bursting.getBurst_time() > 0){
                this.bursting.burst();

                // sets running process or state
                try{
                    run = this.bursting.getName();
                }
                catch(Exception e){
                    run = "-";
                }

                // if burst time becomes zero after last burst, set end time & current bursting is null
                // set ready state or process
                if(this.bursting.getBurst_time() == 0){
                    this.bursting.setEndTime(clock);
                    
                    // set next in queue as ready
                    if(this.nextEmpty_queue > 0){
                        this.ready = this.queue[0];
                        ready_name = this.ready.getName();
                    }
                    else{
                        ready_name = "-";
                    }
                    this.bursting = null;
                }
                else{
                    ready_name = "-";
                }    
            }
            else{
                run = "-";
                ready_name = "-";
            }

            // print processes or states
            // if there is at least 1 in queue
            if(this.nextEmpty_queue > 0){
                if(this.ready == null){
                    q_start = 0;
                    System.out.printf("%-8s %-10s %-8s %-15s\n", clock, run, "-", printQueue(q_start));
                }
                else if(this.queue[1] !=null){
                    q_start = 1;
                    System.out.printf("%-8s %-10s %-8s %-15s\n", clock, run, ready.getName(), printQueue(q_start));
                }
                else{
                    System.out.printf("%-8s %-10s %-8s %-15s\n", clock, run, ready.getName(), "-");
                }
            }
            else{
            // if queue is empty
                System.out.printf("%-8s %-10s %-8s %-15s\n", clock, run, "-", "-");
            }

            if(next_pt == this.processes.length && this.bursting == null && nextEmpty_queue == 0){
                this.isDone = true;
            }

            this.ready = null;
            this.clock++;
        }
        // solving for turnaround is found in scheduler
        return this.processes;
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
        this.queue[nextEmpty_queue] = null;
    }

    private String printQueue(int start){
        String queue = "";
        for(int i = start; i < nextEmpty_queue; i++){
            queue = queue + this.queue[i].getName() + " ";
        }
        return queue;
    }
}
