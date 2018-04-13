package com.tan.cscache.store.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.tan.cscache.store.DataStore;
import com.tan.cscache.store.StoreAccessException;
import com.tan.cscache.store.ValueHolder;

/**最近最少使用
 * @author Administrator
 *
 * @param <K>
 * @param <V>
 */
public class LRUDataStore<K, V> implements DataStore<K, V>{
	
	private final int maxsize;
	
	ConcurrentHashMap<K, LRUEntry<K, ValueHolder<?>>> map = new ConcurrentHashMap<K, LRUEntry<K, ValueHolder<?>>>();
	
	private LRUEntry<K, ValueHolder<?>> first;
	private LRUEntry<K, ValueHolder<?>> last;

	
	public LRUDataStore(int maxsize){
		super();
		this.maxsize=maxsize;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValueHolder<V> get(K key) throws StoreAccessException {
		
		LRUEntry<K, ValueHolder<?>> entry=this.getEntry(key);
		
		if(entry==null){
			return null;
		}
		moveToFist(entry);
		return (ValueHolder<V>) entry.getValue();
	}

	@Override
	public com.tan.cscache.store.DataStore.PutStatus put(K key, V value)
			throws StoreAccessException {
		LRUEntry<K, ValueHolder<?>> entry = (LRUEntry<K, ValueHolder<?>>) getEntry(key);
		PutStatus status = PutStatus.NOOP;
		if(entry==null){
			if(map.size()>maxsize){
				removeLast();
			}
			entry = new LRUEntry<K, ValueHolder<?>>(key, new BasicValueHolder<V>(value));
			moveToFist(entry);
			return PutStatus.PUT;
		}else{
			entry.setValue(new BasicValueHolder<V>(value));
			status = PutStatus.UPDATE;
		}
		moveToFist(entry);
		map.put(key, entry);
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValueHolder<V> remove(K key) throws StoreAccessException {
		LRUEntry<K, ValueHolder<?>> entry = getEntry(key);
		if (entry != null) {
			if (entry.getPre() != null)
				entry.getPre().setNext(entry.getNext());
			if (entry.getNext() != null)
				entry.getNext().setPre(entry.getPre());
			if (entry == first)
				first = entry.getNext();
			if (entry == last)
				last = entry.getPre();
		}
		LRUEntry<K, ValueHolder<?>> oldValue = map.remove(key);
		return (ValueHolder<V>) oldValue.getValue();
	}

	@Override
	public void clear() throws StoreAccessException {
		this.map.clear();
		this.first = this.last = null;
	}
	private LRUEntry<K, ValueHolder<?>> getEntry(K key) {
		return map.get(key);
	}
	
	private void moveToFist(LRUEntry<K, ValueHolder<?>> entry){
		if(entry==first){
			return ;
		}
		
		if(entry.getPre()!=null){
			entry.getPre().setNext(entry.getNext());
		}
		if(entry.getNext()!=null){
			entry.getNext().setPre(entry.getPre());
		}
		if(entry==last){
			last=last.getPre();
		}
		if(last==null||first==null){
			last=first=entry;
			return ;
		}
		entry.setNext(first);
		first.setPre(entry);
		first=entry;
		entry.setPre(null);
	}
	
	private void removeLast() {
		if (last != null) {
			last = last.getPre();
			if (last == null)
				first = null;
			else
				last.next = null;
		}
	}
}
