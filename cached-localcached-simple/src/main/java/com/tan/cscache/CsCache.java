package com.tan.cscache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tan.cscache.store.DataStore;
import com.tan.cscache.store.StoreAccessException;
import com.tan.cscache.store.ValueHolder;

public class CsCache <K, V>{
	private static Logger logger = LoggerFactory.getLogger(CsCache.class);
	
	DataStore<K, V> dataStore;
	
	public CsCache(final DataStore<K, V> dataStore){
		this.dataStore=dataStore;
	}
	
	public V get(K key){
		ValueHolder<V> valueHolder = null;
		try{
		valueHolder=dataStore.get(key);
		if(valueHolder==null){
			return null;
		}
		}catch(Exception e){
			logger.error("  ");
		}
		return valueHolder.value();
	}

	public void put(K key, V value) throws StoreAccessException{
		dataStore.put(key, value);
	}

	public V remove(K key) throws StoreAccessException{
		ValueHolder<V> valueHolder=dataStore.remove(key);
		if(valueHolder==null){
			return null;
		}
		return valueHolder.value();
	}

	public void clear() throws StoreAccessException{
		dataStore.clear();
	}

}
