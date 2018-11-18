package rs.rbt.backendTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
	public static final int VERTICAL_AND_HORIZONTAL_COST = 10;
	private Cell[][] grid;
	private PriorityQueue<Cell> openCells;
	private boolean[][] closedCells;
	private Point startPosition;
	private Point endPosition;

	public AStar(List<Point> availableCells) {
		int gridWidth = availableCells.stream().map(Point::getRow).max(Integer::compare).get() + 1;
		int gridHeight = availableCells.stream().map(Point::getCol).max(Integer::compare).get() + 1;

		initializeStartAndEndPoint(availableCells);
		initializeGrid(gridWidth, gridHeight);
		addBlocksOnGrid(availableCells, gridWidth, gridHeight);
		initializeSearchStructure(gridWidth, gridHeight);
	}

	private void initializeStartAndEndPoint(List<Point> availableCells) {
		startPosition = availableCells.get(0);
		endPosition = availableCells.get(availableCells.size() - 1);
	}

	private void initializeGrid(int width, int height) {
		grid = new Cell[width][height];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Cell(i + 1, Point.numberToStringTransform(j));
				grid[i][j].setHeuristicCost(calculateManhattanDistance(i, j));
			}
		}
		grid[startPosition.getRow()][startPosition.getCol()].setFinalCost(0);
	}

	private int calculateManhattanDistance(int i, int j) {
		return Math.abs(i - endPosition.getRow()) + Math.abs(j - endPosition.getCol());
	}

	private void addBlocksOnGrid(List<Point> availableCells, int width, int height) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (!availableCells.contains(new Point(i + 1, Point.numberToStringTransform(j)))) {
					grid[i][j] = null;
				}
			}
		}
	}

	private void initializeSearchStructure(int gridWidth, int gridHeight) {
		closedCells = new boolean[gridWidth][gridHeight];
		openCells = new PriorityQueue<Cell>(Comparator.comparingInt(Cell::getFinalCost));
	}

	public List<Cell> findPath(int minimalPathSize) {
		openCells.add(grid[startPosition.getRow()][startPosition.getCol()]);

		Cell current = openCells.poll();
		int currentPathSize = 0;
		while (current != null) {
			closedCells[current.getRow()][current.getCol()] = true;

			if (isReachEndCell(current) || currentPathSize > minimalPathSize) {
				break;
			}

			findNextCell(current);

			current = openCells.poll();
			currentPathSize++;
		}

		return getPath();
	}

	private boolean isReachEndCell(Cell current) {
		return current.equals(grid[endPosition.getRow()][endPosition.getCol()]);
	}

	private void findNextCell(Cell current) {
		Cell neighbor;
		if (current.getRow() - 1 >= 0) {
			neighbor = grid[current.getRow() - 1][current.getCol()];
			updateCost(current, neighbor, current.getFinalCost() + VERTICAL_AND_HORIZONTAL_COST);
		}
		if (current.getRow() + 1 < grid.length) {
			neighbor = grid[current.getRow() + 1][current.getCol()];
			updateCost(current, neighbor, current.getFinalCost() + VERTICAL_AND_HORIZONTAL_COST);
		}
		if (current.getCol() - 1 >= 0) {
			neighbor = grid[current.getRow()][current.getCol() - 1];
			updateCost(current, neighbor, current.getFinalCost() + VERTICAL_AND_HORIZONTAL_COST);
		}
		if (current.getCol() + 1 < grid[0].length) {
			neighbor = grid[current.getRow()][current.getCol() + 1];
			updateCost(current, neighbor, current.getFinalCost() + VERTICAL_AND_HORIZONTAL_COST);
		}
	}

	private void updateCost(Cell current, Cell neighbor, int cost) {
		if (neighbor == null || closedCells[neighbor.getRow()][neighbor.getCol()]) {
			return;
		}

		int neighborCalculatedFinalCost = neighbor.getHeuristicCost() + cost;
		boolean neighborCellOpen = openCells.contains(neighbor);
		if (!neighborCellOpen || neighborCalculatedFinalCost < neighbor.getFinalCost()) {
			neighbor.setFinalCost(neighborCalculatedFinalCost);
			neighbor.setParent(current);

			if (!neighborCellOpen) {
				openCells.add(neighbor);
			}
		}
	}

	private List<Cell> getPath() {
		List<Cell> minimalPath = null;
		if (closedCells[endPosition.getRow()][endPosition.getCol()]) {
			minimalPath = new ArrayList<>();
			Cell current = grid[endPosition.getRow()][endPosition.getCol()];
			minimalPath.add(current);
			while (current.getParent() != null) {
				current = current.getParent();
				minimalPath.add(current);
			}
		}

		return minimalPath;
	}

}
