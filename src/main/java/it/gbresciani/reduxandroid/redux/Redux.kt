package it.gbresciani.reduxandroid.redux

/**
 * Base implementation of a [Store].
 */
internal class BaseStore<S : State, A : Action> internal constructor(
    reducer: Reducer<S, A>,
    initState: S
) : Store<S, A>,
    EnhanceableStore<S, A> {

    private var state: S = initState
    private var destroyed = false
    private val subscribers = mutableListOf<(S) -> Unit>()
    private val destructionSubscribers = mutableListOf<() -> Unit>()
    override var dispatchF: Dispatch<A> = { a ->
        state = reducer.reduce(getState(), a)
        subscribers.forEach { it(getState()) }
        a
    }

    override fun subscribe(callback: (s: S) -> Unit) {
        if (destroyed) return
        subscribers.add(callback)
    }

    override fun dispatch(a: A): A {
        if (destroyed) return a
        return dispatchF(a)
    }

    override fun getState(): S = state

    override fun destroy() {
        destroyed = true
        subscribers.clear()
        destructionSubscribers.forEach { it.invoke() }
        destructionSubscribers.clear()
    }

    override fun onDestroy(callback: () -> Unit) {
        destructionSubscribers.add(callback)
    }
}

/**
 * Create a new [Store]. It accepts a [Reducer], an initial [State] and an optional [Enhancer] function.
 */
fun <S : State, A : Action> createStore(
    reducer: Reducer<S, A>,
    initState: S,
    enhancer: Enhancer<S, A>? = null
): Store<S, A> =
    enhancer
        ?.invoke { r, s -> BaseStore(r, s) }
        ?.invoke(reducer, initState)
        ?: BaseStore(reducer, initState)