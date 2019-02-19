package it.gbresciani.reduxandroid.redux

fun <T> List<(T) -> T>.compose(): (T) -> T = reduce { acc, i -> { t -> acc(i(t)) } }