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
public class StringSwitchBenchmark {

    private char[] chars;
    private String str;

    @Setup
    public void setup() throws Exception {
        chars = new char[] {'z', 'z', 'z', 'z', 'z', 'z', 'z', 'z'};
        str = new String(chars);
    }

    @Benchmark
    public long baseline() {
        String test = new String(chars);
        return test.length();
    }

    @Benchmark
    public long smallStringSwitchWithFreshString() {
        String test = new String(chars);
        switch (test) {
            case "aaaaa":
                return 1;
            case "bbbbbb":
                return 2;
            case "ccccccc":
                return 3;
            case "zzzzzzzz":
                return 4;
            default:
                throw new AssertionError();
        }
    }

    @Benchmark
    public long smallIfElse() {
        String test = new String(chars);
        if ("aaaaa".equals(test)) {
            return 1;
        }
        if ("bbbbbb".equals(test)) {
            return 2;
        }
        if ("ccccccc".equals(test)) {
            return 3;
        }
        if ("zzzzzzzz".equals(test)) {
            return 4;
        }
        throw new AssertionError();
    }

    @Benchmark
    public long bigStringSwitchWithFreshString() {
        String test = new String(chars);
        switch (test) {
            case "aaaaa":
                return 1;
            case "bbbbb":
                return 2;
            case "cccccc":
                return 3;
            case "dddddd":
                return 4;
            case "eeeeeee":
                return 5;
            case "fffffff":
                return 6;
            case "gggggggg":
                return 7;
            case "hhhhhhhh":
                return 8;
            case "iiiiiiiii":
                return 9;
            case "jjjjjjjjj":
                return 10;
            case "kkkkkkkkkk":
                return 11;
            case "llllllllll":
                return 12;
            case "mmmmmmmmmmm":
                return 13;
            case "nnnnnnnnnnn":
                return 14;
            case "oooooooooooo":
                return 15;
            case "zzzzzzzz":
                return 16;
            default:
                throw new AssertionError();
        }
    }

    @Benchmark
    public long bigIfElse() {
        String test = new String(chars);
        if ("aaaaa".equals(test)) {
            return 1;
        }
        if ("bbbbb".equals(test)) {
            return 2;
        }
        if ("cccccc".equals(test)) {
            return 3;
        }
        if ("dddddd".equals(test)) {
            return 4;
        }
        if ("eeeeeee".equals(test)) {
            return 5;
        }
        if ("fffffff".equals(test)) {
            return 6;
        }
        if ("gggggggg".equals(test)) { // same number of characters, more expensive String.equals()
            return 7;
        }
        if ("hhhhhhhh".equals(test)) { // same number of characters, more expensive String.equals()
            return 8;
        }
        if ("iiiiiiiii".equals(test)) {
            return 9;
        }
        if ("jjjjjjjjj".equals(test)) {
            return 10;
        }
        if ("kkkkkkkkkk".equals(test)) {
            return 11;
        }
        if ("llllllllll".equals(test)) {
            return 12;
        }
        if ("mmmmmmmmmmm".equals(test)) {
            return 13;
        }
        if ("nnnnnnnnnnn".equals(test)) {
            return 14;
        }
        if ("oooooooooooo".equals(test)) {
            return 15;
        }
        if ("zzzzzzzz".equals(test)) {
            return 16;
        }
        throw new AssertionError();
    }

    @Benchmark
    public long smallSwitchFirstChar() {
        String test = new String(chars);
        switch (test.charAt(0)) {
            case 'a':
                if ("aaaaa".equals(test)) {
                    return 1;
                }
                break;
            case 'b':
                if ("bbbbbb".equals(test)) {
                    return 2;
                }
                break;
            case 'c':
                if ("ccccccc".equals(test)) {
                    return 3;
                }
                break;
            case 'z':
                if ("zzzzzzzz".equals(test)) {
                    return 4;
                }
                break;
        }
        throw new AssertionError();
    }

    @Benchmark
    public long bigSwitchFirstChar() {
        String test = new String(chars);
        switch (test.charAt(0)) {
            case 'a':
                if ("aaaaa".equals(test)) {
                    return 1;
                }
                break;
            case 'b':
                if ("bbbbb".equals(test)) {
                    return 2;
                }
                break;
            case 'c':
                if ("cccccc".equals(test)) {
                    return 3;
                }
                break;
            case 'd':
                if ("dddddd".equals(test)) {
                    return 4;
                }
                break;
            case 'e':
                if ("eeeeeee".equals(test)) {
                    return 5;
                }
                break;
            case 'f':
                if ("fffffff".equals(test)) {
                    return 6;
                }
                break;
            case 'i':
                if ("iiiiiiiii".equals(test)) {
                    return 9;
                }
                break;
            case 'j':
                if ("jjjjjjjjj".equals(test)) {
                    return 10;
                }
                break;
            case 'k':
                if ("kkkkkkkkkk".equals(test)) {
                    return 11;
                }
                break;
            case 'l':
                if ("llllllllll".equals(test)) {
                    return 12;
                }
                break;
            case 'm':
                if ("mmmmmmmmmmm".equals(test)) {
                    return 13;
                }
                break;
            case 'n':
                if ("nnnnnnnnnnn".equals(test)) {
                    return 14;
                }
                break;
            case 'o':
                if ("oooooooooooo".equals(test)) {
                    return 15;
                }
                break;
            case 'z': // same first char

                if ("zzzzzzzg".equals(test)) { // same number of characters, more
                    // expensive String.equals()
                    return 7;
                }
                if ("zhhhhhhh".equals(test)) { // same number of characters, more
                    // expensive String.equals()
                    return 8;
                }
                if ("zzzzzzzz".equals(test)) {
                    return 16;
                }
                break;
        }

        throw new AssertionError();
    }

}
