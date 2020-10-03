package me.whispered.dynahttploader;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DynamicHTTPLoader {
    private String repoURL;
    private DynamicClassLoader dynamicClassLoader;
    private URLClassLoader baseClassLoader;
    public DynamicHTTPLoader(String repoURL, URLClassLoader baseClassLoader) {
        this.baseClassLoader = baseClassLoader;
        this.repoURL = repoURL;
        this.dynamicClassLoader = new DynamicClassLoader(baseClassLoader.getURLs(),this.repoURL);
    }
    public DynamicClassLoader getClassLoader() {
        return this.dynamicClassLoader;
    }

    public URLClassLoader getBaseClassLoader() {
        return baseClassLoader;
    }
}
