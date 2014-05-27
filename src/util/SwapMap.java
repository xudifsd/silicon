package io;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class SwapMap<K, V> extends MapAdapter<K, V> {
    private String tmpdir = System.getProperty("java.io.tmpdir");
    private static final String prefix = "_LRU_";
    private int lruThreshold = 3;
    private int live = 0;

    private BlockingQueue<K> lru;
    private ConcurrentHashMap<K, V> cacheMap;
    private ConcurrentHashMap<K, String> diskMap;

    public SwapMap(int threshold, String tmpdir){
        this.lruThreshold = threshold;
        this.tmpdir = tmpdir;

        this.lru = new ArrayBlockingQueue<K>(lruThreshold);
        this.cacheMap = new ConcurrentHashMap<>();
        this.diskMap = new ConcurrentHashMap<>();
    }

    @Override
    public V put(K key, V value) {
        synchronized (this) {
            if (cacheMap.containsKey(key)) { // hit
                lru.remove(key);
            } else {
                if (live >= lruThreshold) {
                    victim();
                } else {
                    live++;
                }
            }
            return cache(key, value);
        }
    }

    @Override
    public V get(Object key) {
        synchronized (this) {
            V value = cacheMap.get(key);
            if (value != null) { // hit
                lru.remove(key);
                cache((K) key, value);
            } else {
                if (diskMap.containsKey(key)) {
                    value = load(key);
                    victim();
                    cache((K) key, value);
                }
            }

            return value;
        }
    }

    private void store(K key, V value) {
        if (diskMap.containsKey(key)) return; // because the value never changed and has been swapped out.

        File file = createTempFile();
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream output = new ObjectOutputStream(fos)) {
            output.writeObject(value);
        } catch (IOException e) {
            e.printStackTrace();
        }

        diskMap.put(key, file.getName());
    }

    private V load(Object key) {
        String filename = tmpdir + diskMap.get(key);
        V value = null;
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream input = new ObjectInputStream(fis)) {
            value = (V) input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private void victim() {
        K k = null;
        try {
            k = lru.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        V v = cacheMap.remove(k);
        store(k, v);
    }

    private V cache(K key, V value) {
        try {
            lru.put(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cacheMap.put(key, value);
    }

    private File createTempFile() {
        File file = null;
        try {
            file = File.createTempFile(prefix, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}

abstract class MapAdapter<K, V> implements Map<K, V> {

    private void panic() {
        System.err.println("MapAdapter ONLY support put()/get()!!");
        System.exit(1);
    }

    @Override
    public int size() {
        panic();
        return 0;
    }

    @Override
    public boolean isEmpty() {
        panic();
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        panic();
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        panic();
        return false;
    }

    @Override
    public V remove(Object key) {
        panic();
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        panic();
    }

    @Override
    public void clear() {
        panic();
    }

    @Override
    public Set<K> keySet() {
        panic();
        return null;
    }

    @Override
    public Collection<V> values() {
        panic();
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        panic();
        return null;
    }
}