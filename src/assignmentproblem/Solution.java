/**
 * @author Teguayco Gutiérrez González
 * @date 18/06/2016
 * 
 * Grado en Ingeniería Informática
 * Escuela Técnica Superior de Ingeniería y Tecnología
 * Universidad de La Laguna
 */

package assignmentproblem;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Representación de la solución utilizando un TreeMap:
 * el identificador del agente será la clave y la tarea
 * asignada a ese agente será el valor
 * @author teguayco
 */
public class Solution {
	private static final String DEFAULT_PATH_FILES = "./data/";
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private Problem problem;
	private TreeMap<String, Integer> sol;
	
	public Solution(Problem problem) {
		this.problem = problem;
		sol = new TreeMap<String, Integer>();
	}
	
	public Solution(Solution aSol) {
		this.problem = aSol.getProblem();
		this.sol = new TreeMap<String, Integer>(aSol.getSol());
	}
	
	/**
	 * Asigna al agente agentId la tarea task
	 * @param agentId
	 */
	public void assignTaskToAgent(String agentId, Integer task) {
		getSol().put(agentId, task);
	}
	
	/**
	 * Devuelve las tareas que han sido asignadas
	 * (los valores que hay en el TreeMap)
	 * @return
	 */
	public ArrayList<Integer> getAssignedTasks() {
		ArrayList<Integer> assignedTasks = new ArrayList<Integer>(getSol().values());
		return assignedTasks;
	}
	
	/**
	 * Devuelve los agentes a los que se les ha
	 * asignado una tarea (las claves del TreeMap)
	 * @return
	 */
	public ArrayList<Integer> getAssignedAgents() {
		ArrayList<String> assignedAgentsAux = new ArrayList<String>(getSol().keySet());
		ArrayList<Integer> assignedAgents = new ArrayList<Integer>();
		for (int i = 0; i < assignedAgentsAux.size(); i++) {
			assignedAgents.add(Integer.parseInt(assignedAgentsAux.get(i)));
		}
		return assignedAgents;
	}
	
	/**
	 * Devuelve el tamaño de la solución
	 * @return
	 */
	public int size() {
		return getSol().size();
	}
	
	/**
	 * Devuelve el tiempo total para las tareas asignadas a los agentes
	 * en la solución actual
	 * @return
	 */
	public int totalTime() {
		int result = 0;
		for(Map.Entry<String,Integer> entry : getSol().entrySet()) {
			result += getProblem().getTimeCost(entry.getKey(), entry.getValue());
		}
		
		return result;
	}
	
	/**
	 * Exporta la solución con el mismo formato que el 
	 * fichero de especificación de problema a un fichero 
	 * que se crea en el directorio data 
	 * @param fileName
	 */
	public void exportSolutionToFile(String fileName) {
		if (!fileName.contains(DEFAULT_PATH_FILES)) {
			fileName = DEFAULT_PATH_FILES + fileName;
		}
		
		try {
			PrintWriter writer = new PrintWriter(fileName, DEFAULT_ENCODING);
			writer.print(this);
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/****** Getters and Setters ******/
	
	public String toString() {
		String result = "";
		String agentId;
		String taskId;
		int timeCost;
		
		result += getProblem().getN() + "\n";
		for(Map.Entry<String,Integer> entry : getSol().entrySet()) {
			timeCost = getProblem().getTimeCost(entry.getKey(), entry.getValue());
			agentId = Integer.toString(Integer.parseInt(entry.getKey()) + 1);
			taskId = Integer.toString(entry.getValue() + 1);
			result += agentId + " " + taskId + " " + timeCost + "\n";
		}
		
		return result;
	}
	
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public TreeMap<String, Integer> getSol() {
		return sol;
	}

	public void setSol(TreeMap<String, Integer> sol) {
		this.sol = sol;
	}
}
