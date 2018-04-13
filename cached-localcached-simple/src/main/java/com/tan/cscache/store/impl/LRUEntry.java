package com.tan.cscache.store.impl;

import java.util.Map.Entry;

import com.tan.cscache.store.ValueHolder;

public class LRUEntry<K, V extends ValueHolder<?>> implements Entry<K, ValueHolder<?>>{
	
	public LRUEntry<K, ValueHolder<?>> getPre() {
		return pre;
	}



	public void setPre(LRUEntry<K, ValueHolder<?>> pre) {
		this.pre = pre;
	}



	public LRUEntry<K, ValueHolder<?>> getNext() {
		return next;
	}

	
	public void setNext(LRUEntry<K, ValueHolder<?>> next) {
		this.next = next;
	}

	final K key;
	
	ValueHolder<?> v;
	
	
	LRUEntry<K, ValueHolder<?>> pre;
	
	LRUEntry<K, ValueHolder<?>> next;
	
	public LRUEntry(K key,ValueHolder<?> v){
		super();
		this.key=key;
		this.v=v;
	}
	

	@Override
	public K getKey() {
		return this.key;
	}

	@Override
	public ValueHolder<?> getValue() {
		return this.v;
	}

	@Override
	public ValueHolder<?> setValue(ValueHolder<?> value) {
		ValueHolder<?> oldValue = this.v;
	this.v = value;
	return oldValue;
	}

}
