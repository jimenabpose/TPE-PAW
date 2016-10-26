package ar.edu.itba.it.paw.domain;

import java.util.Iterator;
import java.util.List;

public class ProjectState {

	private int countOpen, countOnCourse, countFinished, countClosed;
	private float timeOpen, timeOnCourse, timeFinished, timeClosed;

	public void consult(List<Issue> issues){
		Iterator<Issue> iterator = issues.iterator();
		
		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(issue.getState().equals(State.OPEN)){
				countOpen++;
				timeOpen += issue.getEstimatedTime();
			}
			else if(issue.getState().equals(State.ONCOURSE)){
				countOnCourse++;
				timeOnCourse += issue.getEstimatedTime();
			}
			else if(issue.getState().equals(State.FINISHED)){
				countFinished++;
				timeFinished += issue.getEstimatedTime();
			}
			else{
				countClosed++;
				timeClosed += issue.getEstimatedTime();
			}
		}
	}
	
	public int getCountOpen() {
		return countOpen;
	}
	public int getCountOnCourse() {
		return countOnCourse;
	}
	public int getCountFinished() {
		return countFinished;
	}
	public int getCountClosed() {
		return countClosed;
	}
	public void setTimeOpen(float timeOpen) {
		this.timeOpen = timeOpen;
	}
	public float getTimeOpen() {
		return timeOpen;
	}
	public float getTimeOnCourse() {
		return timeOnCourse;
	}
	public float getTimeFinished() {
		return timeFinished;
	}
	public float getTimeClosed() {
		return timeClosed;
	}
}
