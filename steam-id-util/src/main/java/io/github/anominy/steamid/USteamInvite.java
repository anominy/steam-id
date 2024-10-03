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

import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.UwString;
import io.github.anominy.uwutils.VoidSupplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamInvite {
    @NotNull
    public static final String MIN_CODE = "c";

    @NotNull
    public static final String MAX_CODE = "wwww-wwww";

    @NotNull
    public static final String XUID_BASE = "0123456789abcdef";

    @NotNull
    public static final String CODE_BASE = "bcdfghjkmnpqrtvw";

    @NotNull
    public static final String CODE_DELIMITER = "-";


    @UnknownNullability
    @Contract(value = "null, _ -> param2; !null, _ -> !null", pure = true)
    public static String fromXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final String defaultValue
    ) {
        if (xuid == null) {
            return defaultValue;
        }

        return fromXuidNoCheck(xuid);
    }

    @UnknownNullability
    @Contract(value = "!null, _ -> !null", pure = false)
    public static String fromXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromXuidOrNull(xuid), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "!null, _ -> !null", pure = false)
    public static String fromXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return fromXuidOrElse(xuid, (Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public static String fromXuidOrEmpty(
            @Nullable
            final Integer xuid
    ) {
        return fromXuidOrElse(xuid, UwString.EMPTY);
    }

    @UnknownNullability
    @Contract(value = "null -> null; !null -> !null", pure = true)
    public static String fromXuidOrNull(
            @Nullable
            final Integer xuid
    ) {
        return fromXuidOrElse(xuid, (@Nullable String) null);
    }

    @NotNull
    @Contract(pure = true)
    public static String fromXuidNoCheck(
            final int xuid
    ) {
        String code = Integer.toHexString(xuid);
        code = UwString.toBaseNoCheck(code, XUID_BASE, CODE_BASE);

        final int index = code.length() >> 1;
        if (index > 1) {
            code = code.substring(0, index)
                    + CODE_DELIMITER
                    + code.substring(index);
        }

        return code;
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2; !null, _ -> !null", pure = true)
    public static Integer toXuidOrElse(
            @Nullable
            final String code,

            @Nullable
            final Integer defaultValue
    ) {
        if (code == null) {
            return defaultValue;
        }

        try {
            return toXuidNoCheck(code);
        } catch (final IllegalArgumentException ignored) {
        }

        return defaultValue;
    }

    @UnknownNullability
    @Contract(value = "!null, _ -> !null", pure = false)
    public static Integer toXuidOrElse(
            @Nullable
            final String code,

            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(toXuidOrNull(code), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null; !null, _ -> !null", pure = false)
    public static Integer toXuidOrElse(
            @Nullable
            final String code,

            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return toXuidOrElse(code, (Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    @Contract(pure = true)
    public static int toXuidOrBase(
            @Nullable
            final String code
    ) {
        return toXuidOrElse(code, USteamId.BASE_XUID);
    }

    @UnknownNullability
    @Contract(value = "null -> null; !null -> !null", pure = true)
    public static Integer toXuidOrNull(
            @Nullable
            final String code
    ) {
        return toXuidOrElse(code, (@Nullable Integer) null);
    }

    @Contract(value = "null -> fail", pure = false)
    public static int toXuidNoCheck(
            @UnknownNullability
            String code
    ) {
        code = code.trim();

        final Pattern pattern = USteamPattern.getInviteCodePattern();
        final Matcher matcher = pattern.matcher(code);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Code doesn't match w/ defined Steam invite code pattern");
        }

        code = code.replace(CODE_DELIMITER, "");
        code = UwString.toBaseNoCheck(code, CODE_BASE, XUID_BASE);

        return Integer.parseInt(code, 16);
    }

    @Contract(value = "-> fail", pure = false)
    private USteamInvite() {
        throw new UnsupportedOperationException();
    }
}
