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

/**
 * A Steam invite utility.
 *
 * <p>{@code USteamInvite} is the utility class to make
 * conversions between a Steam account type-32 identifier
 * and a Steam invite code.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class USteamInvite {

    /**
     * A minimum Steam invite code.
     */
    @NotNull
    public static final String MIN_CODE = "c";

    /**
     * A maximum Steam invite code.
     */
    @NotNull
    public static final String MAX_CODE = "wwww-wwww";

    /**
     * A Steam unique account identifier base.
     */
    @NotNull
    public static final String XUID_BASE = "0123456789abcdef";

    /**
     * A Steam invite code base.
     */
    @NotNull
    public static final String CODE_BASE = "bcdfghjkmnpqrtvw";

    /**
     * A Steam invite code delimiter.
     */
    @NotNull
    public static final String CODE_DELIMITER = "-";

    /**
     * Convert a unique Steam account identifier to a Steam invite code
     * or return a default value on failure.
     *
     * <p>Converts a unique account identifier to hex string and replaces characters using two
     * bases in order - {@link #XUID_BASE} and {@link #CODE_BASE}.
     * Then places {@link #CODE_DELIMITER} if the length of the resulting
     * code is greater than three.
     *
     * <p>Wraps {@link #fromXuidNoCheck(int)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam unique account identifier isn't valid.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) defaultValue
     *     USteamInvite.fromXuidOrElse(0, <defaultValue>);
     *
     *     // (String) defaultValue
     *     USteamInvite.fromXuidOrElse(Integer.MAX_VALUE, <defaultValue>);
     *
     *     // (String) "c"
     *     USteamInvite.fromXuidOrElse(1, <defaultValue>);
     *
     *     // (String) "gqkj-gkbr"
     *     USteamInvite.fromXuidOrElse(1266042636, <defaultValue>);
     * }</pre>
     * <hr>
     *
     * @param xuid          Steam unique account identifier to convert, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  Steam invite code or the default value
     */
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

    /**
     * Convert a unique Steam account identifier to a Steam invite code
     * or return a default value on failure.
     *
     * <p>Converts a unique account identifier to hex string and replaces characters using two
     * bases in order - {@link #XUID_BASE} and {@link #CODE_BASE}.
     * Then places {@link #CODE_DELIMITER} if the length of the resulting
     * code is greater than three.
     *
     * <p>Wraps {@link #fromXuidOrNull(Integer)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam unique account identifier isn't valid.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) defaultValue
     *     USteamInvite.fromXuidOrElse(0, <defaultValueSupplier>);
     *
     *     // (String) defaultValue
     *     USteamInvite.fromXuidOrElse(Integer.MAX_VALUE, <defaultValueSupplier>);
     *
     *     // (String) "c"
     *     USteamInvite.fromXuidOrElse(1, <defaultValueSupplier>);
     *
     *     // (String) "gqkj-gkbr"
     *     USteamInvite.fromXuidOrElse(1266042636, <defaultValueSupplier>);
     * }</pre>
     * <hr>
     *
     * @param xuid                  Steam unique account identifier to convert, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  Steam invite code or the default value
     */
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

    /**
     * Convert a unique Steam account identifier to a Steam invite code
     * or return a default value on failure.
     *
     * <p>Converts a unique account identifier to hex string and replaces characters using two
     * bases in order - {@link #XUID_BASE} and {@link #CODE_BASE}.
     * Then places {@link #CODE_DELIMITER} if the length of the resulting
     * code is greater than three.
     *
     * <p>Wraps {@link #fromXuidOrElse(Integer, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam unique account identifier isn't valid.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) null
     *     USteamInvite.fromXuidOrElse(0, <defaultValueSupplier>);
     *
     *     // (String) null
     *     USteamInvite.fromXuidOrElse(Integer.MAX_VALUE, <defaultValueSupplier>);
     *
     *     // (String) "c"
     *     USteamInvite.fromXuidOrElse(1, <defaultValueSupplier>);
     *
     *     // (String) "gqkj-gkbr"
     *     USteamInvite.fromXuidOrElse(1266042636, <defaultValueSupplier>);
     * }</pre>
     * <hr>
     *
     * @param xuid                  Steam unique account identifier to convert, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  Steam invite code or the default value
     */
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

    /**
     * Convert a unique Steam account identifier to a Steam invite code
     * or return an empty string on failure.
     *
     * <p>Converts a unique account identifier to hex string and replaces characters using two
     * bases in order - {@link #XUID_BASE} and {@link #CODE_BASE}.
     * Then places {@link #CODE_DELIMITER} if the length of the resulting
     * code is greater than three.
     *
     * <p>Wraps {@link #fromXuidOrElse(Integer, String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam unique account identifier isn't valid.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) ""
     *     USteamInvite.fromXuidOrEmpty(0);
     *
     *     // (String) ""
     *     USteamInvite.fromXuidOrEmpty(Integer.MAX_VALUE);
     *
     *     // (String) "c"
     *     USteamInvite.fromXuidOrEmpty(1);
     *
     *     // (String) "gqkj-gkbr"
     *     USteamInvite.fromXuidOrEmpty(1266042636);
     * }</pre>
     * <hr>
     *
     * @param xuid  Steam unique account identifier to convert, may be null
     *
     * @return  Steam invite code or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public static String fromXuidOrEmpty(
            @Nullable
            final Integer xuid
    ) {
        return fromXuidOrElse(xuid, UwString.EMPTY);
    }

    /**
     * Convert a unique Steam account identifier to a Steam invite code
     * or return {@code null} on failure.
     *
     * <p>Converts a unique account identifier to hex string and replaces characters using two
     * bases in order - {@link #XUID_BASE} and {@link #CODE_BASE}.
     * Then places {@link #CODE_DELIMITER} if the length of the resulting
     * code is greater than three.
     *
     * <p>Wraps {@link #fromXuidOrElse(Integer, String)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam unique account identifier isn't valid.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (String) null
     *     USteamInvite.fromXuidOrNull(0);
     *
     *     // (String) null
     *     USteamInvite.fromXuidOrNull(Integer.MAX_VALUE);
     *
     *     // (String) "c"
     *     USteamInvite.fromXuidOrNull(1);
     *
     *     // (String) "gqkj-gkbr"
     *     USteamInvite.fromXuidOrNull(1266042636);
     * }</pre>
     * <hr>
     *
     * @param xuid  Steam unique account identifier to convert, may be null
     *
     * @return  Steam invite code or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null; !null -> !null", pure = true)
    public static String fromXuidOrNull(
            @Nullable
            final Integer xuid
    ) {
        return fromXuidOrElse(xuid, (@Nullable String) null);
    }

    /**
     * Convert a unique Steam account identifier to a Steam invite code.
     *
     * <p>Converts a unique account identifier to hex string and replaces characters using two
     * bases in order - {@link #XUID_BASE} and {@link #CODE_BASE}.
     * Then places {@link #CODE_DELIMITER} if the length of the resulting
     * code is greater than three.
     *
     * @param xuid  Steam unique account identifier to convert
     *
     * @return  Steam invite code
     */
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

    /**
     * Convert a Steam invite code to a unique Steam account identifier
     * or return a default value on failure.
     *
     * <p>Removes {@link #CODE_DELIMITER} and replaces characters using two
     * bases in order - {@link #CODE_BASE} and {@link #XUID_BASE}.
     * Then parses the resulting code to an unsigned 64-bit integer.
     *
     * <p>Wraps {@link #toXuidNoCheck(String)}.
     *
     * <p>Possible failure exceptions:
     * <ul>
     *     <li>Steam invite code is {@code null}.</li>
     *     <li>Steam invite code doesn't match w/ {@link USteamRegex#INVITE_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) defaultValue
     *     USteamInvite.toXuidOrElse(null, <defaultValue>);
     *
     *     // (Integer) defaultValue
     *     USteamInvite.toXuidOrElse("", <defaultValue>);
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrElse("c", <defaultValue>);
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrElse("  c  ", <defaultValue>);
     *
     *     // (Integer) 1266042636
     *     USteamInvite.toXuidOrElse("gqkj-gkbr", <defaultValue>);
     * }</pre>
     * <hr>
     *
     * @param code          Steam invite code to convert, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  Steam unique account identifier or the default value
     */
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

    /**
     * Convert a Steam invite code to a unique Steam account identifier
     * or return a default value on failure.
     *
     * <p>Removes {@link #CODE_DELIMITER} and replaces characters using two
     * bases in order - {@link #CODE_BASE} and {@link #XUID_BASE}.
     * Then parses the resulting code to an unsigned 64-bit integer.
     *
     * <p>Wraps {@link #toXuidNoCheck(String)}.
     *
     * <p>Possible failure exceptions:
     * <ul>
     *     <li>Steam invite code is {@code null}.</li>
     *     <li>Steam invite code doesn't match w/ {@link USteamRegex#INVITE_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) defaultValue
     *     USteamInvite.toXuidOrElse(null, <defaultValueSupplier>);
     *
     *     // (Integer) defaultValue
     *     USteamInvite.toXuidOrElse("", <defaultValueSupplier>);
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrElse("c", <defaultValueSupplier>);
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrElse("  c  ", <defaultValueSupplier>);
     *
     *     // (Integer) 1266042636
     *     USteamInvite.toXuidOrElse("gqkj-gkbr", <defaultValueSupplier>);
     * }</pre>
     * <hr>
     *
     * @param code                  Steam invite code to convert, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  Steam unique account identifier or the default value
     */
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

    /**
     * Convert a Steam invite code to a unique Steam account identifier
     * or return a default value on failure.
     *
     * <p>Removes {@link #CODE_DELIMITER} and replaces characters using two
     * bases in order - {@link #CODE_BASE} and {@link #XUID_BASE}.
     * Then parses the resulting code to an unsigned 64-bit integer.
     *
     * <p>Wraps {@link #toXuidNoCheck(String)}.
     *
     * <p>Possible failure exceptions:
     * <ul>
     *     <li>Steam invite code is {@code null}.</li>
     *     <li>Steam invite code doesn't match w/ {@link USteamRegex#INVITE_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) null
     *     USteamInvite.toXuidOrElse(null, <defaultValueSupplier>);
     *
     *     // (Integer) null
     *     USteamInvite.toXuidOrElse("", <defaultValueSupplier>);
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrElse("c", <defaultValueSupplier>);
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrElse("  c  ", <defaultValueSupplier>);
     *
     *     // (Integer) 1266042636
     *     USteamInvite.toXuidOrElse("gqkj-gkbr", <defaultValueSupplier>);
     * }</pre>
     * <hr>
     *
     * @param code                  Steam invite code to convert, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  Steam unique account identifier or the default value
     */
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

    /**
     * Convert a Steam invite code to a unique Steam account identifier
     * or return the base value on failure.
     *
     * <p>Removes {@link #CODE_DELIMITER} and replaces characters using two
     * bases in order - {@link #CODE_BASE} and {@link #XUID_BASE}.
     * Then parses the resulting code to an unsigned 64-bit integer.
     *
     * <p>Wraps {@link #toXuidOrElse(String, Integer)}
     * w/ {@link USteamId#BASE_XUID} as the default value.
     *
     * <p>Possible failure exceptions:
     * <ul>
     *     <li>Steam invite code is {@code null}.</li>
     *     <li>Steam invite code doesn't match w/ {@link USteamRegex#INVITE_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) 0
     *     USteamInvite.toXuidOrBase(null);
     *
     *     // (Integer) 0
     *     USteamInvite.toXuidOrBase("");
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrBase("c");
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrBase("  c  ");
     *
     *     // (Integer) 1266042636
     *     USteamInvite.toXuidOrBase("gqkj-gkbr");
     * }</pre>
     * <hr>
     *
     * @param code  Steam invite code to convert, may be null
     *
     * @return  Steam unique account identifier or the base value
     */
    @Contract(pure = true)
    public static int toXuidOrBase(
            @Nullable
            final String code
    ) {
        return toXuidOrElse(code, USteamId.BASE_XUID);
    }

    /**
     * Convert a Steam invite code to a unique Steam account identifier
     * or return {@code null} on failure.
     *
     * <p>Removes {@link #CODE_DELIMITER} and replaces characters using two
     * bases in order - {@link #CODE_BASE} and {@link #XUID_BASE}.
     * Then parses the resulting code to an unsigned 64-bit integer.
     *
     * <p>Wraps {@link #toXuidOrElse(String, Integer)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure exceptions:
     * <ul>
     *     <li>Steam invite code is {@code null}.</li>
     *     <li>Steam invite code doesn't match w/ {@link USteamRegex#INVITE_CODE}.</li>
     * </ul>
     *
     * <hr>
     * <pre>{@code
     *     // (Integer) null
     *     USteamInvite.toXuidOrNull(null);
     *
     *     // (Integer) null
     *     USteamInvite.toXuidOrNull("");
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrNull("c");
     *
     *     // (Integer) 1
     *     USteamInvite.toXuidOrNull("  c  ");
     *
     *     // (Integer) 1266042636
     *     USteamInvite.toXuidOrNull("gqkj-gkbr");
     * }</pre>
     * <hr>
     *
     * @param code  Steam invite code to convert, may be null
     *
     * @return  Steam unique account identifier or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null; !null -> !null", pure = true)
    public static Integer toXuidOrNull(
            @Nullable
            final String code
    ) {
        return toXuidOrElse(code, (@Nullable Integer) null);
    }

    /**
     * Convert a Steam invite code to a unique Steam account identifier.
     *
     * <p>Removes {@link #CODE_DELIMITER} and replaces characters using two
     * bases in order - {@link #CODE_BASE} and {@link #XUID_BASE}.
     * Then parses the resulting code to an unsigned 64-bit integer.
     *
     * <p>Possible failure exceptions:
     * <ul>
     *     <li>Steam invite code is {@code null}.</li>
     *     <li>Steam invite code doesn't match w/ {@link USteamRegex#INVITE_CODE}.</li>
     * </ul>
     *
     * @param code  Steam invite code to convert, mustn't be null
     *
     * @return  Steam unique account identifier
     *
     * @throws NullPointerException if the provided code is {@code null}
     */
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
