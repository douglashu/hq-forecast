package com.hq.manny.upd.service.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface ExecutorPools {
	public static final ExecutorService FIXED_EXECUTOR = Executors.newFixedThreadPool(10);
	public static final ExecutorService CACHED_EXECUTOR = Executors.newCachedThreadPool();
	public static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(10);
}
