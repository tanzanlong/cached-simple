package com.tan.cscache.store.impl;

import java.lang.ref.WeakReference;

import com.tan.cscache.store.ValueHolder;

public class WeakValueHolder<V> implements ValueHolder<V> {

	public WeakValueHolder(V value) {
		super();
		if (null == value) {
			return;
		}
		this.v = new WeakReference<V>(value);
	}

	private WeakReference<V> v;

	@Override
	public V value() {
		return this.v.get();
	}

}
