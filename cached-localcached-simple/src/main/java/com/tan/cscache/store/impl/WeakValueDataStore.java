package com.tan.cscache.store.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.tan.cscache.store.DataStore;
import com.tan.cscache.store.StoreAccessException;
import com.tan.cscache.store.ValueHolder;

/**基于引用的淘汰算法
 * @author Administrator
 *
 * @param <K>
 * @param <V>
 */
public class WeakValueDataStore<K, V> implements DataStore<K, V> {
	
	ConcurrentHashMap<K, ValueHolder<V>> map = new ConcurrentHashMap<K, ValueHolder<V>>();

	@Override
	public ValueHolder<V> get(K key) throws StoreAccessException {
		return map.get(key);
	}

	@Override
	public com.tan.cscache.store.DataStore.PutStatus put(K key,V value)
			throws StoreAccessException {
		ValueHolder<V> v = new WeakValueHolder<V>(value);
		map.put(key, v);
		return PutStatus.PUT;
	}

	@Override
	public ValueHolder<V> remove(K key) throws StoreAccessException {
		return map.remove(key);
	}

	@Override
	public void clear() throws StoreAccessException {
		map.clear();
	}

}
