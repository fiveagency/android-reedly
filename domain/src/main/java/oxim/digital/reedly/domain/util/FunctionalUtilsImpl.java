package oxim.digital.reedly.domain.util;

import java.lang.ref.WeakReference;
import java.util.Map;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public final class FunctionalUtilsImpl implements FunctionalUtils {

    @Override
    public <T> void executeIfRefNotNull(final WeakReference<T> weakRef, final Action1<T> whenRefNotNull) {
        final T ref = weakRef.get();
        if (ref != null) {
            whenRefNotNull.call(ref);
        }
    }

    @Override
    public <K, V> void executeIfPresent(final Map<K, V> map, final K key, final Action1<V> whenPresent) {
        final V value = map.get(key);
        if (value != null) {
            whenPresent.call(value);
        }
    }

    @Override
    public <K, V> V getOrDefault(final Map<K, V> map, final K key, final V defaultValue) {
        final V value = map.get(key);
        return value == null ? defaultValue : value;
    }

    @Override
    public <T> void ifNotNull(final T target, final Action1<T> ifAction) {
        if (target != null) {
            ifAction.call(target);
        }
    }

    @Override
    public <T> void ifNotNull(final T target, final Action1<T> ifAction, final Action0 elseAction) {
        if (target != null) {
            ifAction.call(target);
        } else {
            elseAction.call();
        }
    }

    @Override
    public <T, R> R ifNotNull(final T target, final Func1<T, R> ifFunction, final Func0<R> elseFunction) {
        if (target != null) {
            return ifFunction.call(target);
        } else {
            return elseFunction.call();
        }
    }
}
