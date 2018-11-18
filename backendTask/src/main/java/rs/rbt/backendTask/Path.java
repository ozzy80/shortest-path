package rs.rbt.backendTask;

import java.util.ArrayList;
import java.util.List;

public class Path {
	private List<Point> points;

	public Path() {
		points = new ArrayList<>();
	}

	public List<Point> getPoints() {
		return points;
	}

	public void addPoints(Point point) {
		points.add(point);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		return true;
	}

}
