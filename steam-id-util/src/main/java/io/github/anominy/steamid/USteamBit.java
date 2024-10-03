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

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamBit {
    public static final int ACCOUNT_ID_OFFSET = 0;
    public static final int ACCOUNT_INSTANCE_OFFSET = 32;
    public static final int ACCOUNT_TYPE_OFFSET = 52;
    public static final int ACCOUNT_UNIVERSE_OFFSET = 56;

    public static final long ACCOUNT_ID_MASK = 0xFFFFFFFFL;
    public static final long ACCOUNT_INSTANCE_MASK = 0x000FFFFFL;
    public static final long ACCOUNT_TYPE_MASK = 0x0000000FL;
    public static final long ACCOUNT_UNIVERSE_MASK = 0x000000FFL;

    @Contract(value = "-> new", pure = true)
    public static int @NotNull [] getOffsets() {
        return new int[] {
                ACCOUNT_ID_OFFSET,
                ACCOUNT_INSTANCE_OFFSET,
                ACCOUNT_TYPE_OFFSET,
                ACCOUNT_UNIVERSE_OFFSET
        };
    }

    @Contract(value = "-> new", pure = true)
    public static long @NotNull [] getMasks() {
        return new long[] {
                ACCOUNT_ID_MASK,
                ACCOUNT_INSTANCE_MASK,
                ACCOUNT_TYPE_MASK,
                ACCOUNT_UNIVERSE_MASK
        };
    }

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Integer> getOffsetList() {
        return SingletonOffsetList.INSTANCE;
    }

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Long> getMaskList() {
        return SingletonMaskList.INSTANCE;
    }

    public static int getAccountXuid(long id64) {
        return get(id64, ACCOUNT_ID_OFFSET, ACCOUNT_ID_MASK);
    }

    public static int getAccountInstance(long id64) {
        return get(id64, ACCOUNT_INSTANCE_OFFSET, ACCOUNT_INSTANCE_MASK);
    }

    public static int getAccountType(long id64) {
        return get(id64, ACCOUNT_TYPE_OFFSET, ACCOUNT_TYPE_MASK);
    }

    public static int getAccountUniverse(long id64) {
        return get(id64, ACCOUNT_UNIVERSE_OFFSET, ACCOUNT_UNIVERSE_MASK);
    }

    private static int get(final long vector, final int offset, final long mask) {
        return (int) ((vector >> offset) & mask);
    }

    private static final class SingletonOffsetList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull Integer> INSTANCE;

        static {
            final int[] values = USteamBit.getOffsets();
            final List<Integer> list = new ArrayList<>(values.length);

            for (final int value : values) {
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonOffsetList() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonMaskList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull Long> INSTANCE;

        static {
            final long[] values = USteamBit.getMasks();
            final List<Long> list = new ArrayList<>(values.length);

            for (final long value : values) {
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonMaskList() {
            throw new UnsupportedOperationException();
        }
    }

    @Contract(value = "-> fail", pure = false)
    private USteamBit() {
        throw new UnsupportedOperationException();
    }
}
