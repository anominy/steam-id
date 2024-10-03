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
import java.util.regex.Pattern;

/**
 * A Steam patterns utility.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamPattern {

    /**
     * Get the Steam ID2 pattern.
     *
     * @return  pattern
     */
    @NotNull
    public static Pattern getId2Pattern() {
        return SingletonId2Pattern.INSTANCE;
    }

    /**
     * Get the Steam ID3 pattern.
     *
     * @return  pattern
     */
    @NotNull
    public static Pattern getId3Pattern() {
        return SingletonId3Pattern.INSTANCE;
    }

    /**
     * Get the Steam ID64 pattern.
     *
     * @return  pattern
     */
    @NotNull
    public static Pattern getId64Pattern() {
        return SingletonId64Pattern.INSTANCE;
    }

    /**
     * Get the Steam vanity ID pattern.
     *
     * @return  pattern
     */
    @NotNull
    public static Pattern getVanityIdPattern() {
        return SingletonVanityIdPattern.INSTANCE;
    }

    /**
     * Get the Steam invite code pattern.
     *
     * @return  pattern
     */
    @NotNull
    public static Pattern getInviteCodePattern() {
        return SingletonInviteCodePattern.INSTANCE;
    }

    /**
     * Get the CS:GO friend code pattern.
     *
     * @return  pattern
     */
    @NotNull
    public static Pattern getCsgoCodePattern() {
        return SingletonCsgoCodePattern.INSTANCE;
    }

    /**
     * Get the profile URL pattern.
     *
     * @return  pattern
     */
    @NotNull
    public static Pattern getProfileUrlPattern() {
        return SingletonProfileUrlPattern.INSTANCE;
    }

    /**
     * Get the user URL pattern.
     *
     * @return  pattern
     */
    @NotNull
    public static Pattern getUserUrlPattern() {
        return SingletonUserUrlPattern.INSTANCE;
    }

    /**
     * Get an array of all Steam patterns.
     *
     * @return  new array
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    public static Pattern @NotNull [] getValues() {
        return new Pattern[] {
                SingletonId2Pattern.INSTANCE,
                SingletonId3Pattern.INSTANCE,
                SingletonId64Pattern.INSTANCE,
                SingletonVanityIdPattern.INSTANCE,
                SingletonInviteCodePattern.INSTANCE,
                SingletonCsgoCodePattern.INSTANCE,
                SingletonProfileUrlPattern.INSTANCE,
                SingletonUserUrlPattern.INSTANCE
        };
    }

    /**
     * Get an unmodifiable list of all Steam patterns.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull Pattern> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    private static final class SingletonId2Pattern {
        @NotNull
        public static final Pattern INSTANCE = Pattern.compile(USteamRegex.ID2);

        @Contract(value = "-> fail", pure = false)
        private SingletonId2Pattern() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonId3Pattern {
        @NotNull
        public static final Pattern INSTANCE = Pattern.compile(USteamRegex.ID3);

        @Contract(value = "-> fail", pure = false)
        private SingletonId3Pattern() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonId64Pattern {
        @NotNull
        public static final Pattern INSTANCE = Pattern.compile(USteamRegex.ID64);

        @Contract(value = "-> fail", pure = false)
        private SingletonId64Pattern() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonVanityIdPattern {
        @NotNull
        public static final Pattern INSTANCE = Pattern.compile(USteamRegex.VANITY_ID);

        @Contract(value = "-> fail", pure = false)
        private SingletonVanityIdPattern() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonInviteCodePattern {
        @NotNull
        public static final Pattern INSTANCE = Pattern.compile(USteamRegex.INVITE_CODE);

        @Contract(value = "-> fail", pure = false)
        private SingletonInviteCodePattern() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonCsgoCodePattern {
        @NotNull
        public static final Pattern INSTANCE = Pattern.compile(USteamRegex.CSGO_CODE);

        @Contract(value = "-> fail", pure = false)
        private SingletonCsgoCodePattern() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonProfileUrlPattern {
        @NotNull
        public static final Pattern INSTANCE = Pattern.compile(USteamRegex.PROFILE_URL);

        @Contract(value = "-> fail", pure = false)
        private SingletonProfileUrlPattern() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonUserUrlPattern {
        @NotNull
        public static final Pattern INSTANCE = Pattern.compile(USteamRegex.USER_URL);

        @Contract(value = "-> fail", pure = false)
        private SingletonUserUrlPattern() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull Pattern> INSTANCE;

        static {
            final Pattern[] values = USteamPattern.getValues();
            final List<Pattern> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final Pattern value : values) {
                //noinspection UseBulkOperation
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonValueList() {
            throw new UnsupportedOperationException();
        }
    }

    @Contract(value = "-> fail", pure = false)
    private USteamPattern() {
        throw new UnsupportedOperationException();
    }
}
