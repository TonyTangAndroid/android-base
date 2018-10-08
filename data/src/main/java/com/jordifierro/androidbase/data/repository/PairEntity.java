/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jordifierro.androidbase.data.repository;

import java.util.Objects;

/**
 * Container to ease passing around a tuple of two objects. This object provides a sensible
 * implementation of equals(), returning true if equals() is true on each of the contained
 * objects.
 */
public class PairEntity<F, S> {
    public final F first;
    public final S second;

    /**
     * Constructor for a PairEntity.
     *
     * @param first  the first object in the PairEntity
     * @param second the second object in the PairEntity
     */
    public PairEntity(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Convenience method for creating an appropriately typed pair.
     *
     * @param a   the first object in the PairEntity
     * @param b   the second object in the PairEntity
     * @param <A> the type of the first object in the PairEntity
     * @param <B> the type of the second object in the PairEntity
     * @return a Pair that is templatized with the types of a and b
     */
    public static <A, B> PairEntity<A, B> create(A a, B b) {
        return new PairEntity<>(a, b);
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link PairEntity} to which this one is to be checked for equality
     * @return <code>true</code> if the underlying objects of the Pair are both considered
     * equal;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PairEntity)) {
            return false;
        }
        PairEntity<?, ?> p = (PairEntity<?, ?>) o;
        return Objects.equals(p.first, first) && Objects.equals(p.second, second);
    }

    /**
     * Compute a hashCode using the hash codes of the underlying objects.
     *
     * @return a hashCode of the PairEntity.
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    /**
     * Returns a String of the value of the first object plus the value of the second object.
     *
     * @return the value of the first object plus the value of the second object.
     */
    @Override
    public String toString() {
        return "{" + String.valueOf(first) + ":" + String.valueOf(second) + "}";
    }
}
