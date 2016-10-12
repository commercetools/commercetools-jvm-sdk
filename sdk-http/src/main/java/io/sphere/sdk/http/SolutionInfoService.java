package io.sphere.sdk.http;

import java.util.*;

//for architecture see https://docs.oracle.com/javase/tutorial/ext/basics/spi.html
public final class SolutionInfoService extends Base {
    private static SolutionInfoService instance;
    private ServiceLoader<SolutionInfoSupplier> loader;

    private SolutionInfoService() {
        loader = ServiceLoader.load(SolutionInfoSupplier.class);
    }

    public static synchronized SolutionInfoService getInstance() {
        if (instance == null) {
            instance = new SolutionInfoService();
        }
        return instance;
    }

    public List<SolutionInfo> getSolutionInfos() {
        final List<SolutionInfo> solutions = new LinkedList<>();
        loader.iterator().forEachRemaining(supplier -> solutions.add(supplier.get()));
        Collections.sort(solutions, Comparator.comparing(SolutionInfo::getName));
        return Collections.unmodifiableList(solutions);
    }
}
