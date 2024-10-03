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

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Steam ID representation.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam", "SynchronizeOnNonFinalField", "MethodDoesntCallSuperMethod", "BooleanMethodIsAlwaysInverted"})
public final class SteamId implements Serializable, Cloneable {

    /**
     * An attribute name of this account type-32 identifier.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_XUID = "xuid";

    /**
     * An attribute name of this account universe type.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_UNIVERSE = "universe";

    /**
     * An attribute name of this account instance type.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_INSTANCE = "instance";

    /**
     * An attribute name of this account type.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_ACCOUNT = "account";

    /**
     * An account type-32 identifier.
     */
    @UnknownNullability
    private final Integer xuid;

    /**
     * An account universe type enum.
     */
    @UnknownNullability
    private final ESteamUniverse universe;

    /**
     * An account instance type enum.
     */
    @UnknownNullability
    private final ESteamInstance instance;

    /**
     * An account type enum.
     */
    @UnknownNullability
    private final ESteamAccount account;

    /**
     * A cache of the validity check.
     */
    @UnknownNullability
    private transient volatile Boolean isValidCache;

    /**
     * A cache of the conversion to a static account key.
     */
    @UnknownNullability
    private transient volatile Long staticKeyCache;

    /**
     * A cache of the conversion to an account type-64 identifier.
     */
    @UnknownNullability
    private transient volatile Long id64Cache;

    /**
     * A cache of the conversion to an account type-2 identifier.
     */
    @UnknownNullability
    private transient volatile String id2Cache;

    /**
     * A cache of the conversion to an account type-3 identifier.
     */
    @UnknownNullability
    private transient volatile String id3Cache;

    /**
     * A cache of the conversion to a Steam invite code.
     */
    @UnknownNullability
    private transient volatile String inviteCodeCache;

    /**
     * A cache of the conversion to a CS:GO friend code.
     */
    @UnknownNullability
    private transient volatile String csgoCodeCache;

    /**
     * A cache of the conversion to a /profiles/%id-64% URL.
     */
    @UnknownNullability
    private transient volatile String id64UrlCache;

    /**
     * A cache of the conversion to a /profiles/%id-3% URL.
     */
    @UnknownNullability
    private transient volatile String id3UrlCache;

    /**
     * A cache of the conversion to a /user/%invite-code% URL.
     */
    @UnknownNullability
    private transient volatile String userUrlCache;

    /**
     * A cache of the conversion to a /p/%invite-code% URL.
     */
    @UnknownNullability
    private transient volatile String inviteUrlCache;

    /**
     * A cache of the conversion to a China /profiles/%id-64% URL.
     */
    @UnknownNullability
    private transient volatile String chinaUrlCache;

    /**
     * A cache of the conversion to a hash code.
     */
    @UnknownNullability
    private transient volatile Integer hashCodeCache;

    /**
     * A cache of the conversion to a string.
     */
    @UnknownNullability
    private transient volatile String stringCache;

    /**
     * A {@link #isValidCache} mutex.
     */
    @UnknownNullability
    private transient Object isValidCacheMutex;

    /**
     * A {@link #staticKeyCache} mutex.
     */
    @UnknownNullability
    private transient Object staticKeyCacheMutex;

    /**
     * An {@link #id64Cache} mutex.
     */
    @UnknownNullability
    private transient Object id64CacheMutex;

    /**
     * An {@link #id2Cache} mutex.
     */
    @UnknownNullability
    private transient Object id2CacheMutex;

    /**
     * An {@link #id3Cache} mutex.
     */
    @UnknownNullability
    private transient Object id3CacheMutex;

    /**
     * An {@link #inviteCodeCache} mutex.
     */
    @UnknownNullability
    private transient Object inviteCodeCacheMutex;

    /**
     * A {@link #csgoCodeCache} mutex.
     */
    @UnknownNullability
    private transient Object csgoCodeCacheMutex;

    /**
     * An {@link #id64UrlCache} mutex.
     */
    @UnknownNullability
    private transient Object id64UrlCacheMutex;

    /**
     * An {@link #id3UrlCache} mutex.
     */
    @UnknownNullability
    private transient Object id3UrlCacheMutex;

    /**
     *  A {@link #userUrlCache} mutex.
     */
    @UnknownNullability
    private transient Object userUrlCacheMutex;

    /**
     * An {@link #inviteUrlCache} mutex.
     */
    @UnknownNullability
    private transient Object inviteUrlCacheMutex;

    /**
     * A {@link #chinaUrlCache} mutex.
     */
    @UnknownNullability
    private transient Object chinaUrlCacheMutex;

    /**
     * A {@link #hashCodeCache} mutex.
     */
    @UnknownNullability
    private transient Object hashCodeCacheMutex;

    /**
     * A {@link #stringCache} mutex.
     */
    @UnknownNullability
    private transient Object stringCacheMutex;

    /**
     * Initialize this mutex objects.
     */
    @Contract(pure = true)
    private void initMutexObjects() {
        this.isValidCacheMutex = new Object();
        this.staticKeyCacheMutex = new Object();
        this.id64CacheMutex = new Object();
        this.id2CacheMutex = new Object();
        this.id3CacheMutex = new Object();
        this.inviteCodeCacheMutex = new Object();
        this.csgoCodeCacheMutex = new Object();
        this.id64UrlCacheMutex = new Object();
        this.id3UrlCacheMutex = new Object();
        this.userUrlCacheMutex = new Object();
        this.inviteUrlCacheMutex = new Object();
        this.chinaUrlCacheMutex = new Object();
        this.hashCodeCacheMutex = new Object();
        this.stringCacheMutex = new Object();
    }

    /**
     * Override the {@code #readResolve} method to set up
     * the object cache mutexes after deserialization.
     *
     * @return	this instance
     */
    @Contract(value = "-> this", pure = true)
    private Object readResolve() {
        this.initMutexObjects();
        return this;
    }

    /**
     * Initialize a {@link SteamId} instance.
     *
     * @param xuid      account type-32 identifier, may be null
     * @param universe  account universe type enum, may be null
     * @param instance  account instance type enum, may be null
     * @param account   account type enum, may be null
     */
    @Contract(pure = true)
    private SteamId(
            @Nullable
            final Integer xuid,

            @Nullable
            final ESteamUniverse universe,

            @Nullable
            final ESteamInstance instance,

            @Nullable
            final ESteamAccount account
    ) {
        this.xuid = xuid;
        this.universe = universe;
        this.instance = instance;
        this.account = account;

        this.initMutexObjects();
    }

    /**
     * Initialize a {@link SteamId} instance.
     *
     * @param xuid      account type-32 identifier, may be null
     * @param universe  account universe type identifier, may be null
     * @param instance  account instance type identifier, may be null
     * @param account   account type identifier, may be null
     */
    @Contract(pure = true)
    private SteamId(
            @Nullable
            final Integer xuid,

            @Nullable
            final Integer universe,

            @Nullable
            final Integer instance,

            @Nullable
            final Integer account
    ) {
        this(
                xuid,
                ESteamUniverse.fromIdOrNull(universe),
                ESteamInstance.fromIdOrNull(instance),
                ESteamAccount.fromIdOrNull(account)
        );
    }

    /**
     * Initialize a {@link SteamId} instance.
     *
     * @param xuid      account type-32 identifier, may be null
     * @param universe  account universe type identifier, may be null
     * @param instance  account instance type identifier, may be null
     * @param account   account type character, may be null
     */
    @Contract(pure = true)
    private SteamId(
            @Nullable
            final Integer xuid,

            @Nullable
            final Integer universe,

            @Nullable
            final Integer instance,

            @Nullable
            final Character account
    ) {
        this(
                xuid,
                ESteamUniverse.fromIdOrNull(universe),
                ESteamInstance.fromIdOrNull(instance),
                ESteamAccount.fromCharOrNull(account)
        );
    }

    /**
     * Initialize a {@link SteamId} instance.
     *
     * <p>Wraps {@link #SteamId(Integer, ESteamUniverse, ESteamInstance, ESteamAccount)}
     * w/ {@link ESteamUniverse#PUBLIC}, {@link ESteamInstance#DESKTOP}, and {@link ESteamAccount#INDIVIDUAL}.
     *
     * @param xuid  account type-32 identifier, may be null
     */
    @Contract(pure = true)
    private SteamId(
            @Nullable
            final Integer xuid
    ) {
        this(
                xuid,
                ESteamUniverse.PUBLIC,
                ESteamInstance.DESKTOP,
                ESteamAccount.INDIVIDUAL
        );
    }

    /**
     * Initialize a {@link SteamId} instance.
     *
     * <p>Defines a copy constructor.
     *
     * @param that	instance to copy field values from
     */
    @Contract(value = "null -> fail", pure = true)
    private SteamId(
            @UnknownNullability
            final SteamId that
    ) {
        this(
                that.xuid,
                that.universe,
                that.instance,
                that.account
        );

        this.isValidCache = that.isValidCache;
        this.staticKeyCache = that.staticKeyCache;
        this.id64Cache = that.id64Cache;
        this.id2Cache = that.id2Cache;
        this.id3Cache = that.id3Cache;
        this.inviteCodeCache = that.inviteCodeCache;
        this.csgoCodeCache = that.csgoCodeCache;
        this.id64UrlCache = that.id64UrlCache;
        this.id3UrlCache = that.id3UrlCache;
        this.userUrlCache = that.userUrlCache;
        this.inviteUrlCache = that.inviteUrlCache;
        this.chinaUrlCache = that.chinaUrlCache;

        this.hashCodeCache = that.hashCodeCache;
        this.stringCache = that.stringCache;
    }

