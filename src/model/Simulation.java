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
	boolean isCompleted;
	String failureReason;
	ArrayList<String> report;

	public abstract boolean tick();

	public abstract boolean multiTick(int ticks);

	public boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public void addReportEntry(String reportEntry) {
		report.add(reportEntry);
	}

	public String getReport() {
		StringBuilder output = new StringBuilder("");
		for (String reportEntry : report) {
			output.append(reportEntry).append("\n");
		}
		return output.toString();
	}
}