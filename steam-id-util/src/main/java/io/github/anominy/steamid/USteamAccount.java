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
public final class USteamAccount {
    public static final int INVALID_ID = 0;
    public static final int INDIVIDUAL_ID = 1;
    public static final int MULTISEAT_ID = 2;
    public static final int GAME_SERVER_ID = 3;
    public static final int ANON_GAME_SERVER_ID = 4;
    public static final int PENDING_ID = 5;
    public static final int CONTENT_SERVER_ID = 6;
    public static final int CLAN_ID = 7;
    public static final int CHAT_ID = 8;
    public static final int CONSOLE_USER_ID = 9;
    public static final int ANON_USER_ID = 10;
    public static final int UNKNOWN_ID = 11;

    public static final int BASE_ID = INVALID_ID;
    public static final int MIN_ID = INDIVIDUAL_ID;
    public static final int MAX_ID = UNKNOWN_ID;

    public static final char INVALID_CHAR = 'I';
    public static final char INDIVIDUAL_CHAR = 'U';
    public static final char MULTISEAT_CHAR = 'M';
    public static final char GAME_SERVER_CHAR = 'G';
    public static final char ANON_GAME_SERVER_CHAR = 'A';
    public static final char PENDING_CHAR = 'P';
    public static final char CONTENT_SERVER_CHAR = 'C';
    public static final char CLAN_CHAR = 'g';
    public static final char CHAT_CHAR = 'T';
    public static final char CONSOLE_USER_CHAR = '\0';
    public static final char ANON_USER_CHAR = 'a';
    public static final char UNKNOWN_CHAR = 'i';
    public static final char LOBBY_CHAT_CHAR = 'L';
    public static final char CLAN_CHAT_CHAR = 'c';

    public static final char BASE_CHAR = INVALID_CHAR;
    public static final char MIN_CHAR = INDIVIDUAL_CHAR;
    public static final char MAX_CHAR = UNKNOWN_CHAR;

    @NotNull
    public static final String CHAR_BASE = ""
            + INVALID_CHAR
            + INDIVIDUAL_CHAR
            + MULTISEAT_CHAR
            + GAME_SERVER_CHAR
            + ANON_GAME_SERVER_CHAR
            + PENDING_CHAR
            + CONTENT_SERVER_CHAR
            + CLAN_CHAR
            + CHAT_CHAR
            + ANON_USER_CHAR
            + UNKNOWN_CHAR
            + LOBBY_CHAT_CHAR
            + CLAN_CHAT_CHAR;

    @Contract(value = "-> new", pure = true)
    public static int @NotNull [] getIds() {
        return new int[] {
                INVALID_ID,
                INDIVIDUAL_ID,
                MULTISEAT_ID,
                GAME_SERVER_ID,
                ANON_GAME_SERVER_ID,
                PENDING_ID,
                CONTENT_SERVER_ID,
                CLAN_ID,
                CHAT_ID,
                CONSOLE_USER_ID,
                ANON_USER_ID,
                UNKNOWN_ID
        };
    }

    @Contract(value = "-> new", pure = false)
    public static char @NotNull [] getChars() {
        return new char[] {
                INVALID_CHAR,
                INDIVIDUAL_CHAR,
                MULTISEAT_CHAR,
                GAME_SERVER_CHAR,
                ANON_GAME_SERVER_CHAR,
                PENDING_CHAR,
                CONTENT_SERVER_CHAR,
                CLAN_CHAR,
                CHAT_CHAR,
                CONSOLE_USER_CHAR,
                ANON_USER_CHAR,
                UNKNOWN_CHAR,
                LOBBY_CHAT_CHAR,
                CLAN_CHAT_CHAR
        };
    }

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Integer> getIdList() {
        return SingletonIdList.INSTANCE;
    }

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Character> getCharList() {
        return SingletonCharList.INSTANCE;
    }

    private static final class SingletonIdList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull Integer> INSTANCE;

        static {
            final int[] values = USteamAccount.getIds();
            final List<Integer> list = new ArrayList<>(values.length);

            for (final int value : values) {
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonIdList() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonCharList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull Character> INSTANCE;

        static {
            final char[] values = USteamAccount.getChars();
            final List<Character> list = new ArrayList<>(values.length);

            for (final char value : values) {
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonCharList() {
            throw new UnsupportedOperationException();
        }
    }

    @Contract(value = "-> fail", pure = false)
    private USteamAccount() {
        throw new UnsupportedOperationException();
    }
}
