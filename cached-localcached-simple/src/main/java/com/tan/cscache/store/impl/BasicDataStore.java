package com.tan.cscache.store.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.tan.cscache.store.DataStore;
import com.tan.cscache.store.StoreAccessException;
import com.tan.cscache.store.ValueHolder;

public class BasicDataStore<K, V> implements DataStore<K, V> {

	ConcurrentHashMap<K, ValueHolder<V>> map = new ConcurrentHashMap<K, ValueHolder<V>>();

	public ValueHolder<V> get(K key) throws StoreAccessException {
		return map.get(key);
	}

	public PutStatus put(K key, V value) throws StoreAccessException {
		ValueHolder<V> v = new BasicValueHolder<V>(value);
		map.put(key, v);
		return PutStatus.PUT;
	}

	public ValueHolder<V> remove(K key) throws StoreAccessException {
		return map.remove(key);
	}

	public void clear() throws StoreAccessException {
		map.clear();
	}

}
