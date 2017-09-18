package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.*;

//for architecture see https://docs.oracle.com/javase/tutorial/ext/basics/spi.html
final class SolutionInfoService extends Base {
    private static SolutionInfoService instance;
    private ServiceLoader<SolutionInfo> loader;

    private SolutionInfoService() {
        loader = ServiceLoader.load(SolutionInfo.class,SolutionInfoService.class.getClassLoader());
    }

    public static synchronized SolutionInfoService getInstance() {
        if (instance == null) {
            instance = new SolutionInfoService();
        }
        return instance;
    }

    public List<SolutionInfo> getSolutionInfos() {
        final List<SolutionInfo> solutions = new LinkedList<>();
        loader.iterator().forEachRemaining(solution -> solutions.add(solution));
        Collections.sort(solutions, Comparator.comparing(SolutionInfo::getName));
        return Collections.unmodifiableList(solutions);
    }
}
