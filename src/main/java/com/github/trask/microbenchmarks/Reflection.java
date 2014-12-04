/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.trask.microbenchmarks;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class Reflection {

    private Method method;
    private MethodHandle methodHandle;

    @Setup
    public void setup() throws Exception {
        method = String.class.getMethod("toString");
        method.setAccessible(true);

        Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class);
        methodHandle = lookup.findVirtual(String.class, "toString", mt);
    }

    @Benchmark
    public Object baselineDirectCall() throws Throwable {
        return "".toString();
    }

    @Benchmark
    public Object methodInvoke() throws Throwable {
        return method.invoke("");
    }

    @Benchmark
    public Object methodHandleInvoke() throws Throwable {
        return methodHandle.invoke("");
    }

    @Benchmark
    public Object methodHandleInvokeExact() throws Throwable {
        return (String) methodHandle.invokeExact("");
    }
}
