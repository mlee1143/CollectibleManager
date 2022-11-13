package edu.westga.cs3151.collectiblemanager.viewmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import edu.westga.cs3151.collectiblemanager.model.Position;
import edu.westga.cs3151.collectiblemanager.view.KnightSolver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

/**
 * The Class PuzzleViewModel
 * 
 * @author CS3151
 * @version Spring
 */
public class PuzzleViewModel {

	private final SimpleObjectProperty<Position> knightPositionProperty;
	private final SimpleObjectProperty<Position> targetPositionProperty;
	private Position startPosition;
	private final BooleanProperty wonProperty;
	private final BooleanProperty lostProperty;
	private final SimpleIntegerProperty numberMovesProperty;
	private final ArrayList<Integer> visitedRow;
	private final ArrayList<Integer> vistedCol;

	/**
	 * Instantiates a new puzzle viewmodel for the logic of the knight's travails
	 * puzzle GUI
	 * 
	 * @pre none
	 * @post a new view model representing a knight's travails puzzle instance
	 */
	public PuzzleViewModel() {
		this.knightPositionProperty = new SimpleObjectProperty<Position>();
		this.targetPositionProperty = new SimpleObjectProperty<Position>();
		this.wonProperty = new SimpleBooleanProperty(false);
		this.lostProperty = new SimpleBooleanProperty(false);
		this.startPosition = null;
		this.numberMovesProperty = new SimpleIntegerProperty();
		this.visitedRow = new ArrayList<Integer>();
		this.vistedCol = new ArrayList<Integer>();
	}

	/**
	 * Gets the knight position property.
	 *
	 * @pre none
	 * @post none
	 * @return the knight position property
	 */
	public SimpleObjectProperty<Position> getKnightPositionProperty() {
		return this.knightPositionProperty;
	}

	/**
	 * Gets the target position property.
	 *
	 * @pre none
	 * @post none
	 * @return the knight position property
	 */
	public SimpleObjectProperty<Position> getTargetPositionProperty() {
		return this.targetPositionProperty;
	}

	/**
	 * Gets the won property.
	 *
	 * @pre none
	 * @post none
	 * @return the solved property
	 */
	public BooleanProperty wonProperty() {
		return this.wonProperty;
	}

	/**
	 * Gets the lost property.
	 *
	 * @pre none
	 * @post none
	 * @return the solved property
	 */
	public BooleanProperty lostProperty() {
		return this.lostProperty;
	}

	/**
	 * Gets the number moves property.
	 *
	 * @pre none
	 * @post none
	 * @return the solved property
	 */
	public SimpleIntegerProperty numberMovesProperty() {
		return this.numberMovesProperty;
	}
	
	/**
	 * Gets all the rows visited
	 *
	 * @pre none
	 * @post none
	 * @return visited rows
	 */
	public ArrayList<Integer> getVisitedRow() {
		return this.visitedRow;
	}
	
	/**
	 * Gets all the columns visited 
	 *
	 * @pre none
	 * @post none
	 * @return the columns visited 
	 */
	public ArrayList<Integer> getVisitedCol() {
		return this.vistedCol;
	}

	/**
	 * Moves knight to the position selected by the user if the move is valid.
	 * 
	 * @pre position != null
	 * @post the knight is moved to the specified position if valid && (knight has
	 *       reached the goal in the minimum number moves ->
	 *       this.wonProperty.getValue() == true) && (knight has reached the goal in
	 *       more than the minimum number of moves -> this.lostProperty.getValue()
	 *       == true)
	 * @param position the new position of the knight
	 */
	public void moveKnight(Position position) {
		
		int rowVisited = this.getKnightPositionProperty().getValue().getRow();
		int colvisited = this.getKnightPositionProperty().getValue().getCol();
		if (this.visitedRow.isEmpty() && this.vistedCol.isEmpty()) {
			this.visitedRow.add(rowVisited);
			this.vistedCol.add(colvisited);
		}
		
		int[] rowPositions = {-2, -2, -1, -1, 1, 1, 2, 2};
		int[] colsPositions = {-1, 1, -2, 2, -2, 2, -1, 1};
		
		for (int index = 0; index < rowPositions.length; index++) {
			int newRowCoor = this.getKnightPositionProperty().getValue().getRow() + rowPositions[index];
			int newColsCoor = this.getKnightPositionProperty().getValue().getCol() + colsPositions[index];
			if (position.getRow() == newRowCoor && position.getCol() == newColsCoor) {
				this.knightPositionProperty.setValue(position);
				this.numberMovesProperty.setValue(this.numberMovesProperty.getValue() + 1);
				rowVisited = position.getRow();
				colvisited = position.getCol();
				this.visitedRow.add(rowVisited);
				this.vistedCol.add(colvisited);
			}	
		}
		
		
		System.out.println(this.startPosition.getRow() + ", " + this.startPosition.getCol());
	}

