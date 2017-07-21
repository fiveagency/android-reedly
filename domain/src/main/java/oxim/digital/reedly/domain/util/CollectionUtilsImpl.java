package oxim.digital.reedly.domain.util;

import com.annimon.stream.Optional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import rx.functions.Func1;
import rx.functions.Func2;

public class CollectionUtilsImpl implements CollectionUtils {

    @Override
    public boolean hasData(final Collection collection) {
        return collection != null && collection.size() > 0;
    }

    @Override
    public boolean isEmpty(final Collection collection) {
        return !hasData(collection);
    }

    @Override
    public <T> int getSize(final Collection<T> collection) {
        return isEmpty(collection) ? 0 : collection.size();
    }

    @Override
    public <T, R> List<R> map(List<T> list, Func1<T, R> mapper) {

        final List<R> result = new ArrayList<>(getSize(list));

        for (T item : list) {
            result.add(mapper.call(item));
        }

        return result;
    }

    @Override
    public <T> List<T> filter(List<T> list, Func1<T, Boolean> predicate) {

        final List<T> result = new ArrayList<>();

        for (T item : list) {
            if (predicate.call(item)) {
                result.add(item);
            }
        }

        return result;
    }

    @Override
    public <T, R> R reduce(List<T> list, R startingValue, Func2<R, T, R> operation) {

        R result = startingValue;

        for (T item : list) {
            result = operation.call(result, item);
        }

        return result;
    }

    @Override
    public boolean isEmpty(final Map map) {
        return !hasData(map);
    }

    @Override
    public boolean hasData(final Map map) {
        return map != null && map.size() > 0;
    }

    @Override
    public int getSize(final Map map) {
        return isEmpty(map) ? 0 : map.size();
    }

    @Override
    public <T> Optional<Integer> indexOfFirst(final List<T> list, final Func1<T, Boolean> predicate) {

        for (int i = 0, size = list.size(); i < size; i++) {
            if (predicate.call(list.get(i))) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

    @Override
    public <T> Iterable<T> reverse(final List<T> list) {
        return () -> new Iterator<T>() {

            private final ListIterator<T> listIterator = list.listIterator(list.size());

            @Override
            public boolean hasNext() {
                return listIterator.hasPrevious();
            }

            @Override
            public T next() {
                return listIterator.previous();
            }

            @Override
            public void remove() {
                listIterator.remove();
            }
        };
    }

    @Override
    public <T> Optional<T> find(final Collection<T> collection, final Func1<T, Boolean> predicate) {
        for (final T element : collection) {
            if (predicate.call(element)) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    @Override
    public <T> IterableWithIndex<T> zipWithIndex(final List<T> list) {
        return new IterableWithIndex<T>() {

            private int position = -1;

            @Override
            public int index() {
                return position;
            }

            @Override
            public Iterator<T> iterator() {
                final Iterator<T> listIterator = list.iterator();

                return new Iterator<T>() {

                    @Override
                    public boolean hasNext() {
                        return listIterator.hasNext();
                    }

                    @Override
                    public T next() {
                        position++;
                        return listIterator.next();
                    }

                    @Override
                    public void remove() {
                        listIterator.remove();
                    }
                };
            }
        };
    }
}
