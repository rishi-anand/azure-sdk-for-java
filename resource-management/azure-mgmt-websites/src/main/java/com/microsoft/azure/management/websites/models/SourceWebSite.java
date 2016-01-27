/**
 * 
 * Copyright (c) Microsoft and contributors.  All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

// Warning: This code was generated by a tool.
// 
// Changes to this file may cause incorrect behavior and will be lost if the
// code is regenerated.

package com.microsoft.azure.management.websites.models;

/**
* Source site for cloning
*/
public class SourceWebSite {
    private String location;
    
    /**
    * Optional. Locatio (georegion) of source site
    * @return The Location value.
    */
    public String getLocation() {
        return this.location;
    }
    
    /**
    * Optional. Locatio (georegion) of source site
    * @param locationValue The Location value.
    */
    public void setLocation(final String locationValue) {
        this.location = locationValue;
    }
    
    private String name;
    
    /**
    * Optional. Name of source site
    * @return The Name value.
    */
    public String getName() {
        return this.name;
    }
    
    /**
    * Optional. Name of source site
    * @param nameValue The Name value.
    */
    public void setName(final String nameValue) {
        this.name = nameValue;
    }
    
    private String resourceGroupName;
    
    /**
    * Optional. Name of resource group of source site
    * @return The ResourceGroupName value.
    */
    public String getResourceGroupName() {
        return this.resourceGroupName;
    }
    
    /**
    * Optional. Name of resource group of source site
    * @param resourceGroupNameValue The ResourceGroupName value.
    */
    public void setResourceGroupName(final String resourceGroupNameValue) {
        this.resourceGroupName = resourceGroupNameValue;
    }
    
    private String slot;
    
    /**
    * Optional. Name of slot of source site
    * @return The Slot value.
    */
    public String getSlot() {
        return this.slot;
    }
    
    /**
    * Optional. Name of slot of source site
    * @param slotValue The Slot value.
    */
    public void setSlot(final String slotValue) {
        this.slot = slotValue;
    }
    
    private String subscriptionId;
    
    /**
    * Optional. Subscription Id of source site
    * @return The SubscriptionId value.
    */
    public String getSubscriptionId() {
        return this.subscriptionId;
    }
    
    /**
    * Optional. Subscription Id of source site
    * @param subscriptionIdValue The SubscriptionId value.
    */
    public void setSubscriptionId(final String subscriptionIdValue) {
        this.subscriptionId = subscriptionIdValue;
    }
}