/*
 * Copyright 2024 anominy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.anominy.steamid;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Steam account instance types utility.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamInstance {

    /**
     * An account instance type - All.
     */
    public static final int ALL = 0;

    /**
     * An account instance type - Desktop.
     */
    public static final int DESKTOP = 1;

    /**
     * An account instance type - Console.
     */
    public static final int CONSOLE = 2;

    /**
     * An account instance type - Web.
     */
    public static final int WEB = 4;

    /**
     * A minimum account instance type.
     *
     * <p>Wraps {@link #ALL}.
     */
    public static final int MIN = ALL;

    /**
     * A maximum account instance type.
     *
     * <p>Wraps {@link #WEB}.
     */
    public static final int MAX = WEB;

    /**
     * Get an array of all account instance types.
     *
     * @return  new array
     */
    @Contract(value = "-> new", pure = true)
    public static int @NotNull [] getValues() {
        return new int[] {
                ALL,
                DESKTOP,
                CONSOLE,
                WEB
        };
    }

    /**
     * Get an unmodifiable list of all account instance types.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Integer> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull Integer> INSTANCE;

        static {
            final int[] values = USteamInstance.getValues();
            final List<Integer> list = new ArrayList<>(values.length);

            for (final int value : values) {
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        public SingletonValueList() {
            throw new UnsupportedOperationException();
        }
    }

    @Contract(value = "-> fail", pure = false)
    private USteamInstance() {
        throw new UnsupportedOperationException();
    }
}
