package com.dawnlight.chronicle_dawnlight.common.utils;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class BaseContext {

    private static final ThreadLocal<UUID> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程的 ID
     * @param id 线程 ID
     */
    public static void setCurrentThreadId(@NonNull UUID id) {
        Objects.requireNonNull(id, "UUID cannot be null");
        threadLocal.set(id);
    }

    /**
     * 获取当前线程的 ID
     * @return 当前线程的 ID，如果未设置则返回 null
     */
    public static UUID getCurrentThreadId() {
        return threadLocal.get();
    }

    /**
     * 移除当前线程的 ID
     */
    public static void removeCurrentThreadId() {
        threadLocal.remove();
    }
}