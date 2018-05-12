package com.sxito.wcr.service;

public interface DataCache {
    String put(int id, String value);
    void remove(int id);
    String query(int id);
    String query2(int id);
}
