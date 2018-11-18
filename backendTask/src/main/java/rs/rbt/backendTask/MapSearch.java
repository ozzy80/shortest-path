package rs.rbt.backendTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapSearch {
	private List<Point> availableCells;

	public MapSearch(List<Point> availableCells) {
		this.availableCells = availableCells;
	}

	public MinimalPaths findAllMinimalPaths() {
		long processStartTime = System.currentTimeMillis();

		MinimalPaths minimalPaths = new MinimalPaths();
		AStar astar = new AStar(availableCells);
		List<Cell> minPath = astar.findPath(availableCells.size());
		if (minPath != null) {
			addPathToResult(minPath, minimalPaths);
			findAnotherPaths(minPath.size(), minimalPaths);
		}

		long processEndTime = System.currentTimeMillis();
		minimalPaths.setExecution_time_in_ms(processEndTime - processStartTime);

		return minimalPaths;
	}

	private void addPathToResult(List<Cell> minPath, MinimalPaths minimalPaths) {
		Path path = new Path();
		for (int i = minPath.size() - 1; i >= 0; i--) {
			path.addPoints(minPath.get(i).getPosition());
		}

		if (!minimalPaths.getPaths().contains(path)) {
			minimalPaths.addPaths(path);
		}
	}

	private void findAnotherPaths(int minPathLength, MinimalPaths minimalPaths) {
		for (int i = 0; i < minimalPaths.getPaths().size(); i++) {
			Set<Point> pathPointsSet = new HashSet(minimalPaths.getPaths().get(i).getPoints());
			for (Point point : pathPointsSet) {
				List<Point> newAvailableCells = blockPointOnGrid(point);
				AStar astar = new AStar(newAvailableCells);
				List<Cell> minPath = astar.findPath(minPathLength);
				if (minPath != null) {
					addPathToResult(minPath, minimalPaths);
				}
			}
		}
	}

	private List<Point> blockPointOnGrid(Point point) {
		List<Point> newAvailableCells = new ArrayList<>(availableCells);
		newAvailableCells.remove(point);
		return newAvailableCells;
	}

}
