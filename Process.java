public class Process {
    private String name;
    private int arrival_time;
    private int burst_time;
    private int turnaround;
    private int end_time;

    public Process(){
        this.name = "";
        this.arrival_time = 0;
        this.burst_time = 0;
        this.turnaround = 0;
        this.end_time = 0;
    }


    public Process(String name, int at, int bt) {
        this.name = name;
        this.arrival_time = at;
        this.burst_time = bt;
        this.turnaround = 0;
        this.end_time = 0;
    }

    /**
     * Getter methods
     */
    public String getName() {
        return this.name;
    }

    public int getArrival_time() {
        return this.arrival_time;
    }

    public int getBurst_time() {
        return this.burst_time;
    }

    public int getTurnaround() {
        return this.turnaround;
    }

    public void burst(){
        this.burst_time--;
    }

    public void setEndTime(int end){
        this.end_time = end;
    }

    public int getEndTime(){
        return this.end_time;
    }

    public void solveTurnaround(){
        this.turnaround = (this.end_time - this.arrival_time) + 1;
    }
}
