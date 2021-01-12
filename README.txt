Submitted by Maya Asuero
For CSCC 32

Algorithm used:
	Non-preemptive: First come first served
	Preemptive: Shortest remaining job (Preemptive shortest job first)

Input format: 
(for Command Prompt, after compilation)

	number_of_processes
	process_name arrival_time cycle_length

	Example:
	5
	A 0 8
	B 2 5
	C 3 3
	D 5 5
	E 11 3

Expected Output:

	1. Breakdown of non-preemptive process switching
	2. Breakdown of preemptive process switching
	3. Turnaround time for processes using non-preemptive algorithm
	4. Turnaround time for processes using preemptive algorithm
	5. Average TAT for non-preemptive
	6. Average TAT for preemptive