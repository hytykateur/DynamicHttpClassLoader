package me.whispered.dynahttploader.asm;

public interface IDynamicClassTranformer {
    void transform(String className,byte[] bytes);
}
