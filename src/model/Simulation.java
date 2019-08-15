/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author kelly.taylor
 *
 */
public abstract class Simulation {
	static boolean isCompleted;
	static String failureReason;
	static ArrayList<String> report = new ArrayList<String>();

	/**
	 * Adds an entry to the report
	 * 
	 * @param reportEntry Entry to add to the report
	 */
	public static void addReportEntry(String reportEntry) {
		report.add(reportEntry);
	}

	/**
	 * Gets the failure reason of the simulation
	 * 
	 * @return The failure reason of the simulation
	 */
	public String getFailureReason() {
		return failureReason;
	}

	/**
	 * Gets if the simulation is completed
	 * 
	 * @return If the simulation is completed
	 */
	public boolean getIsCompleted() {
		return isCompleted;
	}

	/**
	 * Gets the report of the simulation
	 * 
	 * @return Report of simulation
	 */
	public String getReport() {
		StringBuilder output = new StringBuilder("");
		for (String reportEntry : report) {
			output.append(reportEntry).append("\n");
		}
		return output.toString();
	}

	/**
	 * Sets the value of if the simulation is completed
	 * 
	 * @param isCompleted If the simulation is completed
	 */
	public void setIsCompleted(boolean isCompleted) {
		Simulation.isCompleted = isCompleted;
	}

	/**
	 * Sets the failure reason of the simulation
	 * 
	 * @param failureReason The failure reason of the simulation
	 */
	public static void setFailureReason(String failureReason) {
		Simulation.failureReason = failureReason;
	}

	/**
	 * Runs a single tick of the simulation
	 * 
	 * @return If the tick was successful
	 */
	public abstract boolean tick();

	/**
	 * Runs the simulation for the specified number of ticks
	 * 
	 * @param ticks Number of ticks to run for
	 * @return If the ticks were successful
	 */
	public abstract boolean multiTick(int ticks);

	/**
	 * Resets the report to a blank log
	 */
	protected void resetReport() {
		failureReason = "";
		report.clear();
		addReportEntry("Log:");
	}
}