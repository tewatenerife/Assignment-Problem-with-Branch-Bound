/**
 * @author Teguayco Gutiérrez González
 * @date 18/06/2016
 * 
 * Grado en Ingeniería Informática
 * Escuela Técnica Superior de Ingeniería y Tecnología
 * Universidad de La Laguna
 */

package assignmentproblem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase para representar la formulación del problema
 * @author teguayco
 * Problema de asignación: se desea asignar n tareas
 * a n agentes de tal forma que se minimice el coste 
 * total de realizar todas las tareas.
 */
public class Problem {
	private static final String DEFAULT_PATH_FILES = "./data/";
	private static final int FIRST_LINE_LENGTH = 1;
	private static final int REST_LINE_LENGTH = 3;
	
	private int n;					// Número de agentes/tareas
	private int[][] timesMatrix;	// Matriz de tiempos
	
	/**
	 * Construye el problema a partir de la especificación por fichero
	 * @param fileName
	 */
	public Problem(String fileName) {
		n = 0;
		timesMatrix = null;
		readFromFile(fileName);
	}
	
	/**
	 * Crea el problema a partir de la especificación por fichero
	 * @param fileName
	 */
	private void readFromFile(String fileName) {
		FileReader fr = null;
		BufferedReader br = null;
		String line;
		String[] tokens;
		int nrow = 0, ncol = 0;
		
		if (!fileName.contains(DEFAULT_PATH_FILES)) {
			fileName = DEFAULT_PATH_FILES + fileName;
		}
		
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			line = br.readLine();
			
			while (line != null) {
				// Leemos si la línea no es un comentario (;;)
				if (!line.matches(";;;*.*")) {
					tokens = line.split("\\s+");
					if (tokens.length == FIRST_LINE_LENGTH) {
						setN(Integer.parseInt(tokens[0]));
						timesMatrix = new int[getN()][getN()];
						
					} else if (tokens.length == REST_LINE_LENGTH) {
						nrow = Integer.parseInt(tokens[0]) - 1;
						ncol = Integer.parseInt(tokens[1]) - 1;
						getTimesMatrix()[nrow][ncol] = Integer.parseInt(tokens[2]);
					}
				}
				
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file");
			e.printStackTrace();
			
		} catch (IOException e) {
			System.err.println("IO exception was thrown");
			e.printStackTrace();
		}
	}
	
	/** 
	 * Devuelve el tiempo que tarde el agente agentId en realizar
	 * la tarea task
	 * @param agentId
	 * @param task
	 */
	public int getTimeCost(String agentId, int task) {
		int agentInt = Integer.parseInt(agentId);
		return getTimesMatrix()[agentInt][task];
	}

	/****** Getters and Setters ******/
	
	public String toString() {
		String result = "";
		result += "n = " + getN() + "\n";
		for (int i = 0; i < getN(); i++) {
			for (int j = 0; j < getN(); j++) {
				result += getTimesMatrix()[i][j] + " | ";
			}
			
			result += "\n";
		}
		
		return result;
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[][] getTimesMatrix() {
		return timesMatrix;
	}

	public void setTimesMatrix(int[][] timesMatrix) {
		this.timesMatrix = timesMatrix;
	}
}
