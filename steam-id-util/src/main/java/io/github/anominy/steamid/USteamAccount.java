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
 * A Steam account types utility.
 *
 * @see <a href="https://developer.valvesoftware.com/wiki/SteamID#Types_of_Steam_Accounts">
 *     Steam Account Types on Valve Developer Community</a>
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamAccount {

    /**
     * An account type ID - Invalid.
     */
    public static final int INVALID_ID = 0;

    /**
     * An account type ID - Individual.
     */
    public static final int INDIVIDUAL_ID = 1;

    /**
     * An account type ID - Multiseat.
     */
    public static final int MULTISEAT_ID = 2;

    /**
     * An account type ID - Game Server.
     */
    public static final int GAME_SERVER_ID = 3;

    /**
     * An account type ID - Anonymous Game Server.
     */
    public static final int ANON_GAME_SERVER_ID = 4;

    /**
     * An account type ID - Pending.
     */
    public static final int PENDING_ID = 5;

    /**
     * An account type ID - Content Server.
     */
    public static final int CONTENT_SERVER_ID = 6;

    /**
     * An account type ID - Clan.
     */
    public static final int CLAN_ID = 7;

    /**
     * An account type ID - Chat.
     */
    public static final int CHAT_ID = 8;

    /**
     * An account type ID - Console User/P2P SuperSeeder.
     */
    public static final int CONSOLE_USER_ID = 9;

    /**
     * An account type ID - Anonymous User.
     */
    public static final int ANON_USER_ID = 10;

    /**
     * An account type ID - Unknown.
     */
    public static final int UNKNOWN_ID = 11;

    /**
     * A base account type ID.
     *
     * <p>Wraps {@link #INVALID_ID}.
     */
    public static final int BASE_ID = INVALID_ID;

    /**
     * A minimum account type ID.
     *
     * <p>Wraps {@link #INDIVIDUAL_ID}.
     */
    public static final int MIN_ID = INDIVIDUAL_ID;

    /**
     * A maximum account type ID.
     *
     * <p>Wraps {@link #UNKNOWN_ID}.
     */
    public static final int MAX_ID = UNKNOWN_ID;

    /**
     * An account type char - Invalid.
     */
    public static final char INVALID_CHAR = 'I';

    /**
     * An account type char - Individual.
     */
    public static final char INDIVIDUAL_CHAR = 'U';

    /**
     * An account type char - Multiseat.
     */
    public static final char MULTISEAT_CHAR = 'M';

    /**
     * An account type char - Game Server.
     */
    public static final char GAME_SERVER_CHAR = 'G';

    /**
     * An account type char - Anonymous Game Server.
     */
    public static final char ANON_GAME_SERVER_CHAR = 'A';

    /**
     * An account type char - Pending.
     */
    public static final char PENDING_CHAR = 'P';

    /**
     * An account type char - Content Server.
     */
    public static final char CONTENT_SERVER_CHAR = 'C';

    /**
     * An account type char - Clan.
     */
    public static final char CLAN_CHAR = 'g';

    /**
     * An account type char - Chat.
     */
    public static final char CHAT_CHAR = 'T';

    /**
     * An account type char - Console User/P2P SuperSeeder.
     *
     * <p>There's no char variant for the {@link #CONSOLE_USER_ID}
     * so this value is equal to {@code 0}.
     */
    public static final char CONSOLE_USER_CHAR = '\0';

    /**
     * An account type char - Anonymous User.
     */
    public static final char ANON_USER_CHAR = 'a';

    /**
     * An account type char - Unknown.
     */
    public static final char UNKNOWN_CHAR = 'i';

    /**
     * An account type char - Lobby Chat.
     */
    public static final char LOBBY_CHAT_CHAR = 'L';

    /**
     * An account type char - Clan Chat.
     */
    public static final char CLAN_CHAT_CHAR = 'c';

    /**
     * A base account type char.
     *
     * <p>Wraps {@link #INVALID_CHAR}.
     */
    public static final char BASE_CHAR = INVALID_CHAR;

    /**
     * A minimum account type char.
     *
     * <p>Wraps {@link #INDIVIDUAL_CHAR}.
     */
    public static final char MIN_CHAR = INDIVIDUAL_CHAR;

    /**
     * A maximum account type char.
     *
     * <p>Wraps {@link #UNKNOWN_CHAR}.
     */
    public static final char MAX_CHAR = UNKNOWN_CHAR;

    /**
     * An account type character base.
     */
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

    /**
     * Get an array of all Steam account type IDs.
     *
     * @return  new array
     */
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

    /**
     * Get an array of all Steam account type chars.
     *
     * @return  new array
     */
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

    /**
     * Get an unmodifiable list of all Steam account type IDs.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Integer> getIdList() {
        return SingletonIdList.INSTANCE;
    }

    /**
     * Get an unmodifiable list of all Steam account type chars.
     *
     * @return  unmodifiable list
     */
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
