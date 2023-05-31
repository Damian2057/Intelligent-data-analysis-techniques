package p.lodz.pl.algorithm.common;

import java.util.List;

public interface Factory<T> {
    List<T> create(int size);
}
