/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An access key for the storage account.
 */
public class StorageAccountKey {
    /**
     * Name of the key.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String keyName;

    /**
     * Base 64 encoded value of the key.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String value;

    /**
     * Permissions for the key. Possible values include: 'READ', 'FULL'.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private KeyPermission permissions;

    /**
     * Get the keyName value.
     *
     * @return the keyName value
     */
    public String keyName() {
        return this.keyName;
    }

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public String value() {
        return this.value;
    }

    /**
     * Get the permissions value.
     *
     * @return the permissions value
     */
    public KeyPermission permissions() {
        return this.permissions;
    }

}