/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation.api;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureServiceResponseBuilder;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.rest.ServiceCall;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceResponse;
import com.microsoft.rest.ServiceResponseCallback;
import com.microsoft.rest.ServiceResponseEmptyCallback;
import com.microsoft.rest.Validator;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;
import retrofit2.Response;

/**
 * An instance of this class provides access to all the operations defined
 * in Resources.
 */
public final class ResourcesInner {
    /** The Retrofit service to perform REST calls. */
    private ResourcesService service;
    /** The service client containing this operation class. */
    private ResourceManagementClientImpl client;

    /**
     * Initializes an instance of ResourcesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public ResourcesInner(Retrofit retrofit, ResourceManagementClientImpl client) {
        this.service = retrofit.create(ResourcesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for Resources to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ResourcesService {
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("subscriptions/{subscriptionId}/resourceGroups/{sourceResourceGroupName}/moveResources")
        Call<ResponseBody> moveResources(@Path("sourceResourceGroupName") String sourceResourceGroupName, @Path("subscriptionId") String subscriptionId, @Body ResourcesMoveInfoInner parameters, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("subscriptions/{subscriptionId}/resourceGroups/{sourceResourceGroupName}/moveResources")
        Call<ResponseBody> beginMoveResources(@Path("sourceResourceGroupName") String sourceResourceGroupName, @Path("subscriptionId") String subscriptionId, @Body ResourcesMoveInfoInner parameters, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("subscriptions/{subscriptionId}/resources")
        Call<ResponseBody> list(@Path("subscriptionId") String subscriptionId, @Query("$filter") String filter, @Query("$top") Integer top, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @HEAD("subscriptions/{subscriptionId}/resourcegroups/{resourceGroupName}/providers/{resourceProviderNamespace}/{parentResourcePath}/{resourceType}/{resourceName}")
        Call<Void> checkExistence(@Path("resourceGroupName") String resourceGroupName, @Path("resourceProviderNamespace") String resourceProviderNamespace, @Path("parentResourcePath") String parentResourcePath, @Path("resourceType") String resourceType, @Path("resourceName") String resourceName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @HTTP(path = "subscriptions/{subscriptionId}/resourcegroups/{resourceGroupName}/providers/{resourceProviderNamespace}/{parentResourcePath}/{resourceType}/{resourceName}", method = "DELETE", hasBody = true)
        Call<ResponseBody> delete(@Path("resourceGroupName") String resourceGroupName, @Path("resourceProviderNamespace") String resourceProviderNamespace, @Path("parentResourcePath") String parentResourcePath, @Path("resourceType") String resourceType, @Path("resourceName") String resourceName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @PUT("subscriptions/{subscriptionId}/resourcegroups/{resourceGroupName}/providers/{resourceProviderNamespace}/{parentResourcePath}/{resourceType}/{resourceName}")
        Call<ResponseBody> createOrUpdate(@Path("resourceGroupName") String resourceGroupName, @Path("resourceProviderNamespace") String resourceProviderNamespace, @Path("parentResourcePath") String parentResourcePath, @Path("resourceType") String resourceType, @Path("resourceName") String resourceName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Body GenericResourceInner parameters, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("subscriptions/{subscriptionId}/resourcegroups/{resourceGroupName}/providers/{resourceProviderNamespace}/{parentResourcePath}/{resourceType}/{resourceName}")
        Call<ResponseBody> get(@Path("resourceGroupName") String resourceGroupName, @Path("resourceProviderNamespace") String resourceProviderNamespace, @Path("parentResourcePath") String parentResourcePath, @Path("resourceType") String resourceType, @Path("resourceName") String resourceName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @GET
        Call<ResponseBody> listNext(@Url String nextPageLink, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Begin moving resources.To determine whether the operation has finished processing the request, call GetLongRunningOperationStatus.
     *
     * @param sourceResourceGroupName Source resource group name.
     * @param parameters move resources' parameters.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @throws InterruptedException exception thrown when long running operation is interrupted
     * @return the ServiceResponse object if successful.
     */
    public ServiceResponse<Void> moveResources(String sourceResourceGroupName, ResourcesMoveInfoInner parameters) throws CloudException, IOException, IllegalArgumentException, InterruptedException {
        if (sourceResourceGroupName == null) {
            throw new IllegalArgumentException("Parameter sourceResourceGroupName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter parameters is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        Validator.validate(parameters);
        Response<ResponseBody> result = service.moveResources(sourceResourceGroupName, this.client.subscriptionId(), parameters, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent()).execute();
        return client.getAzureClient().getPostOrDeleteResult(result, new TypeToken<Void>() { }.getType());
    }

    /**
     * Begin moving resources.To determine whether the operation has finished processing the request, call GetLongRunningOperationStatus.
     *
     * @param sourceResourceGroupName Source resource group name.
     * @param parameters move resources' parameters.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    public ServiceCall moveResourcesAsync(String sourceResourceGroupName, ResourcesMoveInfoInner parameters, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (sourceResourceGroupName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter sourceResourceGroupName is required and cannot be null."));
        }
        if (this.client.subscriptionId() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null."));
        }
        if (parameters == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter parameters is required and cannot be null."));
        }
        if (this.client.apiVersion() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null."));
        }
        Validator.validate(parameters, serviceCallback);
        Call<ResponseBody> call = service.moveResources(sourceResourceGroupName, this.client.subscriptionId(), parameters, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent());
        final ServiceCall serviceCall = new ServiceCall(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                serviceCallback.failure(t);
            }
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                client.getAzureClient().getPostOrDeleteResultAsync(response, new TypeToken<Void>() { }.getType(), serviceCall, serviceCallback);
            }
        });
        return serviceCall;
    }

    /**
     * Begin moving resources.To determine whether the operation has finished processing the request, call GetLongRunningOperationStatus.
     *
     * @param sourceResourceGroupName Source resource group name.
     * @param parameters move resources' parameters.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the {@link ServiceResponse} object if successful.
     */
    public ServiceResponse<Void> beginMoveResources(String sourceResourceGroupName, ResourcesMoveInfoInner parameters) throws CloudException, IOException, IllegalArgumentException {
        if (sourceResourceGroupName == null) {
            throw new IllegalArgumentException("Parameter sourceResourceGroupName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter parameters is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        Validator.validate(parameters);
        Call<ResponseBody> call = service.beginMoveResources(sourceResourceGroupName, this.client.subscriptionId(), parameters, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent());
        return beginMoveResourcesDelegate(call.execute());
    }

    /**
     * Begin moving resources.To determine whether the operation has finished processing the request, call GetLongRunningOperationStatus.
     *
     * @param sourceResourceGroupName Source resource group name.
     * @param parameters move resources' parameters.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link Call} object
     */
    public ServiceCall beginMoveResourcesAsync(String sourceResourceGroupName, ResourcesMoveInfoInner parameters, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (sourceResourceGroupName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter sourceResourceGroupName is required and cannot be null."));
            return null;
        }
        if (this.client.subscriptionId() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null."));
            return null;
        }
        if (parameters == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter parameters is required and cannot be null."));
            return null;
        }
        if (this.client.apiVersion() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null."));
            return null;
        }
        Validator.validate(parameters, serviceCallback);
        Call<ResponseBody> call = service.beginMoveResources(sourceResourceGroupName, this.client.subscriptionId(), parameters, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent());
        final ServiceCall serviceCall = new ServiceCall(call);
        call.enqueue(new ServiceResponseCallback<Void>(serviceCallback) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serviceCallback.success(beginMoveResourcesDelegate(response));
                } catch (CloudException | IOException exception) {
                    serviceCallback.failure(exception);
                }
            }
        });
        return serviceCall;
    }

    private ServiceResponse<Void> beginMoveResourcesDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<Void, CloudException>(this.client.restClient().mapperAdapter())
                .register(202, new TypeToken<Void>() { }.getType())
                .register(204, new TypeToken<Void>() { }.getType())
                .build(response);
    }