    /**
     * Check if this account type-32 identifier isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean hasXuid() {
        return this.xuid != null;
    }

    /**
     * Check if this account universe type isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean hasUniverseType() {
        return this.universe != null;
    }

    /**
     * Check if this account instance type isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean hasInstanceType() {
        return this.instance != null;
    }

    /**
     * Check if this account type isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean hasAccountType() {
        return this.account != null;
    }

    /**
     * Check if this static key cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isStaticKeyCached() {
        return this.staticKeyCache != null;
    }

    /**
     * Check if this account type-64 identifier cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSteam64Cached() {
        return this.id64Cache != null;
    }

    /**
     * Check if this account type-2 identifier cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSteam2Cached() {
        return this.id2Cache != null;
    }

    /**
     * Check if this account type-3 identifier cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSteam3Cached() {
        return this.id3Cache != null;
    }

    /**
     * Check if this invite code cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isInviteCodeCached() {
        return this.inviteCodeCache != null;
    }

    /**
     * Check if this CS:GO friend code cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isCsgoCodeCached() {
        return this.csgoCodeCache != null;
    }

    /**
     * Check if this /profiles/%id-64% URL cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSteam64UrlCached() {
        return this.id64UrlCache != null;
    }

    /**
     * Check if this /profiles/%id-3% URL cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSteam3UrlCached() {
        return this.id3UrlCache != null;
    }

    /**
     * Check if this /user/%invite-code% URL cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSteamUserUrlCached() {
        return this.userUrlCache != null;
    }

    /**
     * Check if this /p/%invite-code% URL cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSteamInviteUrlCached() {
        return this.inviteUrlCache != null;
    }

    /**
     * Check if this /profiles/%id-64% China URL cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isSteam64ChinaUrlCached() {
        return this.chinaUrlCache != null;
    }

    /**
     * Check if this hash code cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isHashCodeCached() {
        return this.hashCodeCache != null;
    }

    /**
     * Check if this string cache isn't null.
     *
     * @return  {@code true} if not null
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isStringCached() {
        return this.stringCache != null;
    }

    /**
     * Get this account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type-32 identifier is null.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account type-32 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getXuidOrElse(
            @Nullable
            final Integer defaultValue
    ) {
        return UwObject.ifNull(this.getXuidOrNull(), defaultValue);
    }

    /**
     * Get this account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type-32 identifier is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type-32 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Integer getXuidOrElse(
            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getXuidOrNull(), defaultValueSupplier);
    }

    /**
     * Get this account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getXuidOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type-32 identifier is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type-32 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Integer getXuidOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return this.getXuidOrElse((@Nullable Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    /**
     * Get this account type-32 identifier
     * or return the {@link USteamId#BASE_XUID} on failure.
     *
     * <p>Wraps {@link #getXuidOrElse(Integer)}
     * w/ {@link USteamId#BASE_XUID} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type-32 identifier is null.</li>
     * </ul>
     *
     * @return  account type-32 identifier or the {@link USteamId#BASE_XUID}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getXuidOrBase() {
        return this.getXuidOrElse(USteamId.BASE_XUID);
    }

    /**
     * Get this account type-32 identifier
     * or return the {@link USteamId#MIN_XUID} on failure.
     *
     * <p>Wraps {@link #getXuidOrElse(Integer)}
     * w/ {@link USteamId#MIN_XUID} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type-32 identifier is null.</li>
     * </ul>
     *
     * @return  account type-32 identifier or {@link USteamId#MIN_XUID}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getXuidOrMin() {
        return this.getXuidOrElse(USteamId.MIN_XUID);
    }

    /**
     * Get this account type-32 identifier
     * or return the {@link USteamId#MAX_XUID} on failure.
     *
     * <p>Wraps {@link #getXuidOrElse(Integer)}
     * w/ {@link USteamId#MAX_XUID} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type-32 identifier is null.</li>
     * </ul>
     *
     * @return  account type-32 identifier or {@link USteamId#MAX_XUID}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getXuidOrMax() {
        return this.getXuidOrElse(USteamId.MAX_XUID);
    }

    /**
     * Get this account type-32 identifier
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type-32 identifier is null.</li>
     * </ul>
     *
     * @return  account type-32 identifier or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getXuidOrNull() {
        return this.xuid;
    }

    /**
     * Get this account universe type enum
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account universe type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrElse(
            @Nullable
            final ESteamUniverse defaultValue
    ) {
        return UwObject.ifNull(this.getUniverseTypeOrNull(), defaultValue);
    }

    /**
     * Get this account universe type enum
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier supplier to get the default value from, may be null
     *
     * @return  account universe type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public ESteamUniverse getUniverseTypeOrElse(
            @Nullable
            final Supplier<@UnknownNullability ESteamUniverse> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getUniverseTypeOrNull(), defaultValueSupplier);
    }

    /**
     * Get this account universe type enum
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getUniverseTypeOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier supplier to get the default value from, may be null
     *
     * @return  account universe type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public ESteamUniverse getUniverseTypeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability ESteamUniverse> defaultValueSupplier
    ) {
        return this.getUniverseTypeOrElse((@Nullable Supplier<@UnknownNullability ESteamUniverse>) defaultValueSupplier);
    }

    /**
     * Get this account universe type enum
     * or return the {@link ESteamUniverse#BASE} instance on failure.
     *
     * <p>Wraps {@link #getUniverseTypeOrElse(ESteamUniverse)}
     * w/ {@link ESteamUniverse#BASE} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @return  account universe type enum or {@link ESteamUniverse#BASE}
     */
    @NotNull
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrBase() {
        return this.getUniverseTypeOrElse(ESteamUniverse.BASE);
    }

    /**
     * Get this account universe type enum
     * or return the {@link ESteamUniverse#MIN} instance on failure.
     *
     * <p>Wraps {@link #getUniverseTypeOrElse(ESteamUniverse)}
     * w/ {@link ESteamUniverse#MIN} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @return  account universe type enum or {@link ESteamUniverse#MIN}
     */
    @NotNull
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrMin() {
        return this.getUniverseTypeOrElse(ESteamUniverse.MIN);
    }

    /**
     * Get this account universe type enum
     * or return the {@link ESteamUniverse#MAX} instance on failure.
     *
     * <p>Wraps {@link #getUniverseTypeOrElse(ESteamUniverse)}
     * w/ {@link ESteamUniverse#MAX} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @return  account universe type enum or {@link ESteamUniverse#MAX}
     */
    @NotNull
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrMax() {
        return this.getUniverseTypeOrElse(ESteamUniverse.MAX);
    }

    /**
     * Get this account universe type enum
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @return  account universe type enum or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public ESteamUniverse getUniverseTypeOrNull() {
        return this.universe;
    }

    /**
     * Get this account universe type identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account universe type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrElse(
            @Nullable
            final Integer defaultValue
    ) {
        return UwObject.ifNull(this.getUniverseTypeIdOrNull(), defaultValue);
    }

    /**
     * Get this account universe type identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account universe type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Integer getUniverseTypeIdOrElse(
            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getUniverseTypeIdOrNull(), defaultValueSupplier);
    }

    /**
     * Get this account universe type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getUniverseTypeIdOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account universe type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Integer getUniverseTypeIdOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return this.getUniverseTypeIdOrElse((@Nullable Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    /**
     * Get this account universe type identifier
     * or return the {@link USteamUniverse#BASE} on failure.
     *
     * <p>Wraps {@link #getUniverseTypeIdOrElse(Integer)}
     * w/ {@link USteamUniverse#BASE} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @return  account universe type identifier or {@link USteamUniverse#BASE}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrBase() {
        return this.getUniverseTypeIdOrElse(USteamUniverse.BASE);
    }

    /**
     * Get this account universe type identifier
     * or return the {@link USteamUniverse#MIN} on failure.
     *
     * <p>Wraps {@link #getUniverseTypeIdOrElse(Integer)}
     * w/ {@link USteamUniverse#MIN} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @return  account universe type identifier or {@link USteamUniverse#MIN}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrMin() {
        return this.getUniverseTypeIdOrElse(USteamUniverse.MIN);
    }

    /**
     * Get this account universe type identifier
     * or return the {@link USteamUniverse#MAX} on failure.
     *
     * <p>Wraps {@link #getUniverseTypeIdOrElse(Integer)}
     * w/ {@link USteamUniverse#MAX} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @return  account universe type identifier or {@link USteamUniverse#MAX}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrMax() {
        return this.getUniverseTypeIdOrElse(USteamUniverse.MAX);
    }

    /**
     * Get this account universe type identifier
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account universe type enum is null.</li>
     * </ul>
     *
     * @return  account universe type identifier or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getUniverseTypeIdOrNull() {
        return UwObject.ifNotNullNoCheck(this.getUniverseTypeOrNull(), ESteamUniverse::getId);
    }

    /**
     * Get this account instance type enum
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account instance type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public ESteamInstance getInstanceTypeOrElse(
            @Nullable
            final ESteamInstance defaultValue
    ) {
        return UwObject.ifNull(this.getInstanceTypeOrNull(), defaultValue);
    }

    /**
     * Get this account instance type enum
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account instance type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public ESteamInstance getInstanceTypeOrElse(
            @Nullable
            final Supplier<@UnknownNullability ESteamInstance> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getInstanceTypeOrNull(), defaultValueSupplier);
    }

    /**
     * Get this account instance type enum
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getInstanceTypeOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account instance type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public ESteamInstance getInstanceTypeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability ESteamInstance> defaultValueSupplier
    ) {
        return this.getInstanceTypeOrElse((@Nullable Supplier<@UnknownNullability ESteamInstance>) defaultValueSupplier);
    }

    /**
     * Get this account instance type enum
     * or return the {@link ESteamInstance#MIN} instance on failure.
     *
     * <p>Wraps {@link #getInstanceTypeOrElse(ESteamInstance)}
     * w/ {@link ESteamInstance#MIN} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @return  account instance type enum or {@link ESteamInstance#MIN}
     */
    @NotNull
    @Contract(pure = true)
    public ESteamInstance getInstanceTypeOrMin() {
        return this.getInstanceTypeOrElse(ESteamInstance.MIN);
    }

    /**
     * Get this account instance type enum
     * or return the {@link ESteamInstance#MAX} instance on failure.
     *
     * <p>Wraps {@link #getInstanceTypeOrElse(ESteamInstance)}
     * w/ {@link ESteamInstance#MAX} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @return  account instance type enum or {@link ESteamInstance#MAX}
     */
    @NotNull
    @Contract(pure = true)
    public ESteamInstance getInstanceTypeOrMax() {
        return this.getInstanceTypeOrElse(ESteamInstance.MAX);
    }

    /**
     * Get this account instance type enum
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @return  account instance type enum or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public ESteamInstance getInstanceTypeOrNull() {
        return this.instance;
    }

    /**
     * Get this account instance type identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account instance type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getInstanceTypeIdOrElse(
            @Nullable
            final Integer defaultValue
    ) {
        return UwObject.ifNull(this.getInstanceTypeIdOrNull(), defaultValue);
    }

    /**
     * Get this account instance type identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account instance type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Integer getInstanceTypeIdOrElse(
            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getInstanceTypeIdOrNull(), defaultValueSupplier);
    }

    /**
     * Get this account instance type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getInstanceTypeIdOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account instance type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Integer getInstanceTypeIdOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return this.getInstanceTypeIdOrElse((@Nullable Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    /**
     * Get this account instance type identifier
     * or return the {@link USteamInstance#MIN} on failure.
     *
     * <p>Wraps {@link #getInstanceTypeIdOrElse(Integer)}
     * w/ {@link USteamInstance#MIN} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @return  account instance type identifier or {@link USteamInstance#MIN}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getInstanceTypeIdOrMin() {
        return this.getInstanceTypeIdOrElse(USteamInstance.MIN);
    }

    /**
     * Get this account instance type identifier
     * or return the {@link USteamInstance#MAX} on failure.
     *
     * <p>Wraps {@link #getInstanceTypeIdOrElse(Integer)}
     * w/ {@link USteamInstance#MAX} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @return  account instance type identifier or {@link USteamInstance#MAX}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getInstanceTypeIdOrMax() {
        return this.getInstanceTypeIdOrElse(USteamInstance.MAX);
    }

    /**
     * Get this account instance type identifier
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account instance type enum is null.</li>
     * </ul>
     *
     * @return  account instance type identifier or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getInstanceTypeIdOrNull() {
        return UwObject.ifNotNullNoCheck(this.getInstanceTypeOrNull(), ESteamInstance::getId);
    }

    /**
     * Get this account type enum
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrElse(
            @Nullable
            final ESteamAccount defaultValue
    ) {
        return UwObject.ifNull(this.getAccountTypeOrNull(), defaultValue);
    }

    /**
     * Get this account type enum
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public ESteamAccount getAccountTypeOrElse(
            @Nullable
            final Supplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getAccountTypeOrNull(), defaultValueSupplier);
    }

    /**
     * Get this account type enum
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getAccountTypeOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type enum or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public ESteamAccount getAccountTypeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability ESteamAccount> defaultValueSupplier
    ) {
        return this.getAccountTypeOrElse((@Nullable Supplier<@UnknownNullability ESteamAccount>) defaultValueSupplier);
    }

    /**
     * Get this account type enum
     * or return the {@link ESteamAccount#BASE} instance on failure.
     *
     * <p>Wraps {@link #getAccountTypeOrElse(ESteamAccount)}
     * w/ {@link ESteamAccount#BASE} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type enum or {@link ESteamAccount#BASE}
     */
    @NotNull
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrBase() {
        return this.getAccountTypeOrElse(ESteamAccount.BASE);
    }

