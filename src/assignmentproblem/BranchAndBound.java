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

public class BranchAndBound {
	private static final int INFINITY = (int) 1e6;
	
	private Problem problem;
	private ArrayList<TreeNode> activeNodes;	// Conjunto de nodos activos
	private int upperBound;						// Cota superior
	private Solution bestSolution;				// Mejor solución
	
	public BranchAndBound(Problem problem) {
		this.problem = problem;
		activeNodes = new ArrayList<TreeNode>();
		upperBound = calculateUpperBound();
		bestSolution = null;
	}
	
	/**
	 * Se utiliza como cota superior aquella que se corresponde
	 * con la diagonal principal de la matriz de tiempos
	 * @return
	 */
	public int calculateUpperBound() {
		int result = 0;
		for (int i = 0; i < getProblem().getN(); i++) {
			result += getProblem().getTimesMatrix()[i][i];
		}
		return result;
	}
	
	/**
	 * Ejecutar algoritmo B&B
	 */
	public void execute() {
		TreeNode root = new TreeNode(getProblem());
		getActiveNodes().add(root);
		
		while (getActiveNodes().size() != 0) {
			// Se ramifica el nodo más prometedor: aquel con menor cota inferior
			int minBound = INFINITY;
			TreeNode nodeWithSmallestLB = null;
			for (int i = 0; i < getActiveNodes().size(); i++) {
				if (getActiveNodes().get(i).getLowerBound() < minBound) {
					minBound = getActiveNodes().get(i).getLowerBound();
					nodeWithSmallestLB = getActiveNodes().get(i);
				}
			}
			
			branch(nodeWithSmallestLB);
		}
	}
	
	/**
	 * Ramifica el nodo node
	 * @param node
	 */
	private void branch(TreeNode node) {
		ArrayList<Integer> notAssignedTasks = node.getNotAssignedTasks();
		
		for (int i = 0; i < notAssignedTasks.size(); i++) {
			TreeNode child = new TreeNode(getProblem());
			child.setK(node.getK() + 1);
			child.setPartialSolution(new Solution(node.getPartialSolution()));
			child.getPartialSolution().assignTaskToAgent(String.valueOf(child.getK()), 
					notAssignedTasks.get(i));
			child.calculateLowerBound();
			
			if (child.getLowerBound() <= getUpperBound()) {
				getActiveNodes().add(child);
				if (child.getK() == getProblem().getN() - 1) {
					if (updateSolution(child.getPartialSolution())) {
						setUpperBound(child.getLowerBound());
						// Podar aquellos nodos con mayor cota inferior que la superior actual
						for (int j = 0; j < getActiveNodes().size(); j++) {
							if (getActiveNodes().get(j).getLowerBound() > getUpperBound()) {
								getActiveNodes().remove(j);
							}
						}
					}
					getActiveNodes().remove(child);
				}
			}
		}
		
		getActiveNodes().remove(node);
	}
	
	/** 
	 * Devuelve true si se actualizó la mejor solución
	 * @param aSol
	 */
	public boolean updateSolution(Solution aSol) {
		if (getBestSolution() == null) {
			setBestSolution(aSol);
			return true;
		} else if (aSol.totalTime() < getBestSolution().totalTime()) {
			setBestSolution(aSol);
			return true;
		}
		return false;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public ArrayList<TreeNode> getActiveNodes() {
		return activeNodes;
	}

	public void setActiveNodes(ArrayList<TreeNode> activeNodes) {
		this.activeNodes = activeNodes;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	public Solution getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(Solution bestSolution) {
		this.bestSolution = bestSolution;
	}

	public static int getInfinity() {
		return INFINITY;
	}
}