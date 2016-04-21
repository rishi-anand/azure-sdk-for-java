/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.datalake.analytics.models;


/**
 * A Data Lake Analytics catalog U-SQL assembly file information item.
 */
public class USqlAssemblyFileInfo {
    /**
     * Gets or sets the assembly file type. Possible values include:
     * 'Assembly', 'Resource'.
     */
    private FileType type;

    /**
     * Gets or sets the the original path to the assembly file.
     */
    private String originalPath;

    /**
     * Gets or sets the the content path to the assembly file.
     */
    private String contentPath;

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public FileType getType() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     */
    public void setType(FileType type) {
        this.type = type;
    }

    /**
     * Get the originalPath value.
     *
     * @return the originalPath value
     */
    public String getOriginalPath() {
        return this.originalPath;
    }

    /**
     * Set the originalPath value.
     *
     * @param originalPath the originalPath value to set
     */
    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    /**
     * Get the contentPath value.
     *
     * @return the contentPath value
     */
    public String getContentPath() {
        return this.contentPath;
    }

    /**
     * Set the contentPath value.
     *
     * @param contentPath the contentPath value to set
     */
    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

}