    /**
     * Get this account type enum
     * or return the {@link ESteamAccount#MIN} instance on failure.
     *
     * <p>Wraps {@link #getAccountTypeOrElse(ESteamAccount)}
     * w/ {@link ESteamAccount#MIN} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type enum or {@link ESteamAccount#MIN}
     */
    @NotNull
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrMin() {
        return this.getAccountTypeOrElse(ESteamAccount.MIN);
    }

    /**
     * Get this account type enum
     * or return the {@link ESteamAccount#MAX} instance on failure.
     *
     * <p>Wraps {@link #getAccountTypeOrElse(ESteamAccount)}
     * w/ {@link ESteamAccount#MAX} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type enum or {@link ESteamAccount#MAX}
     */
    @NotNull
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrMax() {
        return this.getAccountTypeOrElse(ESteamAccount.MAX);
    }

    /**
     * Get this account type enum
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type enum or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public ESteamAccount getAccountTypeOrNull() {
        return this.account;
    }

    /**
     * Get this account type identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getAccountTypeIdOrElse(
            @Nullable
            final Integer defaultValue
    ) {
        return UwObject.ifNull(this.getAccountTypeIdOrNull(), defaultValue);
    }

    /**
     * Get this account type identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Integer getAccountTypeIdOrElse(
            @Nullable
            final Supplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getAccountTypeIdOrNull(), defaultValueSupplier);
    }

    /**
     * Get this account type identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getAccountTypeIdOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Integer getAccountTypeIdOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Integer> defaultValueSupplier
    ) {
        return this.getAccountTypeIdOrElse((@Nullable Supplier<@UnknownNullability Integer>) defaultValueSupplier);
    }

    /**
     * Get this account type identifier
     * or return the {@link USteamAccount#BASE_ID} on failure.
     *
     * <p>Wraps {@link #getAccountTypeIdOrElse(Integer)}
     * w/ {@link USteamAccount#BASE_ID} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type identifier or {@link USteamAccount#BASE_ID}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getAccountTypeIdOrBase() {
        return this.getAccountTypeIdOrElse(USteamAccount.BASE_ID);
    }

    /**
     * Get this account type identifier
     * or return the {@link USteamAccount#MIN_ID} on failure.
     *
     * <p>Wraps {@link #getAccountTypeIdOrElse(Integer)}
     * w/ {@link USteamAccount#MIN_ID} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type identifier or {@link USteamAccount#MIN_ID}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getAccountTypeIdOrMin() {
        return this.getAccountTypeIdOrElse(USteamAccount.MIN_ID);
    }

    /**
     * Get this account type identifier
     * or return the {@link USteamAccount#MAX_ID} on failure.
     *
     * <p>Wraps {@link #getAccountTypeIdOrElse(Integer)}
     * w/ {@link USteamAccount#MAX_ID} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type identifier or {@link USteamAccount#MAX_ID}
     */
    @NotNull
    @Contract(pure = true)
    public Integer getAccountTypeIdOrMax() {
        return this.getAccountTypeIdOrElse(USteamAccount.MAX_ID);
    }

    /**
     * Get this account type identifier
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type identifier or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getAccountTypeIdOrNull() {
        return UwObject.ifNotNullNoCheck(this.getAccountTypeOrNull(), ESteamAccount::getId);
    }

    /**
     * Get this account type character
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account type character or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public Character getAccountTypeCharOrElse(
            @Nullable
            final Character defaultValue
    ) {
        return UwObject.ifNull(this.getAccountTypeCharOrNull(), defaultValue);
    }

    /**
     * Get this account type character
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type character or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Character getAccountTypeCharOrElse(
            @Nullable
            final Supplier<@UnknownNullability Character> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.getAccountTypeCharOrNull(), defaultValueSupplier);
    }

    /**
     * Get this account type character
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getAccountTypeCharOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type character or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Character getAccountTypeCharOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Character> defaultValueSupplier
    ) {
        return this.getAccountTypeCharOrElse((@Nullable Supplier<@UnknownNullability Character>) defaultValueSupplier);
    }

    /**
     * Get this account type character
     * or return the {@link USteamAccount#BASE_CHAR} on failure.
     *
     * <p>Wraps {@link #getAccountTypeCharOrElse(Character)}
     * w/ {@link USteamAccount#BASE_CHAR} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type character or {@link USteamAccount#BASE_CHAR}
     */
    @NotNull
    @Contract(pure = true)
    public Character getAccountTypeCharOrBase() {
        return this.getAccountTypeCharOrElse(USteamAccount.BASE_CHAR);
    }

    /**
     * Get this account type character
     * or return the {@link USteamAccount#MIN_CHAR} on failure.
     *
     * <p>Wraps {@link #getAccountTypeCharOrElse(Character)}
     * w/ {@link USteamAccount#MIN_CHAR} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type character or {@link USteamAccount#MIN_CHAR}
     */
    @NotNull
    @Contract(pure = true)
    public Character getAccountTypeCharOrMin() {
        return this.getAccountTypeCharOrElse(USteamAccount.MIN_CHAR);
    }

    /**
     * Get this account type character
     * or return the {@link USteamAccount#MAX_CHAR} on failure.
     *
     * <p>Wraps {@link #getAccountTypeCharOrElse(Character)}
     * w/ {@link USteamAccount#MAX_CHAR} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type character or {@link USteamAccount#MAX_CHAR}
     */
    @NotNull
    @Contract(pure = true)
    public Character getAccountTypeCharOrMax() {
        return this.getAccountTypeCharOrElse(USteamAccount.MAX_CHAR);
    }

    /**
     * Get this account type character
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This account type enum is null.</li>
     * </ul>
     *
     * @return  account type character or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public Character getAccountTypeCharOrNull() {
        return UwObject.ifNotNullNoCheck(this.getAccountTypeOrNull(), ESteamAccount::getChar);
    }

    /**
     * Get this is valid cache.
     *
     * @return  is valid cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public Boolean getIsValidCache() {
        return this.isValidCache;
    }

    /**
     * Get this static key cache.
     *
     * @return  static key cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public Long getStaticKeyCache() {
        return this.staticKeyCache;
    }

    /**
     * Get this account type-64 identifier cache.
     *
     * @return  account type-64 identifier cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public Long getSteam64Cache() {
        return this.id64Cache;
    }

    /**
     * Get this account type-2 identifier cache.
     *
     * @return  account type-2 identifier cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getSteam2Cache() {
        return this.id2Cache;
    }

    /**
     * Get this account type-3 identifier cache.
     *
     * @return  account type-3 identifier cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getSteam3Cache() {
        return this.id3Cache;
    }

    /**
     * Get this invite code cache.
     *
     * @return  invite code cache.
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getInviteCodeCache() {
        return this.inviteCodeCache;
    }

    /**
     * Get this CS:GO friend code cache.
     *
     * @return  CS:GO friend code cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getCsgoCodeCache() {
        return this.csgoCodeCache;
    }

    /**
     * Get this /profiles/%id-64% URL cache.
     *
     * @return  /profiles/%id-64% URL cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getSteam64UrlCache() {
        return this.id64UrlCache;
    }

    /**
     * Get this /profiles/%id-3% URL cache.
     *
     * @return  /profiles/%id-3% URL cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getSteam3UrlCache() {
        return this.id3UrlCache;
    }

    /**
     * Get this /user/%invite-code% URL cache.
     *
     * @return  /user/%invite-code% URL cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getSteamUserUrlCache() {
        return this.userUrlCache;
    }

    /**
     * Get this /p/%invite-code% URL cache.
     *
     * @return  /p/%invite-code% URL cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getSteamInviteUrlCache() {
        return this.inviteUrlCache;
    }

    /**
     * Get this /profiles/%id-64% China URL cache.
     *
     * @return  /profiles/%id-64% China URL cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getSteam64ChinaUrlCache() {
        return this.chinaUrlCache;
    }

    /**
     * Get this hash code cache.
     *
     * @return  hash code cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public Integer getHashCodeCache() {
        return this.hashCodeCache;
    }

    /**
     * Get this string cache.
     *
     * @return  string cache
     */
    @UnknownNullability
    @Contract(pure = true)
    public String getStringCache() {
        return this.stringCache;
    }

