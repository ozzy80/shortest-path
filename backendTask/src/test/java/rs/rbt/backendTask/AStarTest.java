package rs.rbt.backendTask;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class AStarTest {
	public static List<Point> createAvailableCells(int width, int height, Point startPoint, Point endPoint) {
		List<Point> points = new ArrayList<>();	
		points.add(startPoint);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				points.add(new Point(i + 1, new String(Character.toChars(j + 'A'))));
			}
		}
		points.add(endPoint);
		return points;
	}

	@Test
	void findPathTest_checkShortestPath() {
		List<Cell> expectedMinimalPath = new ArrayList<>();
		for (int i = 4; i > 0; i--) {
			Cell cell = new Cell(i, "C");
			expectedMinimalPath.add(cell);			
		}
		List<Point> availableCells = createAvailableCells(4, 4, new Point(1, "C"), new Point(4, "C"));
		AStar aStar = new AStar(availableCells);
		
		List<Cell> minPathActual = aStar.findPath(availableCells.size());
		minPathActual.stream().forEach(path -> {
			path.setFinalCost(0);
			path.setHeuristicCost(0);
			path.setParent(null);
		});
		
		assertEquals(expectedMinimalPath, minPathActual);
	}
	
	@Test
	void findPathTest_checkNoExistingPath() {
		List<Point> availableCells = createAvailableCells(3, 4, new Point(1, "C"), new Point(4, "C"));
		availableCells.remove(5);
		availableCells.remove(6);
		availableCells.remove(7);
		availableCells.remove(8);
		AStar aStar = new AStar(availableCells);
		
		assertNull(aStar.findPath(availableCells.size()));
	}
}
