package com.fishkees.backend.dataaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class KeyValueStore<K, V> {
	protected Map<K, V> map;
	protected Map<K, V> cachedMap;

	public KeyValueStore(List<V> initialValues) {
		this.map = new HashMap<>();
		for (V value : initialValues) {
			this.map.put(getId(value), value);
		}

		this.cachedMap = ImmutableMap.copyOf(this.map);
	}

	public void put(K key, V value) {
		map.put(key, value);
	}

	public V get(K key) {
		return map.get(key);
	}

	public List<V> all() {
		return Lists.newArrayList(map.values());
	}

	public V remove(K key) {
		return map.remove(key);
	}

	public V update(K key, V value) {
		if (this.map.containsKey(key)) {
			map.put(key, value);
			return value;
		}

		return null;
	};

	public String getNewId() {
		return UUID.randomUUID().toString();
	}

	public void reset() {
		this.map = Maps.newHashMap(cachedMap);
	}

	public abstract K getId(V value);
}
