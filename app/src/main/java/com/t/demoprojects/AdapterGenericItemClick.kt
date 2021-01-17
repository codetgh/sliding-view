package com.t.demoprojects

interface AdapterGenericItemClick<T> {
    fun onAdapterItemClickCallback(position:Int, adapterItem:T)
}