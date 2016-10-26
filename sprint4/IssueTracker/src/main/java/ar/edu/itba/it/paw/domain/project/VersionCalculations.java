package ar.edu.itba.it.paw.domain.project;

import java.util.HashMap;
import java.util.List;

import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.Job;
import ar.edu.itba.it.paw.domain.issue.State;

public class VersionCalculations {

	private HashMap<String, Integer> states_count;
	private float TTE = 0;// tiempo total estimado
	private float TTT = 0;// tiempo total trabajado
	private float TEFV = 0;// tiempo estimado para finalizar version
	
	public VersionCalculations(List<Issue> issues){
		/* inicializo el mapa de estados */
		this.states_count = initStatesCount();
		
		this.TTE = 0;
		this.TTT = 0;
		this.TEFV = 0;
		
		String state_aux;
		Integer occurrencies = 0;
		float siae = 0;// suma issues abiertas o en curso
		float sjiae = 0;// suma de jobs de issues abiertas o en curso
		
		for (Issue issue : issues) {
			state_aux = issue.getState().getName();
			occurrencies = states_count.get(state_aux);
			occurrencies++;
			states_count.put(state_aux, occurrencies);

			//TTE = Suma del tiempo estimado de las tareas
			// en cualquier estado
			TTE += issue.getEstimatedTime() == null ? 0 : issue
					.getEstimatedTime();

			//TTT = Suma del tiempo que tomo cada job de
			// cada tarea
			for (Job job : issue.getJobs()) {
				TTT += job.getElapsedTime();
			}

			//TEFV = SUMA [(tiempo de la issue ABIERTAS o EN CURSO) - (tiempo de los jobs de dicha issue)]
			//En caso de que una de las restas de negativa se le asigna cero.
			siae = 0;// suma issues abiertas o en curso
			sjiae = 0;// suma de jobs de issues abiertas o en curso
			if (issue.getState().equals(State.OPEN)
					|| issue.getState().equals(State.ONCOURSE)) {
				siae += issue.getEstimatedTime() == null ? 0 : issue
						.getEstimatedTime();
				for (Job job : issue.getJobs()) {
					sjiae += job.getElapsedTime();
				}
			}
			TEFV += (siae >= sjiae) ? siae - sjiae : 0;
		}
		
	}
	
	public HashMap<String, Integer> getStates_count() {
		return states_count;
	}

	public float getTTE() {
		return TTE;
	}

	public float getTTT() {
		return TTT;
	}

	public float getTEFV() {
		return TEFV;
	}

	private HashMap<String, Integer> initStatesCount(){
		HashMap<String, Integer> states_count = new HashMap<String, Integer>();
		
		State states[] = State.values();
		for (int i = 0; i < states.length; i++) {
			states_count.put(states[i].getName(), 0);
		}
		return states_count;
	}
	
}