	/**
	 * Undoes the most recent move.
	 * 
	 * @pre none
	 * @post the most recent move is undone
	 */
	public void undo() {
		
		if (this.visitedRow.size() > 1 && this.vistedCol.size() > 1) {
			this.visitedRow.remove(this.visitedRow.size() - 1);
			this.vistedCol.remove(this.vistedCol.size() - 1);
			int lastRow = this.visitedRow.get(this.visitedRow.size() - 1);
			int lastCol = this.vistedCol.get(this.vistedCol.size() - 1);

			Position newPos = new Position(lastRow, lastCol);
			this.knightPositionProperty.setValue(newPos);
			
			this.numberMovesProperty.setValue(this.numberMovesProperty.getValue() - 1);
		}
	}

	/**
	 * Shows a shortest path from the start to the goal position
	 * 
	 * @pre none
	 * @post wonProperty.getValue == true && lostProperty.getValue() == true
	 */
	public void showSolution() {
		KnightSolver newSolver = new KnightSolver();
		LinkedList<Position> solutionPath = new LinkedList<Position>();
		solutionPath = newSolver.solver(this.startPosition.getRow(), this.startPosition.getCol(), this.targetPositionProperty.getValue());
		this.wonProperty.setValue(true);
		this.lostProperty.setValue(true);
		this.tracePath(solutionPath.iterator(), solutionPath.size() - 1);
		
		for (Position current : solutionPath) {
			System.out.println(current.getRow() + ", " + current.getCol());
		}
		System.out.println(this.targetPositionProperty.getValue().getRow() + "//" + this.targetPositionProperty.getValue().getCol());
		
	}

	/**
	 * Instantiates a new knight's travails instance
	 * 
	 * @pre none
	 * @post wonProperty.getValue == false && lostProperty.getValue() == false
	 */
	public void initializeNewPuzzle() {
		Random rand = new Random();
		this.startPosition = new Position(rand.nextInt(Position.MAX_ROWS), rand.nextInt(Position.MAX_COLS));
		this.knightPositionProperty.setValue(this.startPosition);
		Position targetPosition = this.startPosition;
		while (targetPosition.equals(this.startPosition)) {
			targetPosition = new Position(rand.nextInt(Position.MAX_ROWS), rand.nextInt(Position.MAX_COLS));
		}
		this.targetPositionProperty.setValue(targetPosition);
		this.wonProperty.setValue(false);
		this.lostProperty.setValue(false);
		this.numberMovesProperty.setValue(0);
		this.visitedRow.clear();
		this.vistedCol.clear();
	}

	/**
	 * Traces the specified path by setting the knight position property one-by-one
	 * to the positions on the path.
	 * 
	 * @pre the number positions the iterator path returns is equal to pathLength+1
	 * @post none
	 * @param path       the path to be traced
	 * @param pathLength the number moves on the path
	 */
	private void tracePath(Iterator<Position> path, int pathLength) {
		this.numberMovesProperty.setValue(0);
		if (path.hasNext()) {
			this.knightPositionProperty.setValue(path.next());
		}
		if (path.hasNext()) {
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.7), evt -> {
				Position nextPosition = path.next();
				this.knightPositionProperty.setValue(nextPosition);
				this.numberMovesProperty.setValue(this.numberMovesProperty.getValue() + 1);
			}));
			timeline.setCycleCount(pathLength);
			timeline.play();
		}
	}
}
