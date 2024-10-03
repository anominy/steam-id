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
 * A Steam URLs utility.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamUrl {

    /**
     * A Steam vanity /id/ URL string.
     */
    @NotNull
    public static final String VANITY = url(USteamDomain.COMMUNITY, USteamEndpoint.ID);

    /**
     * A Steam worldwide /profiles/ URL string.
     */
    @NotNull
    public static final String PROFILE = url(USteamDomain.COMMUNITY, USteamEndpoint.PROFILES);

    /**
     * A Steam /user/ URL string.
     */
    @NotNull
    public static final String USER = url(USteamDomain.COMMUNITY, USteamEndpoint.USER);

    /**
     * A Steam invite /p/ URL string.
     */
    @NotNull
    public static final String INVITE = url(USteamDomain.INVITE, USteamEndpoint.P);

    /**
     * A Steam China /profiles/ URL string.
     */
    @NotNull
    public static final String CHINA = url(USteamDomain.CHINA, USteamEndpoint.PROFILES);

    /**
     * Get an array of all Steam URLs.
     *
     * @return  new array
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    public static String @NotNull [] getValues() {
        return new String[] {
                VANITY,
                PROFILE,
                USER,
                INVITE,
                CHINA
        };
    }

    /**
     * Get an unmodifiable list of all Steam URLs.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull String> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    @NotNull
    private static String url(
            @NotNull
            final String domain,

            @NotNull
            final String endpoint
    ) {
        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Domain mustn't be <empty>");
        }
        if (endpoint.isEmpty()) {
            throw new IllegalArgumentException("Endpoint mustn't be <empty>");
        }

        return "https://" + domain + "/" + endpoint + "/";
    }

    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull String> INSTANCE;

        static {
            final String[] values = USteamUrl.getValues();
            final List<String> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final String value : values) {
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
    private USteamUrl() {
        throw new UnsupportedOperationException();
    }
}
