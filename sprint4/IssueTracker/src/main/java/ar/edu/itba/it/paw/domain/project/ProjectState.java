package ar.edu.itba.it.paw.domain.project;

import java.util.Iterator;
import java.util.List;

import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.State;

public class ProjectState {

	private int countOpen, countOnCourse, countFinished, countClosed;
	private float timeOpen, timeOnCourse, timeFinished, timeClosed;

	public void build(List<Issue> issues){
		Iterator<Issue> iterator = issues.iterator();
		
		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(issue.getEstimatedTime() != null){
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
