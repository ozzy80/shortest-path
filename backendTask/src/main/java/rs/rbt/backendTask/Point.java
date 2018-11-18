package rs.rbt.backendTask;

public class Point {
	private int row;
	private String col;

	public Point(int row, String col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row - 1;
	}

	public int getCol() {
		return stringToNumberTransform(col);
	}

	public static int stringToNumberTransform(String word) {
		int sum = 0;
		for (int i = 0; i < word.length(); i++) {
			sum += word.charAt(i) - 'A';
		}
		return sum;
	}

	public static String numberToStringTransform(int i) {
		return String.valueOf(Character.toChars(i + 'A'));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((col == null) ? 0 : col.hashCode());
		result = prime * result + row;
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
		Point other = (Point) obj;
		if (col == null) {
			if (other.col != null)
				return false;
		} else if (!col.equals(other.col))
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
