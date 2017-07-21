package oxim.digital.reedly.domain.util;

import com.annimon.stream.Optional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;
import rx.functions.Func2;

public interface CollectionUtils {

    boolean hasData(Collection collection);

    boolean isEmpty(Collection collection);

    <T> int getSize(Collection<T> collection);

    <T, R> List<R> map(List<T> list, Func1<T, R> mapper);

    <T> List<T> filter(List<T> list, Func1<T, Boolean> predicate);

    <T, R> R reduce(List<T> list, R startingValue, Func2<R, T, R> operation);

    boolean isEmpty(Map map);

    boolean hasData(Map map);

    int getSize(Map map);

    <T> Optional<Integer> indexOfFirst(List<T> list, Func1<T, Boolean> predicate);

    <T> Iterable<T> reverse(List<T> list);

    <T> Optional<T> find(Collection<T> collection, Func1<T, Boolean> predicate);

    <T> IterableWithIndex<T> zipWithIndex(List<T> list);

    interface IterableWithIndex<T> extends Iterable<T> {

        int index();

    }
}
