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

	public abstract boolean tick();

	public abstract boolean multiTick(int ticks);

	public boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(boolean isCompleted) {
		Simulation.isCompleted = isCompleted;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public static void setFailureReason(String failureReason) {
		Simulation.failureReason = failureReason;
	}

	public static void addReportEntry(String reportEntry) {
		report.add(reportEntry);
	}

	public String getReport() {
		StringBuilder output = new StringBuilder("");
		for (String reportEntry : report) {
			output.append(reportEntry).append("\n");
		}
		return output.toString();
	}
	
	protected void resetReport() {
		failureReason = "";
		report.clear();
		addReportEntry("Log:");
	}
}