    /**
     * Check if this instance is valid.
     *
     * @return  {@code true} if valid
     *          or {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isValid() {
        if (this.isValidCache != null) {
            return this.isValidCache;
        }

        synchronized (this.isValidCacheMutex) {
            if (this.isValidCache != null) {
                return this.isValidCache;
            }

            if (this.xuid == null || this.universe == null
                    || this.instance == null || this.account == null) {
                return (this.isValidCache = false);
            }

            if (this.xuid < USteamId.BASE_XUID
                /*&& this.xuid > USteamId.MAX_XUID*/) {
                return (this.isValidCache = false);
            }

            if (this.universe == ESteamUniverse.INVALID
                    || this.account == ESteamAccount.INVALID) {
                return (this.isValidCache = false);
            }

            if (this.xuid < USteamId.MIN_XUID) {
                return (this.isValidCache = this.account != ESteamAccount.INDIVIDUAL
                        && this.account != ESteamAccount.GAME_SERVER
                        && (this.account != ESteamAccount.CLAN || this.instance == ESteamInstance.ALL));
            }

            return (this.isValidCache = true);
        }
    }

    /**
     * Get this instance as a static key
     * or return a default value on failure.
     *
     * <p>Used for grouping accounts w/ different account instances.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     *     <li>Caught {@link ArithmeticException}.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  static key or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public Long toStaticKeyOrElse(
            @Nullable
            final Long defaultValue
    ) {
        return UwObject.ifNull(this.toStaticKeyOrNull(), defaultValue);
    }

    /**
     * Get this instance as a static key
     * or return a default value on failure.
     *
     * <p>Used for grouping accounts w/ different account instances.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     *     <li>Caught {@link ArithmeticException}.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  static key or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Long toStaticKeyOrElse(
            @Nullable
            final Supplier<@UnknownNullability Long> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toStaticKeyOrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as a static key
     * or return a default value on failure.
     *
     * <p>Used for grouping accounts w/ different account instances.
     *
     * <p>Wraps {@link #toStaticKeyOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     *     <li>Caught {@link ArithmeticException}.</li>
     * </ul>
     *
     * @param defaultValueSupplier supplier to get the default value from, may be null
     *
     * @return  static key or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Long toStaticKeyOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Long> defaultValueSupplier
    ) {
        return this.toStaticKeyOrElse((@Nullable Supplier<@UnknownNullability Long>) defaultValueSupplier);
    }

    /**
     * Get this instance as a static key
     * or return {@code 0} on failure.
     *
     * <p>Used for grouping accounts w/ different account instances.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     *     <li>Caught {@link ArithmeticException}.</li>
     * </ul>
     *
     * @return  static key or {@code 0}
     */
    @NotNull
    @Contract(pure = true)
    public Long toStaticKeyOrZero() {
        return this.toStaticKeyOrElse(0L);
    }

    /**
     * Get this instance as a static key
     * or return the {@link Long#MIN_VALUE} on failure.
     *
     * <p>Used for grouping accounts w/ different account instances.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     *     <li>Caught {@link ArithmeticException}.</li>
     * </ul>
     *
     * @return  static key or {@link Long#MIN_VALUE}
     */
    @NotNull
    @Contract(pure = true)
    public Long toStaticKeyOrMin() {
        return this.toStaticKeyOrElse(Long.MIN_VALUE);
    }

    /**
     * Get this instance as a static key
     * or return the {@link Long#MAX_VALUE} on failure.
     *
     * <p>Used for grouping accounts w/ different account instances.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     *     <li>Caught {@link ArithmeticException}.</li>
     * </ul>
     *
     * @return  static key or {@link Long#MAX_VALUE}
     */
    @NotNull
    @Contract(pure = true)
    public Long toStaticKeyOrMax() {
        return this.toStaticKeyOrElse(Long.MAX_VALUE);
    }

    /**
     * Get this instance as a static key
     * or return {@code null} on failure.
     *
     * <p>Used for grouping accounts w/ different account instances.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     *     <li>Caught {@link ArithmeticException}.</li>
     * </ul>
     *
     * @return  static key or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public Long toStaticKeyOrNull() {
        if (this.staticKeyCache != null) {
            return this.staticKeyCache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.staticKeyCacheMutex) {
            if (this.staticKeyCache != null) {
                return this.staticKeyCache;
            }

            final BigInteger universe = BigInteger.valueOf(this.universe.getId())
                    .shiftLeft(USteamBit.ACCOUNT_UNIVERSE_OFFSET);

            final BigInteger account = BigInteger.valueOf(this.account.getId())
                    .shiftLeft(USteamBit.ACCOUNT_ID_OFFSET);

            try {
                return (this.staticKeyCache = BigInteger.valueOf(this.xuid)
                        .add(universe)
                        .add(account)
                        .longValueExact());
            } catch (ArithmeticException ignored) {
            }
        }

        return null;
    }

    /**
     * Get this instance as an account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account type-64 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public Long toSteam64OrElse(
            @Nullable
            final Long defaultValue
    ) {
        return UwObject.ifNull(this.toSteam64OrNull(), defaultValue);
    }

    /**
     * Get this instance as an account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type-64 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Long toSteam64OrElse(
            @Nullable
            final Supplier<@UnknownNullability Long> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam64OrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as an account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toSteam64OrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type-64 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public Long toSteam64OrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability Long> defaultValueSupplier
    ) {
        return this.toSteam64OrElse((@Nullable Supplier<@UnknownNullability Long>) defaultValueSupplier);
    }

    /**
     * Get this instance as an account type-64 identifier
     * or return the {@link USteamId#BASE_ID64} on failure.
     *
     * <p>Wraps {@link #toSteam64OrElse(Long)}
     * w/ {@link USteamId#BASE_ID64} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  account type-64 identifier or {@link USteamId#BASE_ID64}
     */
    @NotNull
    @Contract(pure = true)
    public Long toSteam64OrBase() {
        return this.toSteam64OrElse(USteamId.BASE_ID64);
    }

    /**
     * Get this instance as an account type-64 identifier
     * or return the {@link USteamId#MIN_ID64} on failure.
     *
     * <p>Wraps {@link #toSteam64OrElse(Long)}
     * w/ {@link USteamId#MIN_ID64} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  account type-64 identifier or {@link USteamId#MIN_ID64}
     */
    @NotNull
    @Contract(pure = true)
    public Long toSteam64OrMin() {
        return this.toSteam64OrElse(USteamId.MIN_ID64);
    }

    /**
     * Get this instance as an account type-64 identifier
     * or return the {@link USteamId#MAX_ID64} on failure.
     *
     * <p>Wraps {@link #toSteam64OrElse(Long)}
     * w/ {@link USteamId#MAX_ID64} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  account type-64 identifier or {@link USteamId#MAX_ID64}
     */
    @NotNull
    @Contract(pure = true)
    public Long toSteam64OrMax() {
        return this.toSteam64OrElse(USteamId.MAX_ID64);
    }

    /**
     * Get this instance as an account type-64 identifier
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  account type-64 identifier or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public Long toSteam64OrNull() {
        if (this.id64Cache != null) {
            return this.id64Cache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.id64CacheMutex) {
            if (this.id64Cache != null) {
                return this.id64Cache;
            }

            final long xuid = this.xuid;
            final long instance = this.instance.getId();
            final long account = this.account.getId();
            final long universe = this.universe.getId();

            return (this.id64Cache = xuid << USteamBit.ACCOUNT_ID_OFFSET
                    | instance << USteamBit.ACCOUNT_INSTANCE_OFFSET
                    | account << USteamBit.ACCOUNT_TYPE_OFFSET
                    | universe << USteamBit.ACCOUNT_UNIVERSE_OFFSET);
        }
    }

    /**
     * Get this instance as an account type-2 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account type-2 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam2OrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam2OrNull(), defaultValue);
    }

    /**
     * Get this instance as an account type-2 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type-2 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam2OrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam2OrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as an account type-2 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toSteam2OrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type-2 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam2OrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam2OrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as an account type-2 identifier
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toSteam2OrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  account type-2 identifier or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toSteam2OrEmpty() {
        return this.toSteam2OrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as an account type-2 identifier
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  account type-2 identifier or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam2OrNull() {
        if (this.id2Cache != null) {
            return this.id2Cache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.id2CacheMutex) {
            if (this.id2Cache != null) {
                return this.id2Cache;
            }

            final int x = this.universe.getId();
            final int y = this.xuid & 1;
            final int z = this.xuid >> 1;

            return (this.id2Cache = "STEAM_" + x + ":" + y + ":" + z);
        }
    }

    /**
     * Get this instance as an account type-3 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  account type-3 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam3OrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam3OrNull(), defaultValue);
    }

    /**
     * Get this instance as an account type-3 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type-3 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam3OrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam3OrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as an account type-3 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toSteam3OrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  account type-3 identifier or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam3OrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam3OrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as an account type-3 identifier
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toSteam3OrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  account type-3 identifier or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toSteam3OrEmpty() {
        return this.toSteam3OrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as an account type-3 identifier
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  account type-3 identifier or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam3OrNull() {
        if (this.id3Cache != null) {
            return this.id3Cache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.id3CacheMutex) {
            if (this.id3Cache != null) {
                return this.id3Cache;
            }

            final boolean bInstance;
            final char ch = this.account.getChar();

            //noinspection CommentedOutCode
            switch (this.account) {
//                case CHAT:
//                    switch (this.instance) {
//                        case CLAN:
//                            ch = USteamAccount.CLAN_CHAT_CHAR;
//                            break;
//
//                        case LOBBY:
//                            ch = USteamAccount.LOBBY_CHAT_CHAR;
//                            break;
//                    }
//                    break;

                case ANON_GAME_SERVER:
                case MULTISEAT:
                    bInstance = true;
                    break;

                default:
                    bInstance = false;
            }

            int universe = this.universe.getId();
            int instance = this.instance.getId();

            return (this.id3Cache = "[" + ch + ":" + universe + ":" + this.xuid + (bInstance ? ":" + instance : "") + "]");
        }
    }

    /**
     * Get this instance as a Steam invite code
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  invite code or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toInviteCodeOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toInviteCodeOrNull(), defaultValue);
    }

    /**
     * Get this instance as a Steam invite code
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  invite code or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toInviteCodeOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toInviteCodeOrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam invite code
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toInviteCodeOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  invite code or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toInviteCodeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toInviteCodeOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam invite code
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toInviteCodeOrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  invite code or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toInviteCodeOrEmpty() {
        return this.toInviteCodeOrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as a Steam invite code
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  invite code or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toInviteCodeOrNull() {
        if (this.inviteCodeCache != null) {
            return this.inviteCodeCache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.inviteCodeCacheMutex) {
            if (this.inviteCodeCache != null) {
                return this.inviteCodeCache;
            }

            return (this.inviteCodeCache = USteamInvite.fromXuidNoCheck(this.xuid));
        }
    }

    /**
     * Get this instance as a CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  CS:GO friend code or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toCsgoCodeOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toCsgoCodeOrNull(), defaultValue);
    }

    /**
     * Get this instance as a CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  CS:GO friend code or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toCsgoCodeOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toCsgoCodeOrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as a CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toCsgoCodeOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  CS:GO friend code or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toCsgoCodeOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toCsgoCodeOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as a CS:GO friend code
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toCsgoCodeOrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  CS:GO friend code or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toCsgoCodeOrEmpty() {
        return this.toCsgoCodeOrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as a CS:GO friend code
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  CS:GO friend code or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toCsgoCodeOrNull() {
        if (this.csgoCodeCache != null) {
            return this.csgoCodeCache;
        }

        if (!this.isValid()) {
            return null;
        }

        synchronized (this.csgoCodeCacheMutex) {
            if (this.csgoCodeCache != null) {
                return this.csgoCodeCache;
            }

            return (this.csgoCodeCache = USteamCsgo.fromXuidNoCheck(this.xuid));
        }
    }

    /**
     * Get this instance as a Steam /profiles/%id-64% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  /profiles/%id-64% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam64UrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam64UrlOrNull(), defaultValue);
    }

    /**
     * Get this instance as a Steam /profiles/%id-64% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /profiles/%id-64% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam64UrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam64UrlOrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam /profiles/%id-64% URL
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toSteam64UrlOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /profiles/%id-64% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam64UrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam64UrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam /profiles/%id-64% URL
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toSteam64UrlOrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /profiles/%id-64% URL or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toSteam64UrlOrEmpty() {
        return this.toSteam64UrlOrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as a Steam /profiles/%id-64% URL
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /profiles/%id-64% URL or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam64UrlOrNull() {
        if (this.id64UrlCache != null) {
            return this.id64UrlCache;
        }

        final Long id64 = this.toSteam64OrNull();
        if (id64 == null) {
            return null;
        }

        synchronized (this.id64UrlCacheMutex) {
            if (this.id64UrlCache != null) {
                return this.id64UrlCache;
            }

            return (this.id64UrlCache = USteamUrl.PROFILE + id64);
        }
    }

    /**
     * Get this instance as a Steam /profiles/%id-3% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  /profiles/%id-3% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam3UrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam3UrlOrNull(), defaultValue);
    }

    /**
     * Get this instance as a Steam /profiles/%id-3% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /profiles/%id-3% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam3UrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam3UrlOrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam /profiles/%id-3% URL
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toSteam3UrlOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /profiles/%id-3% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam3UrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam3UrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam /profiles/%id-3% URL
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toSteam3UrlOrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /profiles/%id-3% URL or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toSteam3UrlOrEmpty() {
        return this.toSteam3UrlOrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as a Steam /profiles/%id-3% URL
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /profiles/%id-3% URL or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam3UrlOrNull() {
        if (this.id3UrlCache != null) {
            return this.id3UrlCache;
        }

        final String id3 = this.toSteam3OrNull();
        if (id3 == null) {
            return null;
        }

        synchronized (this.id3UrlCacheMutex) {
            if (this.id3UrlCache != null) {
                return this.id3UrlCache;
            }

            return (this.id3UrlCache = USteamUrl.PROFILE + id3);
        }
    }

    /**
     * Get this instance as a Steam /user/%invite-code% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  /user/%invite-code% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteamUserUrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteamUserUrlOrNull(), defaultValue);
    }

    /**
     * Get this instance as a Steam /user/%invite-code% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /user/%invite-code% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteamUserUrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteamUserUrlOrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam /user/%invite-code% URL
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toSteamUserUrlOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /user/%invite-code% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteamUserUrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteamUserUrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam /user/%invite-code% URL
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toSteamUserUrlOrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /user/%invite-code% URL or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toSteamUserUrlOrEmpty() {
        return this.toSteamUserUrlOrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as a Steam /user/%invite-code% URL
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /user/%invite-code% URL or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteamUserUrlOrNull() {
        if (this.userUrlCache != null) {
            return this.userUrlCache;
        }

        final String inviteCode = this.toInviteCodeOrNull();
        if (inviteCode == null) {
            return null;
        }

        synchronized (this.userUrlCacheMutex) {
            if (this.userUrlCache != null) {
                return this.userUrlCache;
            }

            return (this.userUrlCache = USteamUrl.USER + inviteCode);
        }
    }

    /**
     * Get this instance as a Steam /p/%invite-code% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  /p/%invite-code% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteamInviteUrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteamInviteUrlOrNull(), defaultValue);
    }

    /**
     * Get this instance as a Steam /p/%invite-code% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /p/%invite-code% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteamInviteUrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteamInviteUrlOrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam /p/%invite-code% URL
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toSteamInviteUrlOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /p/%invite-code% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteamInviteUrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteamInviteUrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam /p/%invite-code% URL
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toSteamInviteUrlOrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /p/%invite-code% URL or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toSteamInviteUrlOrEmpty() {
        return this.toSteamInviteUrlOrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as a Steam /p/%invite-code% URL
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /p/%invite-code% URL or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteamInviteUrlOrNull() {
        if (this.inviteUrlCache != null) {
            return this.inviteUrlCache;
        }

        final String inviteCode = this.toInviteCodeOrNull();
        if (inviteCode == null) {
            return null;
        }

        synchronized (this.inviteUrlCacheMutex) {
            if (this.inviteUrlCache != null) {
                return this.inviteUrlCache;
            }

            return (this.inviteUrlCache = USteamUrl.INVITE + inviteCode);
        }
    }

    /**
     * Get this instance as a Steam China /profiles/%id-64% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  /profiles/%id-64% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam64ChinaUrlOrElse(
            @Nullable
            final String defaultValue
    ) {
        return UwObject.ifNull(this.toSteam64ChinaUrlOrNull(), defaultValue);
    }

    /**
     * Get this instance as a Steam China /profiles/%id-64% URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /profiles/%id-64% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam64ChinaUrlOrElse(
            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(this.toSteam64ChinaUrlOrNull(), defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam China /profiles/%id-64% URL
     * or return a default value on failure.
     *
     * <p>Wraps {@link #toSteam64ChinaUrlOrElse(Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  /profiles/%id-64% URL or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public String toSteam64ChinaUrlOrElse(
            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return this.toSteam64ChinaUrlOrElse((@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get this instance as a Steam China /profiles/%id-64% URL
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #toSteam64ChinaUrlOrElse(String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /profiles/%id-64% URL or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public String toSteam64ChinaUrlOrEmpty() {
        return this.toSteam64ChinaUrlOrElse(UwString.EMPTY);
    }

    /**
     * Get this instance as a Steam China /profiles/%id-64% URL
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>This instance isn't valid.</li>
     * </ul>
     *
     * @return  /profiles/%id-64% URL or {@code null}
     */
    @UnknownNullability
    @Contract(pure = true)
    public String toSteam64ChinaUrlOrNull() {
        if (this.chinaUrlCache != null) {
            return this.chinaUrlCache;
        }

        final Long id64 = this.toSteam64OrNull();
        if (id64 == null) {
            return null;
        }

        synchronized (this.chinaUrlCacheMutex) {
            if (this.chinaUrlCache != null) {
                return this.chinaUrlCache;
            }

            return (this.chinaUrlCache = USteamUrl.CHINA + id64);
        }
    }

    /**
     * Create a new {@link SteamId} instance
     * w/ different account type-32 identifier value.
     *
     * @param xuid  new account type-32 identifier, may be null
     *
     * @return  new instance or this if values are equal
     */
    @NotNull
    @Contract(pure = true)
    public SteamId withXuid(
            @Nullable
            final Integer xuid
    ) {
        if (Objects.equals(this.xuid, xuid)) {
            return this;
        }

        return new SteamId(xuid, this.universe, this.instance, this.account);
    }

    /**
     * Create a new {@link SteamId} instance
     * w/ different account universe type value.
     *
     * @param universe  new account universe type, may be null
     *
     * @return  new instance or this if values are equal
     */
    @NotNull
    @Contract(pure = true)
    public SteamId withUniverseType(
            @Nullable
            final ESteamUniverse universe
    ) {
        if (this.universe == universe) {
            return this;
        }

        return new SteamId(this.xuid, universe, this.instance, this.account);
    }

    /**
     * Create a new {@link SteamId} instance
     * w/ different account universe type value.
     *
     * @param universe  new account universe type, may be null
     *
     * @return  new instance or this if values are equal
     */
    @NotNull
    @Contract(pure = true)
    public SteamId withUniverseType(
            @Nullable
            final Integer universe
    ) {
        return this.withUniverseType(ESteamUniverse.fromIdOrNull(universe));
    }

    /**
     * Create a new {@link SteamId} instance
     * w/ different account instance type value.
     *
     * @param instance  new account instance type, may be null
     *
     * @return  new instance or this if values are equal
     */
    @NotNull
    @Contract(pure = true)
    public SteamId withInstanceType(
            @Nullable
            final ESteamInstance instance
    ) {
        if (this.instance == instance) {
            return this;
        }

        return new SteamId(this.xuid, this.universe, instance, this.account);
    }

    /**
     * Create a new {@link SteamId} instance
     * w/ different account instance type value.
     *
     * @param instance  new account instance type, may be null
     *
     * @return  new instance or this if values are equal
     */
    @NotNull
    @Contract(pure = true)
    public SteamId withInstanceType(
            @Nullable
            final Integer instance
    ) {
        return this.withInstanceType(ESteamInstance.fromIdOrNull(instance));
    }

    /**
     * Create a new {@link SteamId} instance
     * w/ different account type value.
     *
     * @param account  new account type, may be null
     *
     * @return  new instance or this if values are equal
     */
    @NotNull
    @Contract(pure = true)
    public SteamId withAccountType(
            @Nullable
            final ESteamAccount account
    ) {
        if (this.account == account) {
            return this;
        }

        return new SteamId(this.xuid, this.universe, this.instance, account);
    }

    /**
     * Create a new {@link SteamId} instance
     * w/ different account type value.
     *
     * @param account  new account type, may be null
     *
     * @return  new instance or this if values are equal
     */
    @NotNull
    @Contract(pure = true)
    public SteamId withAccountType(
            @Nullable
            final Integer account
    ) {
        return this.withAccountType(ESteamAccount.fromIdOrNull(account));
    }

    /**
     * Create a new {@link SteamId} instance
     * w/ different account type value.
     *
     * @param account  new account type, may be null
     *
     * @return  new instance or this if values are equal
     */
    @NotNull
    @Contract(pure = true)
    public SteamId withAccountType(
            @Nullable
            final Character account
    ) {
        return this.withAccountType(ESteamAccount.fromCharOrNull(account));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract(pure = true)
    public boolean equals(
            @Nullable
            final Object obj
    ) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        SteamId that = (SteamId) obj;
        return Objects.equals(this.xuid, that.xuid)
                && this.universe == that.universe
                && this.instance == that.instance
                && this.account == that.account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract(pure = true)
    public int hashCode() {
        if (this.hashCodeCache != null) {
            return this.hashCodeCache;
        }

        synchronized (this.hashCodeCacheMutex) {
            if (this.hashCodeCache != null) {
                return this.hashCodeCache;
            }

            return (this.hashCodeCache = Objects.hash(this.xuid, this.universe, this.instance, this.account));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    @Contract(pure = true)
    public String toString() {
        if (this.stringCache != null) {
            return this.stringCache;
        }

        synchronized (this.stringCacheMutex) {
            if (this.stringCache != null) {
                return this.stringCache;
            }

            final String simpleName = SteamId.class.getSimpleName();

            return (this.stringCache = simpleName + "["
                    + ATTRIBUTE_NAME_XUID
                    + "="
                    + this.xuid
                    + ", "
                    + ATTRIBUTE_NAME_UNIVERSE
                    + "="
                    + this.universe
                    + ", "
                    + ATTRIBUTE_NAME_INSTANCE
                    + "="
                    + this.instance
                    + ", "
                    + ATTRIBUTE_NAME_ACCOUNT
                    + "="
                    + this.account
                    + "]");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    @Contract(pure = true)
    public SteamId clone() {
        return new SteamId(this);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-32 identifier isn't valid.</li>
     * </ul>
     *
     * @param xuid          account type-32 identifier, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final SteamId defaultValue
    ) {
        if (!USteamId.isSteamXuidValid(xuid)) {
            return defaultValue;
        }

        return new SteamId(xuid);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-32 identifier isn't valid.</li>
     * </ul>
     *
     * @param xuid                  account type-32 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamXuidOrNull(xuid), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteamXuidOrElse(Integer, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-32 identifier isn't valid.</li>
     * </ul>
     *
     * @param xuid                  account type-32 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final Integer xuid,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamXuidOrElse(xuid, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteamXuidOrElse(Integer, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-32 identifier isn't valid.</li>
     * </ul>
     *
     * @param xuid  account type-32 identifier, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamXuidOrNull(
            @Nullable
            final Integer xuid
    ) {
        return fromSteamXuidOrElse(xuid, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-32 identifier isn't valid.</li>
     * </ul>
     *
     * @param xuid          account type-32 identifier, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final String xuid,

            @Nullable
            final SteamId defaultValue
    ) {
        try {
            //noinspection DataFlowIssue
            final int id = Integer.parseUnsignedInt(xuid);
            return fromSteamXuidOrElse(id, defaultValue);
        } catch (final NumberFormatException ignored) {
        }

        return defaultValue;
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-32 identifier isn't valid.</li>
     * </ul>
     *
     * @param xuid                  account type-32 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final String xuid,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamXuidOrNull(xuid), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteamXuidOrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-32 identifier isn't valid.</li>
     * </ul>
     *
     * @param xuid                  account type-32 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamXuidOrElse(
            @Nullable
            final String xuid,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamXuidOrElse(xuid, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-32 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteamXuidOrElse(String, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-32 identifier isn't valid.</li>
     * </ul>
     *
     * @param xuid  account type-32 identifier, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamXuidOrNull(
            @Nullable
            final String xuid
    ) {
        return fromSteamXuidOrElse(xuid, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-64 identifier isn't valid.</li>
     * </ul>
     *
     * @param id64          account type-64 identifier, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final Long id64,

            @Nullable
            final SteamId defaultValue
    ) {
        if (!USteamId.isSteamId64Valid(id64)) {
            return defaultValue;
        }

        final int xuid = USteamBit.getAccountXuid(id64);
        final int universe = USteamBit.getAccountUniverse(id64);
        final int instance = USteamBit.getAccountInstance(id64);
        final int account = USteamBit.getAccountType(id64);

        return new SteamId(xuid, universe, instance, account);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-64 identifier isn't valid.</li>
     * </ul>
     *
     * @param id64                  account type-64 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final Long id64,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteam64OrNull(id64), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteam64OrElse(Long, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-64 identifier isn't valid.</li>
     * </ul>
     *
     * @param id64                  account type-64 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final Long id64,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteam64OrElse(id64, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-64 identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromSteam64OrElse(Long, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-64 identifier isn't valid.</li>
     * </ul>
     *
     * @param id64  account type-64 identifier, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteam64OrNull(
            @Nullable
            final Long id64
    ) {
        return fromSteam64OrElse(id64, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-64 identifier isn't valid.</li>
     * </ul>
     *
     * @param id64          account type-64 identifier, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteam64OrElse(
            @Nullable
            String id64,

            @Nullable
            final SteamId defaultValue
    ) {
        if (id64 == null) {
            return defaultValue;
        }

        id64 = id64.trim();
        try {
            final long val = Long.parseUnsignedLong(id64);
            return fromSteam64OrElse(id64, defaultValue);
        } catch (final NumberFormatException ignored) {
        }

        return defaultValue;
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-64 identifier isn't valid.</li>
     * </ul>
     *
     * @param id64                  account type-64 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final String id64,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteam64OrNull(id64), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-64 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteam64OrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-64 identifier isn't valid.</li>
     * </ul>
     *
     * @param id64                  account type-64 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteam64OrElse(
            @Nullable
            final String id64,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteam64OrElse(id64, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-64 identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromSteam64OrElse(String, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-64 identifier isn't valid.</li>
     * </ul>
     *
     * @param id64  account type-64 identifier, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteam64OrNull(
            @Nullable
            final String id64
    ) {
        return fromSteam64OrElse(id64, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-2 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-2 identifier isn't valid.</li>
     * </ul>
     *
     * @param id2           account type-2 identifier, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteam2OrElse(
            @Nullable
            String id2,

            @Nullable
            final SteamId defaultValue
    ) {
        if (id2 == null) {
            return defaultValue;
        }

        id2 = id2.trim();

        final Pattern pattern = USteamPattern.getId2Pattern();
        final Matcher matcher = pattern.matcher(id2);

        if (!matcher.matches()) {
            return defaultValue;
        }

        try {
            final String mId = matcher.group(USteamRegexGroup.ID);
            final String mAuth = matcher.group(USteamRegexGroup.AUTH);

            final int id = Integer.parseUnsignedInt(mId);
            final int auth = Integer.parseUnsignedInt(mAuth);

            return new SteamId((id << 1) | auth);
        } catch (NumberFormatException ignored) {
        }

        return defaultValue;
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-2 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-2 identifier isn't valid.</li>
     * </ul>
     *
     * @param id2                   account type-2 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteam2OrElse(
            @Nullable
            final String id2,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteam2OrNull(id2), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-2 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteam2OrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-2 identifier isn't valid.</li>
     * </ul>
     *
     * @param id2                   account type-2 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteam2OrElse(
            @Nullable
            final String id2,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteam2OrElse(id2, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-2 identifier
     * or return {@code null} on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-2 identifier isn't valid.</li>
     * </ul>
     *
     * @param id2   account type-2 identifier, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteam2OrNull(
            @Nullable
            final String id2
    ) {
        return fromSteam2OrElse(id2, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-3 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-3 identifier isn't valid.</li>
     * </ul>
     *
     * @param id3           account type-3 identifier, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteam3OrElse(
            @Nullable
            String id3,

            @Nullable
            final SteamId defaultValue
    ) {
        if (id3 == null) {
            return defaultValue;
        }

        id3 = id3.trim();

        final Pattern pattern = USteamPattern.getId3Pattern();
        final Matcher matcher = pattern.matcher(id3);

        if (!matcher.matches()) {
            return defaultValue;
        }

        try {
            final String mXuid = matcher.group(USteamRegexGroup.ID);
            final String mUniverse = matcher.group(USteamRegexGroup.UNIVERSE);
            final String mAccount = matcher.group(USteamRegexGroup.ACCOUNT);

            final int xuid = Integer.parseUnsignedInt(mXuid);
            final int universe = Integer.parseUnsignedInt(mUniverse);
            int instance = USteamInstance.DESKTOP;
            char account = mAccount.charAt(0);

            try {
                final String mInstance = matcher.group(USteamRegexGroup.INSTANCE);
                instance = Integer.parseUnsignedInt(mInstance);
            } catch (final NumberFormatException ignored) {
                switch (account) {
                    case USteamAccount.CLAN_CHAR:
                    case USteamAccount.CHAT_CHAR:
                    case USteamAccount.CLAN_CHAT_CHAR:
                    case USteamAccount.LOBBY_CHAT_CHAR:
                        instance = USteamInstance.ALL;
                        break;
                }
            }

            switch (account) {
                case USteamAccount.CLAN_CHAT_CHAR:
                case USteamAccount.LOBBY_CHAT_CHAR:
                    account = USteamAccount.CHAT_CHAR;
                    break;
            }

            return new SteamId(xuid, universe, instance, account);
        } catch (final NumberFormatException ignored) {
        }


        return defaultValue;
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-3 identifier
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-3 identifier isn't valid.</li>
     * </ul>
     *
     * @param id3                   account type-3 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteam3OrElse(
            @Nullable
            final String id3,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteam3OrNull(id3), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-3 identifier
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteam3OrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-3 identifier isn't valid.</li>
     * </ul>
     *
     * @param id3                   account type-3 identifier, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteam3OrElse(
            @Nullable
            final String id3,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteam3OrElse(id3, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam account type-3 identifier
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromSteam3OrElse(String, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Account type-3 identifier isn't valid.</li>
     * </ul>
     *
     * @param id3   account type-3 identifier, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteam3OrNull(
            @Nullable
            final String id3
    ) {
        return fromSteam3OrElse(id3, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam invite code
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Invite code isn't valid.</li>
     * </ul>
     *
     * @param code          invite code, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromInviteCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final SteamId defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(USteamInvite.toXuidOrNull(code), SteamId::new, defaultValue);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam invite code
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Invite code isn't valid.</li>
     * </ul>
     *
     * @param code                  invite code, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromInviteCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromInviteCodeOrNull(code), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam invite code
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromInviteCodeOrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Invite code isn't valid.</li>
     * </ul>
     *
     * @param code                  invite code, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromInviteCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromInviteCodeOrElse(code, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam invite code
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromInviteCodeOrElse(String, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Invite code isn't valid.</li>
     * </ul>
     *
     * @param code  invite code, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromInviteCodeOrNull(
            @Nullable
            final String code
    ) {
        return fromInviteCodeOrElse(code, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code isn't valid.</li>
     * </ul>
     *
     * @param code          CS:GO friend code, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromCsgoCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final SteamId defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(USteamCsgo.toXuidOrNull(code), SteamId::new, defaultValue);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code isn't valid.</li>
     * </ul>
     *
     * @param code                  CS:GO friend code, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromCsgoCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromCsgoCodeOrNull(code), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a CS:GO friend code
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromCsgoCodeOrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code isn't valid.</li>
     * </ul>
     *
     * @param code                  CS:GO friend code, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromCsgoCodeOrElse(
            @Nullable
            final String code,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromCsgoCodeOrElse(code, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a CS:GO friend code
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromCsgoCodeOrElse(String, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>CS:GO friend code isn't valid.</li>
     * </ul>
     *
     * @param code  CS:GO friend code, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromCsgoCodeOrNull(
            @Nullable
            final String code
    ) {
        return fromCsgoCodeOrElse(code, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam /profiles/ URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>/profiles/ URL isn't valid.</li>
     * </ul>
     *
     * @param url           /profiles/ URL, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamProfileUrlOrElse(
            @Nullable
            String url,

            @Nullable
            final SteamId defaultValue
    ) {
        if (url == null) {
            return defaultValue;
        }

        url = url.trim();

        final Pattern pattern = USteamPattern.getProfileUrlPattern();
        final Matcher matcher = pattern.matcher(url);

        if (!matcher.matches()) {
            return defaultValue;
        }

        final String mId = matcher.group(USteamRegexGroup.ID);
        try {
            final long id64 = Long.parseUnsignedLong(mId);
            return fromSteam64OrElse(id64, defaultValue);
        } catch (final NumberFormatException ignored) {
        }

        return fromSteam3OrElse(mId, defaultValue);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam /profiles/ URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>/profiles/ URL isn't valid.</li>
     * </ul>
     *
     * @param url                   /profiles/ URL, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamProfileUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamProfileUrlOrNull(url), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam /profiles/ URL
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteamProfileUrlOrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>/profiles/ URL isn't valid.</li>
     * </ul>
     *
     * @param url                   /profiles/ URL, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamProfileUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamProfileUrlOrElse(url, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam /profiles/ URL
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromSteamProfileUrlOrElse(String, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>/profiles/ URL isn't valid.</li>
     * </ul>
     *
     * @param url   /profiles/ URL, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = false)
    public static SteamId fromSteamProfileUrlOrNull(
            @Nullable
            final String url
    ) {
        return fromSteamProfileUrlOrElse(url, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam /user/ URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>/user/ URL isn't valid.</li>
     * </ul>
     *
     * @param url           /user/ URL, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamUserUrlOrElse(
            @Nullable
            String url,

            @Nullable
            final SteamId defaultValue
    ) {
        if (url == null) {
            return defaultValue;
        }

        url = url.trim();

        final Pattern pattern = USteamPattern.getUserUrlPattern();
        final Matcher matcher = pattern.matcher(url);

        if (!matcher.matches()) {
            return defaultValue;
        }

        final String code = matcher.group(USteamRegexGroup.ID);
        return fromInviteCodeOrElse(code, defaultValue);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam /user/ URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>/user/ URL isn't valid.</li>
     * </ul>
     *
     * @param url                   /user/ URL, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamUserUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamUserUrlOrNull(url), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam /user/ URL
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteamUserUrlOrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>/user/ URL isn't valid.</li>
     * </ul>
     *
     * @param url                   /user/ URL, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamUserUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamUserUrlOrElse(url, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam /user/ URL
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromSteamUserUrlOrElse(String, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>/user/ URL isn't valid.</li>
     * </ul>
     *
     * @param url   /user/ URL, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamUserUrlOrNull(
            @Nullable
            final String url
    ) {
        return fromSteamUserUrlOrElse(url, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance from a Steam URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam URL isn't valid.</li>
     * </ul>
     *
     * @param url           Steam URL, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final SteamId defaultValue
    ) {
        final SteamId result = fromSteamProfileUrlOrNull(url);
        if (result != null) {
            return result;
        }

        return fromSteamUserUrlOrElse(url, defaultValue);
    }

    /**
     * Create a new {@link SteamId} instance from a Steam URL
     * or return a default value on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam URL isn't valid.</li>
     * </ul>
     *
     * @param url                   Steam URL, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamUrlOrNull(url), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance from a Steam URL
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteamUrlOrElse(String, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam URL isn't valid.</li>
     * </ul>
     *
     * @param url                   Steam URL, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamUrlOrElse(
            @Nullable
            final String url,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamUrlOrElse(url, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance from a Steam URL
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromSteamUrlOrElse(String, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam URL isn't valid.</li>
     * </ul>
     *
     * @param url   Steam URL, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamUrlOrNull(
            @Nullable
            final String url
    ) {
        return fromSteamUrlOrElse(url, (@Nullable SteamId) null);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam related object
     * or return a default value on failure.
     *
     * <p>Wraps all these methods in order:
     * <ul>
     *     <li>{@link #fromSteamXuidOrElse(Integer, SteamId)}</li>
     *     <li>{@link #fromSteam64OrElse(Long, SteamId)}</li>
     *     <li>{@link #fromSteamXuidOrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam64OrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam2OrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam3OrElse(String, SteamId)}</li>
     *     <li>{@link #fromInviteCodeOrElse(String, SteamId)}</li>
     *     <li>{@link #fromCsgoCodeOrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteamUrlOrElse(String, SteamId)}</li>
     * </ul>
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam related object doesn't match w/ any of methods.</li>
     * </ul>
     *
     * @param obj           Steam related object, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static SteamId fromSteamAnyOrElse(
            @Nullable
            final Object obj,

            @Nullable
            final SteamId defaultValue
    ) {
        if (obj == null) {
            return defaultValue;
        }

        if (obj instanceof Integer) {
            return fromSteamXuidOrElse((@Nullable Integer) obj, defaultValue);
        }

        if (obj instanceof Long) {
            return fromSteam64OrElse((@Nullable Long) obj, defaultValue);
        }

        if (obj instanceof String) {
            final String str = (String) obj;

            SteamId result = fromSteamXuidOrNull(str);
            if (result != null) {
                return result;
            }

            result = fromSteam64OrNull(str);
            if (result != null) {
                return result;
            }

            result = fromSteam2OrNull(str);
            if (result != null) {
                return result;
            }

            result = fromSteam3OrNull(str);
            if (result != null) {
                return result;
            }

            result = fromInviteCodeOrNull(str);
            if (result != null) {
                return result;
            }

            result = fromCsgoCodeOrNull(str);
            if (result != null) {
                return result;
            }

            result = fromSteamUrlOrNull(str);
            if (result != null) {
                return result;
            }
        }

        return defaultValue;
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam related object
     * or return a default value on failure.
     *
     * <p>Wraps all these methods in order:
     * <ul>
     *     <li>{@link #fromSteamXuidOrElse(Integer, SteamId)}</li>
     *     <li>{@link #fromSteam64OrElse(Long, SteamId)}</li>
     *     <li>{@link #fromSteamXuidOrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam64OrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam2OrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam3OrElse(String, SteamId)}</li>
     *     <li>{@link #fromInviteCodeOrElse(String, SteamId)}</li>
     *     <li>{@link #fromCsgoCodeOrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteamUrlOrElse(String, SteamId)}</li>
     * </ul>
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam related object doesn't match w/ any of methods.</li>
     * </ul>
     *
     * @param obj                   Steam related object, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(pure = false)
    public static SteamId fromSteamAnyOrElse(
            @Nullable
            final Object obj,

            @Nullable
            final Supplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromSteamAnyOrNull(obj), defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam related object
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromSteamAnyOrElse(Object, Supplier)}.
     *
     * <p>Wraps all these methods in order:
     * <ul>
     *     <li>{@link #fromSteamXuidOrElse(Integer, SteamId)}</li>
     *     <li>{@link #fromSteam64OrElse(Long, SteamId)}</li>
     *     <li>{@link #fromSteamXuidOrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam64OrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam2OrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam3OrElse(String, SteamId)}</li>
     *     <li>{@link #fromInviteCodeOrElse(String, SteamId)}</li>
     *     <li>{@link #fromCsgoCodeOrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteamUrlOrElse(String, SteamId)}</li>
     * </ul>
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam related object doesn't match w/ any of methods.</li>
     * </ul>
     *
     * @param obj                   Steam related object, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static SteamId fromSteamAnyOrElse(
            @Nullable
            final Object obj,

            @Nullable
            final VoidSupplier<@UnknownNullability SteamId> defaultValueSupplier
    ) {
        return fromSteamAnyOrElse(obj, (@Nullable Supplier<@UnknownNullability SteamId>) defaultValueSupplier);
    }

    /**
     * Create a new {@link SteamId} instance
     * from a Steam related object
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromSteamAnyOrElse(Object, SteamId)}
     * w/ {@code null} as the default value.
     *
     * <p>Wraps all these methods in order:
     * <ul>
     *     <li>{@link #fromSteamXuidOrElse(Integer, SteamId)}</li>
     *     <li>{@link #fromSteam64OrElse(Long, SteamId)}</li>
     *     <li>{@link #fromSteamXuidOrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam64OrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam2OrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteam3OrElse(String, SteamId)}</li>
     *     <li>{@link #fromInviteCodeOrElse(String, SteamId)}</li>
     *     <li>{@link #fromCsgoCodeOrElse(String, SteamId)}</li>
     *     <li>{@link #fromSteamUrlOrElse(String, SteamId)}</li>
     * </ul>
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Steam related object doesn't match w/ any of methods.</li>
     * </ul>
     *
     * @param obj   Steam related object, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static SteamId fromSteamAnyOrNull(
            @Nullable
            final Object obj
    ) {
        return fromSteamAnyOrElse(obj, (@Nullable SteamId) null);
    }

    /**
     * A builder class for creation of {@link SteamId} instance.
     */
    public static final class Builder implements Serializable, Cloneable {

        /**
         * An attribute name of this account type-32 identifier.
         */
        @NotNull
        public static final String ATTRIBUTE_NAME_XUID = "xuid";

        /**
         * An attribute name of this account universe type enum.
         */
        @NotNull
        public static final String ATTRIBUTE_NAME_E_UNIVERSE = "eUniverse";

        /**
         * An attribute name of this account instance type enum.
         */
        @NotNull
        public static final String ATTRIBUTE_NAME_E_INSTANCE = "eInstance";

        /**
         * An attribute name of this account type enum.
         */
        @NotNull
        public static final String ATTRIBUTE_NAME_E_ACCOUNT = "eAccount";

        /**
         * An attribute name of this account universe type identifier.
         */
        @NotNull
        public static final String ATTRIBUTE_NAME_I_UNIVERSE = "iUniverse";

        /**
         * An attribute name of this account instance type identifier.
         */
        @NotNull
        public static final String ATTRIBUTE_NAME_I_INSTANCE = "iInstance";

        /**
         * An attribute name of this account type identifier.
         */
        @NotNull
        public static final String ATTRIBUTE_NAME_I_ACCOUNT = "iAccount";

        /**
         * An attribute name of this account type character.
         */
        @NotNull
        public static final String ATTRIBUTE_NAME_C_ACCOUNT = "cAccount";

        /**
         * A simple name of this class.
         */
        @NotNull
        private static final String SIMPLE_NAME = SteamId.class.getSimpleName()
                + ":" + Builder.class.getSimpleName();

        /**
         * An account type-32 identifier.
         */
        @UnknownNullability
        private Integer xuid;

        /**
         * An account universe type enum.
         */
        @UnknownNullability
        private ESteamUniverse eUniverse;

        /**
         * An account instance type enum.
         */
        @UnknownNullability
        private ESteamInstance eInstance;

        /**
         * An account type enum.
         */
        @UnknownNullability
        private ESteamAccount eAccount;

        /**
         * An account universe type identifier.
         */
        @UnknownNullability
        private Integer iUniverse;

        /**
         * An account instance type identifier.
         */
        @UnknownNullability
        private Integer iInstance;

        /**
         * An account type identifier.
         */
        @UnknownNullability
        private Integer iAccount;

        /**
         * An account type character.
         */
        @UnknownNullability
        private Character cAccount;

        /**
         * Initialize a {@link SteamId.Builder} instance
         * w/ all fields being assigned to {@code null}.
         */
        @Contract(pure = true)
        public Builder() {
            this.xuid = null;
            this.eUniverse = null;
            this.eInstance = null;
            this.eAccount = null;
            this.iUniverse = null;
            this.iInstance = null;
            this.iAccount = null;
            this.cAccount = null;
        }

        /**
         * Initialize a {@link SteamId.Builder} instance.
         *
         * <p>Defines a copy constructor.
         *
         * @param that	instance to copy filed values from
         *
         * @throws NullPointerException if that instance is {@code null}
         */
        @Contract(value = "null -> fail", pure = true)
        private Builder(
                @UnknownNullability
                final Builder that
        ) {
            this.xuid = that.xuid;
            this.eUniverse = that.eUniverse;
            this.eInstance = that.eInstance;
            this.eAccount = that.eAccount;
            this.iUniverse = that.iUniverse;
            this.iInstance = that.iInstance;
            this.iAccount = that.iAccount;
            this.cAccount = that.cAccount;
        }

        /**
         * Create a new {@link SteamId} instance.
         *
         * @return  new {@link SteamId} instance
         */
        @NotNull
        @Contract(value = "-> new", pure = true)
        public SteamId build() {
            final ESteamUniverse universe = ESteamUniverse.fromIdOrElse(this.iUniverse, this.eUniverse);
            final ESteamInstance instance = ESteamInstance.fromIdOrElse(this.iInstance, this.eInstance);

            ESteamAccount account = ESteamAccount.fromIdOrElse(this.iAccount, this.eAccount);
            account = ESteamAccount.fromCharOrElse(this.cAccount, account);

            return new SteamId(this.xuid, universe, instance, account);
        }

        /**
         * Check if this account type-32 identifier isn't null.
         *
         * @return  {@code true} if not null
         *          or {@code false} otherwise
         */
        @Contract(pure = true)
        public boolean hasXuid() {
            return this.xuid != null;
        }

        /**
         * Check if this account universe type enum isn't null.
         *
         * @return  {@code true} if not null
         *          or {@code false} otherwise
         */
        @Contract(pure = true)
        public boolean hasUniverseType() {
            return this.eUniverse != null;
        }

        /**
         * Check if this account instance type enum isn't null.
         *
         * @return  {@code true} if not null
         *          or {@code false} otherwise
         */
        @Contract(pure = true)
        public boolean hasInstanceType() {
            return this.eInstance != null;
        }

        /**
         * Check if this account type enum isn't null.
         *
         * @return  {@code true} if not null
         *          or {@code false} otherwise
         */
        @Contract(pure = true)
        public boolean hasAccountType() {
            return this.eAccount != null;
        }

        /**
         * Check if this account universe type identifier isn't null.
         *
         * @return  {@code true} if not null
         *          or {@code false} otherwise
         */
        @Contract(pure = true)
        public boolean hasIntUniverseType() {
            return this.iUniverse != null;
        }

        /**
         * Check if this account instance type identifier isn't null.
         *
         * @return  {@code true} if not null
         *          or {@code false} otherwise
         */
        @Contract(pure = true)
        public boolean hasIntInstanceType() {
            return this.iInstance != null;
        }

        /**
         * Check if this account type identifier isn't null.
         *
         * @return  {@code true} if not null
         *          or {@code false} otherwise
         */
        @Contract(pure = true)
        public boolean hasIntAccountType() {
            return this.iAccount != null;
        }

        /**
         * Check if this account type character isn't null.
         *
         * @return  {@code true} if not null
         *          or {@code false} otherwise
         */
        @Contract(pure = true)
        public boolean hasCharAccountType() {
            return this.cAccount != null;
        }

        /**
         * Get this account type-32 identifier.
         *
         * @return  account type-32 identifier
         */
        @UnknownNullability
        @Contract(pure = true)
        public Integer getXuid() {
            return this.xuid;
        }

        /**
         * Get this account universe type enum.
         *
         * @return  account universe type enum
         */
        @UnknownNullability
        @Contract(pure = true)
        public ESteamUniverse getUniverseType() {
            return this.eUniverse;
        }

        /**
         * Get this account instance type enum.
         *
         * @return  account instance type enum
         */
        @UnknownNullability
        @Contract(pure = true)
        public ESteamInstance getInstanceType() {
            return this.eInstance;
        }

        /**
         * Get this account type enum.
         *
         * @return  account type enum
         */
        @UnknownNullability
        @Contract(pure = true)
        public ESteamAccount getAccountType() {
            return this.eAccount;
        }

        /**
         * Get this account universe type identifier.
         *
         * @return  account universe type identifier
         */
        @UnknownNullability
        @Contract(pure = true)
        public Integer getIntUniverseType() {
            return this.iUniverse;
        }

        /**
         * Get this account instance type identifier.
         *
         * @return  account instance type identifier
         */
        @UnknownNullability
        @Contract(pure = true)
        public Integer getIntInstanceType() {
            return this.iInstance;
        }

        /**
         * Get this account type identifier.
         *
         * @return  account type identifier
         */
        @UnknownNullability
        @Contract(pure = true)
        public Integer getIntAccountType() {
            return iAccount;
        }

        /**
         * Get this account type character.
         *
         * @return  account type character
         */
        @UnknownNullability
        @Contract(pure = true)
        public Character getCharAccountType() {
            return this.cAccount;
        }

        /**
         * Set this account type-32 identifier.
         *
         * @param xuid  account type-32 identifier
         *
         * @return  this instance
         */
        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setXuid(
                @Nullable
                final Integer xuid
        ) {
            this.xuid = xuid;
            return this;
        }

        /**
         * Set this account universe type enum.
         *
         * @param universe  account universe type enum
         *
         * @return  this instance
         */
        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setUniverseType(
                @Nullable
                final ESteamUniverse universe
        ) {
            this.eUniverse = universe;
            this.iUniverse = null;
            return this;
        }

        /**
         * Set this account universe type identifier.
         *
         * @param universe  account universe type identifier
         *
         * @return  this instance
         */
        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setUniverseType(
                @Nullable
                final Integer universe
        ) {
            this.eUniverse = null;
            this.iUniverse = universe;
            return this;
        }

        /**
         * Set this account instance type enum.
         *
         * @param instance  account instance type enum
         *
         * @return  this instance
         */
        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setInstanceType(
                @Nullable
                final ESteamInstance instance
        ) {
            this.eInstance = instance;
            this.iInstance = null;
            return this;
        }

        /**
         * Set this account instance type identifier.
         *
         * @param instance  account instance type identifier
         *
         * @return  this instance
         */
        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setInstanceType(
                @Nullable
                final Integer instance
        ) {
            this.eInstance = null;
            this.iInstance = instance;
            return this;
        }

        /**
         * Set this account type enum.
         *
         * @param account   account type enum
         *
         * @return  this instance
         */
        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setAccountType(
                @Nullable
                final ESteamAccount account
        ) {
            this.eAccount = account;
            this.iAccount = null;
            this.cAccount = null;
            return this;
        }

        /**
         * Set this account type identifier.
         *
         * @param account   account type identifier
         *
         * @return  this instance
         */
        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setAccountType(
                @Nullable
                final Integer account
        ) {
            this.eAccount = null;
            this.iAccount = account;
            this.cAccount = null;
            return this;
        }

        /**
         * Set this account type character.
         *
         * @param account   account type character
         *
         * @return  this instance
         */
        @NotNull
        @Contract(value = "_ -> this", pure = false)
        public Builder setAccountType(
                @Nullable
                final Character account
        ) {
            this.eAccount = null;
            this.iAccount = null;
            this.cAccount = account;
            return this;
        }

        /**
         * Create a new {@link SteamId.Builder} instance
         * w/ different {@link #xuid} value.
         *
         * @param xuid  new account type-32 identifier
         *
         * @return  new instance or this if values are equal
         */
        @NotNull
        @Contract(pure = true)
        public Builder withXuid(
                @Nullable
                final Integer xuid
        ) {
            if (Objects.equals(this.xuid, xuid)) {
                return this;
            }

            return this.clone()
                    .setXuid(xuid);
        }

        /**
         * Create a new {@link SteamId.Builder} instance
         * w/ different {@link #eUniverse} value.
         *
         * @param universe  new account universe type enum
         *
         * @return  new instance or this if values are equal
         */
        @NotNull
        @Contract(pure = true)
        public Builder withUniverseType(
                @Nullable
                final ESteamUniverse universe
        ) {
            if (this.eUniverse == universe) {
                return this;
            }

            return this.clone()
                    .setUniverseType(universe);
        }

        /**
         * Create a new {@link SteamId.Builder} instance
         * w/ different {@link #iUniverse} value.
         *
         * @param universe  new account universe type identifier
         *
         * @return  new instance or this if values are equal
         */
        @NotNull
        @Contract(pure = true)
        public Builder withUniverseType(
                @Nullable
                final Integer universe
        ) {
            if (Objects.equals(this.iUniverse, universe)) {
                return this;
            }

            return this.clone()
                    .setUniverseType(universe);
        }

        /**
         * Create a new {@link SteamId.Builder} instance
         * w/ different {@link #eInstance} value.
         *
         * @param instance  new account instance type enum
         *
         * @return  new instance or this if values are equal
         */
        @NotNull
        @Contract(pure = true)
        public Builder withInstanceType(
                @Nullable
                final ESteamInstance instance
        ) {
            if (this.eInstance == instance) {
                return this;
            }

            return this.clone()
                    .setInstanceType(instance);
        }

        /**
         * Create a new {@link SteamId.Builder} instance
         * w/ different {@link #iInstance} value.
         *
         * @param instance  new account instance type identifier
         *
         * @return  new instance or this if values are equal
         */
        @NotNull
        @Contract(pure = true)
        public Builder withInstanceType(
                @Nullable
                final Integer instance
        ) {
            if (Objects.equals(this.iInstance, instance)) {
                return this;
            }

            return this.clone()
                    .setInstanceType(instance);
        }

        /**
         * Create a new {@link SteamId.Builder} instance
         * w/ different {@link #eAccount} value.
         *
         * @param account   new account type enum
         *
         * @return  new instance or this if values are equal
         */
        @NotNull
        @Contract(pure = true)
        public Builder withAccountType(
                @Nullable
                final ESteamAccount account
        ) {
            if (this.eAccount == account) {
                return this;
            }

            return this.clone()
                    .setAccountType(account);
        }

        /**
         * Create a new {@link SteamId.Builder} instance
         * w/ different {@link #iAccount} value.
         *
         * @param account   new account type identifier
         *
         * @return  new instance or this if values are equal
         */
        @NotNull
        @Contract(pure = true)
        public Builder withAccountType(
                @Nullable
                final Integer account
        ) {
            if (Objects.equals(this.iAccount, account)) {
                return this;
            }

            return this.clone()
                    .setAccountType(account);
        }

        /**
         * Create a new {@link SteamId.Builder} instance
         * w/ different {@link #cAccount} value.
         *
         * @param account   new account type character
         *
         * @return  new instance or this if values are equal
         */
        @NotNull
        @Contract(pure = true)
        public Builder withAccountType(
                @Nullable
                final Character account
        ) {
            if (Objects.equals(this.cAccount, account)) {
                return this;
            }

            return this.clone()
                    .setAccountType(account);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return Objects.hash(
                    this.xuid,
                    this.eUniverse,
                    this.eInstance,
                    this.eAccount,
                    this.iUniverse,
                    this.iInstance,
                    this.iAccount,
                    this.cAccount
            );
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Contract(pure = true)
        public boolean equals(
                @Nullable
                final Object obj
        ) {
            if (this == obj) {
                return true;
            }

            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }

            Builder that = (Builder) obj;
            return Objects.equals(this.xuid, that.xuid)
                    && this.eUniverse == that.eUniverse
                    && this.eInstance == that.eInstance
                    && this.eAccount == that.eAccount
                    && Objects.equals(this.iUniverse, that.iUniverse)
                    && Objects.equals(this.iInstance, that.iInstance)
                    && Objects.equals(this.iAccount, that.iAccount)
                    && Objects.equals(this.cAccount, that.cAccount);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NotNull
        @Contract(pure = true)
        public String toString() {
            return SIMPLE_NAME + "["
                    + ATTRIBUTE_NAME_XUID
                    + "="
                    + this.xuid
                    + ", "
                    + ATTRIBUTE_NAME_E_UNIVERSE
                    + "="
                    + this.eUniverse
                    + ", "
                    + ATTRIBUTE_NAME_E_INSTANCE
                    + "="
                    + this.eInstance
                    + ", "
                    + ATTRIBUTE_NAME_E_ACCOUNT
                    + "="
                    + this.eAccount
                    + ", "
                    + ATTRIBUTE_NAME_I_UNIVERSE
                    + "="
                    + this.iUniverse
                    + ", "
                    + ATTRIBUTE_NAME_I_INSTANCE
                    + "="
                    + this.iInstance
                    + ", "
                    + ATTRIBUTE_NAME_I_ACCOUNT
                    + "="
                    + this.iAccount
                    + ", "
                    + ATTRIBUTE_NAME_C_ACCOUNT
                    + "="
                    + this.cAccount
                    + "]";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NotNull
        @Contract(value = "-> new", pure = true)
        public Builder clone() {
            return new Builder(this);
        }
    }
}
