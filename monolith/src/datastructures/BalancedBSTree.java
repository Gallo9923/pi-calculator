package datastructures;

import java.util.List;

public interface BalancedBSTree<K extends Comparable<K>, V> {
	void add(K key, V value);
	List<V> search(K key);
	void delete(K key);
	boolean contains(K key);
	List<V> autoComplete(String key, int maxSize);
}
