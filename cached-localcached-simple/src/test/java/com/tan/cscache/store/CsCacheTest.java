package com.tan.cscache.store;

import org.junit.Test;

import com.tan.cscache.CsCache;
import com.tan.cscache.store.impl.WeakValueDataStore;

public class CsCacheTest {

	@Test
	public void TestWeakValue() throws InterruptedException, StoreAccessException {
		CsCache<String, User> cache = new CsCache<String, User>(new WeakValueDataStore<String, User>());
		String key = "tan";
		User user = new User();
		user.setName("tan");
		cache.put(key, user);
		user = null;
		System.out.println("Hello " + cache.get(key).getName());
		System.gc();
		Thread.sleep(1000);
		System.out.println("Hello " + cache.get(key));
	}


}
