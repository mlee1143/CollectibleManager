package edu.westga.cs3151.collectiblemanager.view;

import java.util.HashSet;
import java.util.LinkedList;

import edu.westga.cs3151.collectiblemanager.model.Position;

/**
 * The solver class
 * 
 * @author 
 * @version 
 *
 */
public class KnightSolver {
	
	private HashSet<Position> visitedPositions;
	private LinkedList<Position> queue;
	
	/**
	 * Instantiates the list of positions visited and the queue that will
	 * contain the solution
	 * 
	 * @precondition none
	 * @postcondition getVisitedSquares() == visitedSquares && getQueue == queue
	 */
	public KnightSolver() {
		this.visitedPositions = new HashSet<Position>();
		this.queue = new LinkedList<Position>();
	}

	/**
	 * Returns all the visited position in the puzzle 
	 * 
	 * @return the visited Squares
	 */
	public HashSet<Position> getVisitedPositions() {
		return this.visitedPositions;
	}

	/**
	 * Returns the queue with the possible solution
	 * 
	 * @return the queue with the solution
	 */
	public LinkedList<Position> getQueue() {
		return this.queue;
	}
	
	public LinkedList<Position> solver(int x, int y, Position targetPosition) {
		int[] rowPositions = {-2, -2, -1, -1, 1, 1, 2, 2};
		int[] colsPositions = {-1, 1, -2, 2, -2, 2, -1, 1};
		
		this.queue.add(new Position(x, y));
		this.visitedPositions.add(new Position(x, y));
		
		while (!this.queue.isEmpty()) {
			//Position current = this.queue.pollFirst();
			int size = this.queue.size();
			
			for (int i = 0; i < size; i++) {
				Position p = queue.remove();
				System.out.println(p.getRow() + " t " + p.getCol());
				if (p.getRow() == targetPosition.getRow() && p.getCol() == targetPosition.getCol()) {
					this.queue.add(p);
					return this.getQueue();
				}
				for (int j = 0; j < rowPositions.length; j++) {
					int newRow = p.getRow() + rowPositions[j];
					int newCol = p.getCol() + colsPositions[j];
					if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
						Position newPos = new Position(newRow, newCol);
						if (!this.visitedPositions.contains(newPos)) {
							
							this.visitedPositions.add(newPos);
							
							this.queue.add(newPos);
						}
					}
//					System.out.println(newPos.getRow() + ". " + newPos.getCol());
//					System.out.println(targetPosition.getRow() + " : " + targetPosition.getCol());
//					System.out.println(x + " ; " + y);
					
				}
			}
		}
		
		
		return null;
	}

}
