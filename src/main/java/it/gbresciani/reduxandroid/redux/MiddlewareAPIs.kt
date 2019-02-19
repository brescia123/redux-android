package it.gbresciani.reduxandroid.redux

/**
 * Type defining a function that can take a [StoreCreator], enhance the to-be-created [Store] with
 * features, and return the new [StoreCreator].
 */
typealias Enhancer<S, A> = (StoreCreator<S, A>) -> StoreCreator<S, A>

/**
 * Type defining a Middleware function
 */
typealias Middleware<S, A> = (Store<S, A>) -> (Dispatch<A>) -> Dispatch<A>

/**
 * Type defining a dispatch function
 */
internal typealias Dispatch<A> = (A) -> A

/**
 * This internal interface is used only to be able to apply [Middleware] to a [Store]
 */
internal interface EnhanceableStore<S: State, A: Action>: Store<S, A> {
    var dispatchF: Dispatch<A>
}

