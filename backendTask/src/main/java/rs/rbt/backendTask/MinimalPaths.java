package rs.rbt.backendTask;

import java.util.ArrayList;
import java.util.List;

public class MinimalPaths {
	private long execution_time_in_ms;
	private List<Path> paths;

	public MinimalPaths() {
		paths = new ArrayList<>();
	}

	public long getExecution_time_in_ms() {
		return execution_time_in_ms;
	}

	public void setExecution_time_in_ms(long execution_time_in_ms) {
		this.execution_time_in_ms = execution_time_in_ms;
	}

	public List<Path> getPaths() {
		return paths;
	}

	public void addPaths(Path path) {
		paths.add(path);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (execution_time_in_ms ^ (execution_time_in_ms >>> 32));
		result = prime * result + ((paths == null) ? 0 : paths.hashCode());
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
		MinimalPaths other = (MinimalPaths) obj;
		if (execution_time_in_ms != other.execution_time_in_ms)
			return false;
		if (paths == null) {
			if (other.paths != null)
				return false;
		} else if (!paths.equals(other.paths))
			return false;
		return true;
	}
}
