package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.*;

//for architecture see https://docs.oracle.com/javase/tutorial/ext/basics/spi.html
final class SolutionInfoService extends Base {
    private static SolutionInfoService instance;

    private SolutionInfoService() {

    }

    public static synchronized SolutionInfoService getInstance() {
        if (instance == null) {
            instance = new SolutionInfoService();
        }
        return instance;
    }

    public List<SolutionInfo> getSolutionInfos() {
        List<SolutionInfo> solutions  ;
        ServiceLoader<SolutionInfo> loader;
        // workaroud for play framework, since the overloaded version of ServiceLoader seems to cause some errors there, here we try bot until we get a working classloader
        try{
            loader = ServiceLoader.load(SolutionInfo.class);
            solutions = new ArrayList<>();
            loader.forEach(solutions::add);

        }catch(Throwable e){
            loader = ServiceLoader.load(SolutionInfo.class,SolutionInfo.class.getClassLoader());
            solutions = new ArrayList<>();
            loader.forEach(solutions::add);
        }

        Collections.sort(solutions, Comparator.comparing(SolutionInfo::getName));
        return Collections.unmodifiableList(solutions);
    }
}
