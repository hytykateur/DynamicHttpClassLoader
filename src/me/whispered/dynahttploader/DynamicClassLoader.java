package me.whispered.dynahttploader;

import me.whispered.dynahttploader.asm.IDynamicClassTranformer;
import me.whispered.dynahttploader.utils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class DynamicClassLoader extends URLClassLoader {
    private String repoURL;
    private HashMap<String,Class<?>> cachedClasses = new HashMap<>();
    private ArrayList<String> classesExceptions = new ArrayList<>();
    private ArrayList<IDynamicClassTranformer> transformers = new ArrayList<>();
    public boolean enableDebug = false;
    public DynamicClassLoader(URL[] urls,String repoURL) {
        super(urls);
        this.repoURL = repoURL;
        classesExceptions.add("java.");
        classesExceptions.add("javax.");
        classesExceptions.add("com.oracle.");
        classesExceptions.add("com.sun.");
        classesExceptions.add("jdk.");
    }
    public void addTransformer(IDynamicClassTranformer transformer) {
        transformers.add(transformer);
    }
    @Override
    public InputStream getResourceAsStream(String s) {
        return super.getResourceAsStream(s);
    }

    @Override
    public Class<?> loadClass(String s) throws ClassNotFoundException {
        if (enableDebug) System.out.println("Loading "+s);
        if (cachedClasses.containsKey(s)) {
            if (enableDebug) System.out.println("Reading from cachedClasses "+s);
            return cachedClasses.get(s);
        }
        for (String classException : classesExceptions) {
            if (s.startsWith(classException)) {
                if (enableDebug) System.out.println("Found class exception "+s);
                return super.loadClass(s);
            }
        }
        byte[] classBytes;
        try {
            String url = repoURL+"/"+s.replaceAll("\\.","/")+".class";
            if (enableDebug) System.out.println("Querying "+url);
            classBytes = HttpUtils.getUrlToBytes(new URL(url));
            for (IDynamicClassTranformer tranformer : transformers) {
                tranformer.transform(s,classBytes);
            }
            Class<?> clazz = defineClass(s,classBytes,0,classBytes.length);
            cachedClasses.put(s,clazz);
            return clazz;
        } catch (Exception e) {
            if (enableDebug) {
                e.printStackTrace();
            }
        }

        throw new ClassNotFoundException(s);
    }


    @Override
    protected Class<?> findClass(String s) throws ClassNotFoundException {
        return loadClass(s);
    }

    @Override
    public URL getResource(String s) {
        return super.getResource(s);
    }

    @Override
    public Enumeration<URL> getResources(String s) throws IOException {
        return super.getResources(s);
    }
}
