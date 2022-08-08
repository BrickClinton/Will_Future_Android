package com.example.wbw_first.Interfaces;

import com.example.wbw_first.Entities.EArea;

public interface IOnClickListenerGeneric<T> {
    void onItemClickEdit(T obj);
    void onItemClickDelete(T obj);
}
