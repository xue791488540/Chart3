package com.example.testchart1;



public class Smartband_sleepCalitem {
	public static final int quality_deep=1;
	public static final int quality_light=2; 
	public static final int quality_wake=3;
	
	public Smartband_sleepCalitem(String timeStart, String timeEnd, int sleepQualityAvg) {
		super();
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.sleepQualityAvg = sleepQualityAvg;
	}
	
	private String timeStart;
	private String timeEnd;
	private int sleepQualityAvg;
	
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public int getSleepQualityAvg() {
		return sleepQualityAvg;
	}
	public void setSleepQualityAvg(int sleepQualityAvg) {
		this.sleepQualityAvg = sleepQualityAvg;
	}
	
	
}