    /**
     * Get all of the resources under a subscription.
     *
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the List&lt;GenericResourceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public ServiceResponse<PagedList<GenericResourceInner>> list() throws CloudException, IOException, IllegalArgumentException {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        final String filter = null;
        final Integer top = null;
        Call<ResponseBody> call = service.list(this.client.subscriptionId(), filter, top, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent());
        ServiceResponse<PageImpl<GenericResourceInner>> response = listDelegate(call.execute());
        PagedList<GenericResourceInner> result = new PagedList<GenericResourceInner>(response.getBody()) {
            @Override
            public Page<GenericResourceInner> nextPage(String nextPageLink) throws CloudException, IOException {
                return listNext(nextPageLink).getBody();
            }
        };
        return new ServiceResponse<>(result, response.getResponse());
    }

    /**
     * Get all of the resources under a subscription.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link Call} object
     */
    public ServiceCall listAsync(final ListOperationCallback<GenericResourceInner> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (this.client.subscriptionId() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null."));
            return null;
        }
        if (this.client.apiVersion() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null."));
            return null;
        }
        final String filter = null;
        final Integer top = null;
        Call<ResponseBody> call = service.list(this.client.subscriptionId(), filter, top, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent());
        final ServiceCall serviceCall = new ServiceCall(call);
        call.enqueue(new ServiceResponseCallback<List<GenericResourceInner>>(serviceCallback) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ServiceResponse<PageImpl<GenericResourceInner>> result = listDelegate(response);
                    serviceCallback.load(result.getBody().getItems());
                    if (result.getBody().getNextPageLink() != null
                            && serviceCallback.progress(result.getBody().getItems()) == ListOperationCallback.PagingBahavior.CONTINUE) {
                        listNextAsync(result.getBody().getNextPageLink(), serviceCall, serviceCallback);
                    } else {
                        serviceCallback.success(new ServiceResponse<>(serviceCallback.get(), result.getResponse()));
                    }
                } catch (CloudException | IOException exception) {
                    serviceCallback.failure(exception);
                }
            }
        });
        return serviceCall;
    }

    /**
     * Get all of the resources under a subscription.
     *
     * @param filter The filter to apply on the operation.
     * @param top Query parameters. If null is passed returns all resource groups.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the List&lt;GenericResourceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public ServiceResponse<PagedList<GenericResourceInner>> list(final String filter, final Integer top) throws CloudException, IOException, IllegalArgumentException {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        Call<ResponseBody> call = service.list(this.client.subscriptionId(), filter, top, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent());
        ServiceResponse<PageImpl<GenericResourceInner>> response = listDelegate(call.execute());
        PagedList<GenericResourceInner> result = new PagedList<GenericResourceInner>(response.getBody()) {
            @Override
            public Page<GenericResourceInner> nextPage(String nextPageLink) throws CloudException, IOException {
                return listNext(nextPageLink).getBody();
            }
        };
        return new ServiceResponse<>(result, response.getResponse());
    }

    /**
     * Get all of the resources under a subscription.
     *
     * @param filter The filter to apply on the operation.
     * @param top Query parameters. If null is passed returns all resource groups.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link Call} object
     */
    public ServiceCall listAsync(final String filter, final Integer top, final ListOperationCallback<GenericResourceInner> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (this.client.subscriptionId() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null."));
            return null;
        }
        if (this.client.apiVersion() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null."));
            return null;
        }
        Call<ResponseBody> call = service.list(this.client.subscriptionId(), filter, top, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent());
        final ServiceCall serviceCall = new ServiceCall(call);
        call.enqueue(new ServiceResponseCallback<List<GenericResourceInner>>(serviceCallback) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ServiceResponse<PageImpl<GenericResourceInner>> result = listDelegate(response);
                    serviceCallback.load(result.getBody().getItems());
                    if (result.getBody().getNextPageLink() != null
                            && serviceCallback.progress(result.getBody().getItems()) == ListOperationCallback.PagingBahavior.CONTINUE) {
                        listNextAsync(result.getBody().getNextPageLink(), serviceCall, serviceCallback);
                    } else {
                        serviceCallback.success(new ServiceResponse<>(serviceCallback.get(), result.getResponse()));
                    }
                } catch (CloudException | IOException exception) {
                    serviceCallback.failure(exception);
                }
            }
        });
        return serviceCall;
    }

    private ServiceResponse<PageImpl<GenericResourceInner>> listDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<PageImpl<GenericResourceInner>, CloudException>(this.client.restClient().mapperAdapter())
                .register(200, new TypeToken<PageImpl<GenericResourceInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Checks whether resource exists.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param resourceProviderNamespace Resource identity.
     * @param parentResourcePath Resource identity.
     * @param resourceType Resource identity.
     * @param resourceName Resource identity.
     * @param apiVersion the String value
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the boolean object wrapped in {@link ServiceResponse} if successful.
     */
    public ServiceResponse<Boolean> checkExistence(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion) throws CloudException, IOException, IllegalArgumentException {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (resourceProviderNamespace == null) {
            throw new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null.");
        }
        if (parentResourcePath == null) {
            throw new IllegalArgumentException("Parameter parentResourcePath is required and cannot be null.");
        }
        if (resourceType == null) {
            throw new IllegalArgumentException("Parameter resourceType is required and cannot be null.");
        }
        if (resourceName == null) {
            throw new IllegalArgumentException("Parameter resourceName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (apiVersion == null) {
            throw new IllegalArgumentException("Parameter apiVersion is required and cannot be null.");
        }
        Call<Void> call = service.checkExistence(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent());
        return checkExistenceDelegate(call.execute());
    }

    /**
     * Checks whether resource exists.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param resourceProviderNamespace Resource identity.
     * @param parentResourcePath Resource identity.
     * @param resourceType Resource identity.
     * @param resourceName Resource identity.
     * @param apiVersion the String value
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link Call} object
     */
    public ServiceCall checkExistenceAsync(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, final ServiceCallback<Boolean> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (resourceGroupName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
            return null;
        }
        if (resourceProviderNamespace == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null."));
            return null;
        }
        if (parentResourcePath == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter parentResourcePath is required and cannot be null."));
            return null;
        }
        if (resourceType == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceType is required and cannot be null."));
            return null;
        }
        if (resourceName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceName is required and cannot be null."));
            return null;
        }
        if (this.client.subscriptionId() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null."));
            return null;
        }
        if (apiVersion == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter apiVersion is required and cannot be null."));
            return null;
        }
        Call<Void> call = service.checkExistence(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent());
        final ServiceCall serviceCall = new ServiceCall(call);
        call.enqueue(new ServiceResponseEmptyCallback<Boolean>(serviceCallback) {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try {
                    serviceCallback.success(checkExistenceDelegate(response));
                } catch (CloudException | IOException exception) {
                    serviceCallback.failure(exception);
                }
            }
        });
        return serviceCall;
    }

    private ServiceResponse<Boolean> checkExistenceDelegate(Response<Void> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<Boolean, CloudException>(this.client.restClient().mapperAdapter())
                .register(204, new TypeToken<Void>() { }.getType())
                .register(404, new TypeToken<Void>() { }.getType())
                .registerError(CloudException.class)
                .buildEmpty(response);
    }

    /**
     * Delete resource and all of its resources.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param resourceProviderNamespace Resource identity.
     * @param parentResourcePath Resource identity.
     * @param resourceType Resource identity.
     * @param resourceName Resource identity.
     * @param apiVersion the String value
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the {@link ServiceResponse} object if successful.
     */
    public ServiceResponse<Void> delete(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion) throws CloudException, IOException, IllegalArgumentException {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (resourceProviderNamespace == null) {
            throw new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null.");
        }
        if (parentResourcePath == null) {
            throw new IllegalArgumentException("Parameter parentResourcePath is required and cannot be null.");
        }
        if (resourceType == null) {
            throw new IllegalArgumentException("Parameter resourceType is required and cannot be null.");
        }
        if (resourceName == null) {
            throw new IllegalArgumentException("Parameter resourceName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (apiVersion == null) {
            throw new IllegalArgumentException("Parameter apiVersion is required and cannot be null.");
        }
        Call<ResponseBody> call = service.delete(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent());
        return deleteDelegate(call.execute());
    }

    /**
     * Delete resource and all of its resources.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param resourceProviderNamespace Resource identity.
     * @param parentResourcePath Resource identity.
     * @param resourceType Resource identity.
     * @param resourceName Resource identity.
     * @param apiVersion the String value
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link Call} object
     */
    public ServiceCall deleteAsync(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (resourceGroupName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
            return null;
        }
        if (resourceProviderNamespace == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null."));
            return null;
        }
        if (parentResourcePath == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter parentResourcePath is required and cannot be null."));
            return null;
        }
        if (resourceType == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceType is required and cannot be null."));
            return null;
        }
        if (resourceName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceName is required and cannot be null."));
            return null;
        }
        if (this.client.subscriptionId() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null."));
            return null;
        }
        if (apiVersion == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter apiVersion is required and cannot be null."));
            return null;
        }
        Call<ResponseBody> call = service.delete(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent());
        final ServiceCall serviceCall = new ServiceCall(call);
        call.enqueue(new ServiceResponseCallback<Void>(serviceCallback) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serviceCallback.success(deleteDelegate(response));
                } catch (CloudException | IOException exception) {
                    serviceCallback.failure(exception);
                }
            }
        });
        return serviceCall;
    }

    private ServiceResponse<Void> deleteDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<Void, CloudException>(this.client.restClient().mapperAdapter())
                .register(200, new TypeToken<Void>() { }.getType())
                .register(204, new TypeToken<Void>() { }.getType())
                .register(202, new TypeToken<Void>() { }.getType())
                .build(response);
    }

    /**
     * Create a resource.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param resourceProviderNamespace Resource identity.
     * @param parentResourcePath Resource identity.
     * @param resourceType Resource identity.
     * @param resourceName Resource identity.
     * @param apiVersion the String value
     * @param parameters Create or update resource parameters.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the GenericResourceInner object wrapped in {@link ServiceResponse} if successful.
     */
    public ServiceResponse<GenericResourceInner> createOrUpdate(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, GenericResourceInner parameters) throws CloudException, IOException, IllegalArgumentException {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (resourceProviderNamespace == null) {
            throw new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null.");
        }
        if (parentResourcePath == null) {
            throw new IllegalArgumentException("Parameter parentResourcePath is required and cannot be null.");
        }
        if (resourceType == null) {
            throw new IllegalArgumentException("Parameter resourceType is required and cannot be null.");
        }
        if (resourceName == null) {
            throw new IllegalArgumentException("Parameter resourceName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (apiVersion == null) {
            throw new IllegalArgumentException("Parameter apiVersion is required and cannot be null.");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter parameters is required and cannot be null.");
        }
        Validator.validate(parameters);
        Call<ResponseBody> call = service.createOrUpdate(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, this.client.subscriptionId(), apiVersion, parameters, this.client.acceptLanguage(), this.client.userAgent());
        return createOrUpdateDelegate(call.execute());
    }

    /**
     * Create a resource.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param resourceProviderNamespace Resource identity.
     * @param parentResourcePath Resource identity.
     * @param resourceType Resource identity.
     * @param resourceName Resource identity.
     * @param apiVersion the String value
     * @param parameters Create or update resource parameters.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link Call} object
     */
    public ServiceCall createOrUpdateAsync(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, GenericResourceInner parameters, final ServiceCallback<GenericResourceInner> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (resourceGroupName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
            return null;
        }
        if (resourceProviderNamespace == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null."));
            return null;
        }
        if (parentResourcePath == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter parentResourcePath is required and cannot be null."));
            return null;
        }
        if (resourceType == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceType is required and cannot be null."));
            return null;
        }
        if (resourceName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceName is required and cannot be null."));
            return null;
        }
        if (this.client.subscriptionId() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null."));
            return null;
        }
        if (apiVersion == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter apiVersion is required and cannot be null."));
            return null;
        }
        if (parameters == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter parameters is required and cannot be null."));
            return null;
        }
        Validator.validate(parameters, serviceCallback);
        Call<ResponseBody> call = service.createOrUpdate(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, this.client.subscriptionId(), apiVersion, parameters, this.client.acceptLanguage(), this.client.userAgent());
        final ServiceCall serviceCall = new ServiceCall(call);
        call.enqueue(new ServiceResponseCallback<GenericResourceInner>(serviceCallback) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serviceCallback.success(createOrUpdateDelegate(response));
                } catch (CloudException | IOException exception) {
                    serviceCallback.failure(exception);
                }
            }
        });
        return serviceCall;
    }

    private ServiceResponse<GenericResourceInner> createOrUpdateDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<GenericResourceInner, CloudException>(this.client.restClient().mapperAdapter())
                .register(201, new TypeToken<GenericResourceInner>() { }.getType())
                .register(200, new TypeToken<GenericResourceInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Returns a resource belonging to a resource group.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param resourceProviderNamespace Resource identity.
     * @param parentResourcePath Resource identity.
     * @param resourceType Resource identity.
     * @param resourceName Resource identity.
     * @param apiVersion the String value
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the GenericResourceInner object wrapped in {@link ServiceResponse} if successful.
     */
    public ServiceResponse<GenericResourceInner> get(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion) throws CloudException, IOException, IllegalArgumentException {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (resourceProviderNamespace == null) {
            throw new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null.");
        }
        if (parentResourcePath == null) {
            throw new IllegalArgumentException("Parameter parentResourcePath is required and cannot be null.");
        }
        if (resourceType == null) {
            throw new IllegalArgumentException("Parameter resourceType is required and cannot be null.");
        }
        if (resourceName == null) {
            throw new IllegalArgumentException("Parameter resourceName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (apiVersion == null) {
            throw new IllegalArgumentException("Parameter apiVersion is required and cannot be null.");
        }
        Call<ResponseBody> call = service.get(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent());
        return getDelegate(call.execute());
    }

    /**
     * Returns a resource belonging to a resource group.
     *
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param resourceProviderNamespace Resource identity.
     * @param parentResourcePath Resource identity.
     * @param resourceType Resource identity.
     * @param resourceName Resource identity.
     * @param apiVersion the String value
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link Call} object
     */
    public ServiceCall getAsync(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, final ServiceCallback<GenericResourceInner> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (resourceGroupName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
            return null;
        }
        if (resourceProviderNamespace == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null."));
            return null;
        }
        if (parentResourcePath == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter parentResourcePath is required and cannot be null."));
            return null;
        }
        if (resourceType == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceType is required and cannot be null."));
            return null;
        }
        if (resourceName == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter resourceName is required and cannot be null."));
            return null;
        }
        if (this.client.subscriptionId() == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null."));
            return null;
        }
        if (apiVersion == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter apiVersion is required and cannot be null."));
            return null;
        }
        Call<ResponseBody> call = service.get(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent());
        final ServiceCall serviceCall = new ServiceCall(call);
        call.enqueue(new ServiceResponseCallback<GenericResourceInner>(serviceCallback) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serviceCallback.success(getDelegate(response));
                } catch (CloudException | IOException exception) {
                    serviceCallback.failure(exception);
                }
            }
        });
        return serviceCall;
    }

    private ServiceResponse<GenericResourceInner> getDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<GenericResourceInner, CloudException>(this.client.restClient().mapperAdapter())
                .register(200, new TypeToken<GenericResourceInner>() { }.getType())
                .register(204, new TypeToken<GenericResourceInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Get all of the resources under a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the List&lt;GenericResourceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public ServiceResponse<PageImpl<GenericResourceInner>> listNext(final String nextPageLink) throws CloudException, IOException, IllegalArgumentException {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        Call<ResponseBody> call = service.listNext(nextPageLink, this.client.acceptLanguage(), this.client.userAgent());
        return listNextDelegate(call.execute());
    }

    /**
     * Get all of the resources under a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceCall the ServiceCall object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link Call} object
     */
    public ServiceCall listNextAsync(final String nextPageLink, final ServiceCall serviceCall, final ListOperationCallback<GenericResourceInner> serviceCallback) throws IllegalArgumentException {
        if (serviceCallback == null) {
            throw new IllegalArgumentException("ServiceCallback is required for async calls.");
        }
        if (nextPageLink == null) {
            serviceCallback.failure(new IllegalArgumentException("Parameter nextPageLink is required and cannot be null."));
            return null;
        }
        Call<ResponseBody> call = service.listNext(nextPageLink, this.client.acceptLanguage(), this.client.userAgent());
        serviceCall.newCall(call);
        call.enqueue(new ServiceResponseCallback<List<GenericResourceInner>>(serviceCallback) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ServiceResponse<PageImpl<GenericResourceInner>> result = listNextDelegate(response);
                    serviceCallback.load(result.getBody().getItems());
                    if (result.getBody().getNextPageLink() != null
                            && serviceCallback.progress(result.getBody().getItems()) == ListOperationCallback.PagingBahavior.CONTINUE) {
                        listNextAsync(result.getBody().getNextPageLink(), serviceCall, serviceCallback);
                    } else {
                        serviceCallback.success(new ServiceResponse<>(serviceCallback.get(), result.getResponse()));
                    }
                } catch (CloudException | IOException exception) {
                    serviceCallback.failure(exception);
                }
            }
        });
        return serviceCall;
    }

    private ServiceResponse<PageImpl<GenericResourceInner>> listNextDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<PageImpl<GenericResourceInner>, CloudException>(this.client.restClient().mapperAdapter())
                .register(200, new TypeToken<PageImpl<GenericResourceInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}