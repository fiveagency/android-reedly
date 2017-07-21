package oxim.digital.reedly.domain.util;

import java.lang.ref.WeakReference;
import java.util.Map;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public interface FunctionalUtils {

    <T> void executeIfRefNotNull(WeakReference<T> weakRef, Action1<T> whenRefNotNull);

    <K, V> void executeIfPresent(Map<K, V> map, K key, Action1<V> whenPresent);

    <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue);

    <T> void ifNotNull(T target, Action1<T> ifAction);

    <T> void ifNotNull(T target, Action1<T> ifAction, Action0 elseAction);

    <T, R> R ifNotNull(T target, Func1<T, R> ifFunction, Func0<R> elseFunction);
}
