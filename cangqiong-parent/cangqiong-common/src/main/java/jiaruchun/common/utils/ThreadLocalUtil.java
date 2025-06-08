package jiaruchun.common.utils;

public class ThreadLocalUtil {
    private static final ThreadLocal<Long> integerThreadLocal = new ThreadLocal<>();

    public static void set(Long id) {
        integerThreadLocal.set(id);
    }

    public static Long get() {
        return integerThreadLocal.get();
    }

    public static void remove() {
        integerThreadLocal.remove();
    }
}