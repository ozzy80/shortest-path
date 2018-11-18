package rs.rbt.backendTask;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class MapSearchTest {

	@Test
	void findAllMinimalPathsTest_checkMultiplePaths() {
		List<Point> availableCells = AStarTest.createAvailableCells(4, 4, new Point(1, "C"), new Point(4, "C"));
		availableCells.remove(5);
		availableCells.remove(5);
		availableCells.remove(9);
		MapSearch mapSearch = new MapSearch(availableCells);
		
		MinimalPaths minimalPaths = mapSearch.findAllMinimalPaths();
		Path expectedFirstPath = new Path();
		expectedFirstPath.addPoints(new Point(1, "C"));
		expectedFirstPath.addPoints(new Point(2, "C"));
		expectedFirstPath.addPoints(new Point(2, "D"));
		expectedFirstPath.addPoints(new Point(3, "D"));
		expectedFirstPath.addPoints(new Point(4, "D"));
		expectedFirstPath.addPoints(new Point(4, "C"));
		
		Path expectedSecondPath = new Path();
		expectedSecondPath.addPoints(new Point(1, "C"));
		expectedSecondPath.addPoints(new Point(1, "D"));
		expectedSecondPath.addPoints(new Point(2, "D"));
		expectedSecondPath.addPoints(new Point(3, "D"));
		expectedSecondPath.addPoints(new Point(4, "D"));
		expectedSecondPath.addPoints(new Point(4, "C"));
		
		assertEquals(expectedFirstPath, minimalPaths.getPaths().get(0));
		assertEquals(expectedSecondPath, minimalPaths.getPaths().get(1));
		assertEquals(2, minimalPaths.getPaths().size());
	}

	
	@Test
	void findAllMinimalPathsTest_checkOnePath() {
		List<Point> availableCells = AStarTest.createAvailableCells(4, 4, new Point(1, "C"), new Point(4, "C"));
		availableCells.remove(5);
		availableCells.remove(6);
		availableCells.remove(6);
		availableCells.remove(11);
		MapSearch mapSearch = new MapSearch(availableCells);
				
		MinimalPaths minimalPaths = mapSearch.findAllMinimalPaths();
		Path expectedPath = new Path();
		expectedPath.addPoints(new Point(1, "C"));
		expectedPath.addPoints(new Point(1, "B"));
		expectedPath.addPoints(new Point(2, "B"));
		expectedPath.addPoints(new Point(3, "B"));
		expectedPath.addPoints(new Point(3, "C"));
		expectedPath.addPoints(new Point(4, "C"));
		
		assertEquals(expectedPath, minimalPaths.getPaths().get(0));
		assertEquals(1, minimalPaths.getPaths().size());
	}
	
	
	@Test
	void findAllMinimalPathsTest_checkNoPaths() {
		List<Point> availableCells = AStarTest.createAvailableCells(4, 4, new Point(1, "C"), new Point(4, "C"));
		availableCells.remove(5);
		availableCells.remove(5);
		availableCells.remove(5);
		availableCells.remove(5);
		MapSearch mapSearch = new MapSearch(availableCells);
				
		MinimalPaths minimalPaths = mapSearch.findAllMinimalPaths();
		
		assertEquals(0, minimalPaths.getPaths().size());
	}
}
