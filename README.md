# DynamicHttpClassLoader

## Example
```java
import me.whispered.dynahttploader.DynamicHTTPLoader;
import java.net.URLClassLoader;

public class Main {
    public static DynamicHTTPLoader dynamicHTTPLoader;
    public static void main(String[] args) throws Exception{
        dynamicHTTPLoader = new DynamicHTTPLoader("http://127.0.0.1:8080", (URLClassLoader) ClassLoader.getSystemClassLoader());
        dynamicHTTPLoader.getClassLoader().enableDebug = true;
        dynamicHTTPLoader.getClassLoader().loadClass("me.whispered.test.Test").getDeclaredConstructors()[0].newInstance();
    }
}
```
