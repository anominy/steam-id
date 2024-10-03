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
 * A Steam chat flags utility.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamChat {
    private static final int MAGIC = (int) (USteamBit.ACCOUNT_INSTANCE_MASK + 1);

    /**
     * A chat flag - Clan.
     */
    public static final int CLAN_FLAG = MAGIC >> 1;

    /**
     * A chat flag - Lobby.
     */
    public static final int LOBBY_FLAG = MAGIC >> 2;

    /**
     * A chat flag - MM Lobby.
     */
    public static final int MM_LOBBY_FLAG = MAGIC >> 3;

    /**
     * Get an array of all chat flags.
     *
     * @return  new array
     */
    @Contract(value = "-> new", pure = true)
    public static int @NotNull [] getFlags() {
        return new int[] {
                CLAN_FLAG,
                LOBBY_FLAG,
                MM_LOBBY_FLAG
        };
    }

    /**
     * Get an unmodifiable list of all chat flags
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Integer> getFlagList() {
        return SingletonFlagList.INSTANCE;
    }

    private static final class SingletonFlagList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull Integer> INSTANCE;

        static {
            final int[] values = USteamChat.getFlags();
            final List<Integer> list = new ArrayList<>(values.length);

            for (final int value : values) {
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonFlagList() {
            throw new UnsupportedOperationException();
        }
    }

    @Contract(value = "-> fail", pure = false)
    private USteamChat() {
        throw new UnsupportedOperationException();
    }
}
