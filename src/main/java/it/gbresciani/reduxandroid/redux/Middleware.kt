package it.gbresciani.reduxandroid.redux

/**
 * Create an [Enhancer] that can apply a list of [Middleware] to the [Store].
 */
fun <S : State, A : Action> applyMiddleware(vararg middleware: Middleware<S, A>): Enhancer<S, A> {

    return { createStore ->
        { reducer, initState ->
            val store = createStore(reducer, initState) as EnhanceableStore<S, A>

            val dispatchChain = middleware
                .map { it(store) }
                .compose()
                .invoke(store.dispatchF)

            store.apply { dispatchF = dispatchChain }
        }
    }
}