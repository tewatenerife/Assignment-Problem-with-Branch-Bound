/**
 * @author Teguayco Gutiérrez González
 * @date 18/06/2016
 * 
 * Grado en Ingeniería Informática
 * Escuela Técnica Superior de Ingeniería y Tecnología
 * Universidad de La Laguna
 */

package assignmentproblem;

public class Clock {
	private long start;	
	private long stop;
	
	public Clock() {
		start = 0;
	}
	
	public long getStart() {
		return start;
	}
	
	public void setStart(long start) {
		this.start = start;
	}
	
	public void start() {
		setStart(System.currentTimeMillis());
	}
	
	public double getElapsedTime() {
		return System.currentTimeMillis() - getStart();
	}
}