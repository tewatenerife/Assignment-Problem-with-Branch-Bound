/**
 * @author Teguayco Gutiérrez González
 * @date 18/06/2016
 * 
 * Grado en Ingeniería Informática
 * Escuela Técnica Superior de Ingeniería y Tecnología
 * Universidad de La Laguna
 */

package assignmentproblem;

import java.util.ArrayList;
import java.util.Map;

public class TreeNode {
	private static final int INFINITY = (int) 1e6;
	
	private Problem problem;
	private Solution partialSolution;		// Solución parcial
	private int lowerBound;					// Cota inferior
	int k; 									// Nivel del nodo en el árbol

	public TreeNode(Problem problem) {
		this.problem = problem;
		partialSolution = new Solution(problem);
		lowerBound = -1;
		k = -1;
	}
	
	/**
	 * Devuelve la cota inferior para este TreeNode
	 * @return
	 */
	public void calculateLowerBound() {
		int result = 0;
		ArrayList<Integer> assignedTasks = getPartialSolution().getAssignedTasks();
		ArrayList<Integer> assignedAgents = getPartialSolution().getAssignedAgents();
		int agentId, task;
		
		// Sumar los tiempos para las tareas asignadas
		for(Map.Entry<String,Integer> entry : getPartialSolution().getSol().entrySet()) {
			agentId = Integer.parseInt(entry.getKey());
			task = entry.getValue();
			result += getProblem().getTimeCost(String.valueOf(agentId), task);
		}
		
		int minTimeCost = INFINITY;
		// Para las tareas no asignadas, sumar los costes mínimos
		for (int taskIndx = 0; taskIndx < getProblem().getN(); taskIndx++) {
			minTimeCost = INFINITY;
			for (int agentIndx = 0; agentIndx < getProblem().getN(); agentIndx++) {
				if (!assignedAgents.contains(agentIndx) && !assignedTasks.contains(taskIndx)) {
					if (getProblem().getTimeCost(String.valueOf(agentIndx), taskIndx) < minTimeCost) {
						minTimeCost = getProblem().getTimeCost(String.valueOf(agentIndx), taskIndx);
					}
				}
			}
			
			if (minTimeCost != INFINITY) {
				result += minTimeCost;
			}
		}
		
		setLowerBound(result);
	}
	
	/**
	 * Devuelve las tareas que no han sido asignadas en este TreeNode
	 * @return
	 */
	public ArrayList<Integer> getNotAssignedTasks() {
		ArrayList<Integer> assignedTasks = getPartialSolution().getAssignedTasks();
		ArrayList<Integer> notAssignedTasks = new ArrayList<Integer>();
		
		for (int i = 0; i < getProblem().getN(); i++) {
			if (!assignedTasks.contains(i)) {
				notAssignedTasks.add(i);
			}
		}
		
		return notAssignedTasks;
	}

	/****** Getters and Setters ******/
	
	public String toString() {
		String result = "";
		
		result += "\n\nLowerBound: " + getLowerBound() + "\n";
		result += getPartialSolution();
		return result;
	}
	
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Solution getPartialSolution() {
		return partialSolution;
	}

	public void setPartialSolution(Solution partialSolution) {
		this.partialSolution = partialSolution;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}
}
