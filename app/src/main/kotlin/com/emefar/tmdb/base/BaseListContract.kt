package com.emefar.tmdb.base

class BaseListContract {


    interface BaseAdapter<T> {
        fun setModelListForAdapter(modelList: List<T>)
        fun addItems(modelList: MutableList<T>)
        fun notifyDataSetChanged()
    }
}
