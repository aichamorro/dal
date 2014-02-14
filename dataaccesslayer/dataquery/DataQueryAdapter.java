package dataaccesslayer.dataquery;

public interface DataQueryAdapter<E> {
	public E objectForQuery(DataQuery query);
}
