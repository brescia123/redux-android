package it.gbresciani.reduxandroid.redux

/**
 * Marker interface defining a generic action
 */
interface Action

/**
 * Marker interface defining a generic state
 */
interface State

/**
 * Interface defining a reducer for a state [S] and for actions [A]
 */
interface Reducer<S : State, A : Action> {

    /**
     * Reduce function
     */
    fun reduce(s: S, a: A): S
}

/**
 * Interface defining a store for a state [S] and for actions [A]
 */
interface Store<S : State, A : Action> {

    /**
     * Subscribe to state changes
     */
    fun subscribe(callback: (s: S) -> Unit)

    /**
     * Get the current state
     */
    fun getState(): S

    /**
     * Dispatch an action [A]
     */
    fun dispatch(a: A): A

    /**
     * Destroy the store, meaning it will cancel all the subscription and will stop to dispatch actions.
     */
    fun destroy()

    /**
     * Subscribe to destroy event
     */
    fun onDestroy(callback: () -> Unit)
}

/**
 * A type defining a function used to create a [Store] given a [Reducer] and an initial [State].
 */
internal typealias StoreCreator<S, A> = (Reducer<S, A>, S) -> Store<S, A>