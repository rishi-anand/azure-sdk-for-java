/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.website.implementation;

import com.google.common.collect.Sets;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.ResourceNamer;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.azure.management.website.AppServiceCertificate;
import com.microsoft.azure.management.website.AppServiceDomain;
import com.microsoft.azure.management.website.AppServicePlan;
import com.microsoft.azure.management.website.AppServicePricingTier;
import com.microsoft.azure.management.website.AzureResourceType;
import com.microsoft.azure.management.website.CloningInfo;
import com.microsoft.azure.management.website.CustomHostNameDnsRecordType;
import com.microsoft.azure.management.website.HostNameBinding;
import com.microsoft.azure.management.website.HostNameSslState;
import com.microsoft.azure.management.website.HostNameType;
import com.microsoft.azure.management.website.JavaVersion;
import com.microsoft.azure.management.website.ManagedPipelineMode;
import com.microsoft.azure.management.website.NetFrameworkVersion;
import com.microsoft.azure.management.website.PhpVersion;
import com.microsoft.azure.management.website.PlatformArchitecture;
import com.microsoft.azure.management.website.PythonVersion;
import com.microsoft.azure.management.website.RemoteVisualStudioVersion;
import com.microsoft.azure.management.website.SiteAvailabilityState;
import com.microsoft.azure.management.website.SslState;
import com.microsoft.azure.management.website.UsageState;
import com.microsoft.azure.management.website.WebAppBase;
import com.microsoft.azure.management.website.WebContainer;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.FuncN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The implementation for {@link WebAppBase}.
 */
abstract class WebAppBaseImpl<
        FluentT extends WebAppBase<FluentT>,
        FluentImplT extends WebAppBaseImpl<FluentT, FluentImplT>>
        extends GroupableResourceImpl<
            FluentT,
            SiteInner,
            FluentImplT,
            AppServiceManager>
        implements
            WebAppBase<FluentT>,
            WebAppBase.Definition<FluentT>,
            WebAppBase.Update<FluentT> {

    final WebAppsInner client;

    private Set<String> hostNamesSet;
    private Set<String> enabledHostNamesSet;
    private Set<String> trafficManagerHostNamesSet;
    private Set<String> outboundIpAddressesSet;
    private Map<String, HostNameSslState> hostNameSslStateMap;
    private Map<String, HostNameBindingImpl<FluentT, FluentImplT>> hostNameBindingsToCreate;
    private List<String> hostNameBindingsToDelete;
    private Map<String, HostNameSslBindingImpl<FluentT, FluentImplT>> sslBindingsToCreate;
    private Map<String, HostNameSslBindingImpl<FluentT, FluentImplT>> sslBindingsToDelete;

    WebAppBaseImpl(String name, SiteInner innerObject, SiteConfigInner configObject, final WebAppsInner client, AppServiceManager manager) {
        super(name, innerObject, manager);
        this.client = client;
        this.inner().withSiteConfig(configObject);
        normalizeProperties();
    }

    @SuppressWarnings("unchecked")
    private FluentT normalizeProperties() {
        this.hostNameBindingsToCreate = new HashMap<>();
        this.hostNameBindingsToDelete = new ArrayList<>();
        this.sslBindingsToCreate = new HashMap<>();
        if (inner().hostNames() != null) {
            this.hostNamesSet = Sets.newHashSet(inner().hostNames());
        }
        if (inner().enabledHostNames() != null) {
            this.enabledHostNamesSet = Sets.newHashSet(inner().enabledHostNames());
        }
        if (inner().trafficManagerHostNames() != null) {
            this.trafficManagerHostNamesSet = Sets.newHashSet(inner().trafficManagerHostNames());
        }
        if (inner().outboundIpAddresses() != null) {
            this.outboundIpAddressesSet = Sets.newHashSet(inner().outboundIpAddresses().split(",[ ]*"));
        }
        this.hostNameSslStateMap = new HashMap<>();
        if (inner().hostNameSslStates() != null) {
            for (HostNameSslState hostNameSslState : inner().hostNameSslStates()) {
                // Server returns null sometimes, invalid on update, so we set default
                if (hostNameSslState.sslState() == null) {
                    hostNameSslState.withSslState(SslState.DISABLED);
                }
                hostNameSslStateMap.put(hostNameSslState.name(), hostNameSslState);
            }
        }
        return (FluentT) this;
    }

    @Override
    public String state() {
        return inner().state();
    }

    @Override
    public Set<String> hostNames() {
        return Collections.unmodifiableSet(hostNamesSet);
    }

    @Override
    public String repositorySiteName() {
        return inner().repositorySiteName();
    }

    @Override
    public UsageState usageState() {
        return inner().usageState();
    }

    @Override
    public boolean enabled() {
        return inner().enabled();
    }

    @Override
    public Set<String> enabledHostNames() {
        if (enabledHostNamesSet == null) {
            return null;
        }
        return Collections.unmodifiableSet(enabledHostNamesSet);
    }

    @Override
    public SiteAvailabilityState availabilityState() {
        return inner().availabilityState();
    }

    @Override
    public Map<String, HostNameSslState> hostNameSslStates() {
        return Collections.unmodifiableMap(hostNameSslStateMap);
    }

    @Override
    public String appServicePlanId() {
        return inner().serverFarmId();
    }

    @Override
    public DateTime lastModifiedTime() {
        return inner().lastModifiedTimeUtc();
    }

    @Override
    public Set<String> trafficManagerHostNames() {
        return Collections.unmodifiableSet(trafficManagerHostNamesSet);
    }

    @Override
    public boolean isPremiumApp() {
        return inner().premiumAppDeployed();
    }

    @Override
    public boolean scmSiteAlsoStopped() {
        return inner().scmSiteAlsoStopped();
    }

    @Override
    public String targetSwapSlot() {
        return inner().targetSwapSlot();
    }

    @Override
    public String microService() {
        return inner().microService();
    }

    @Override
    public String gatewaySiteName() {
        return inner().gatewaySiteName();
    }

    @Override
    public boolean clientAffinityEnabled() {
        return inner().clientAffinityEnabled();
    }

    @Override
    public boolean clientCertEnabled() {
        return inner().clientCertEnabled();
    }

    @Override
    public boolean hostNamesDisabled() {
        return false;
    }

    @Override
    public Set<String> outboundIpAddresses() {
        return Collections.unmodifiableSet(outboundIpAddressesSet);
    }

    @Override
    public int containerSize() {
        return inner().containerSize();
    }

    @Override
    public CloningInfo cloningInfo() {
        return inner().cloningInfo();
    }

    @Override
    public boolean isDefaultContainer() {
        return inner().isDefaultContainer();
    }

    @Override
    public String defaultHostName() {
        return inner().defaultHostName();
    }

    @Override
    public List<String> defaultDocuments() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return Collections.unmodifiableList(inner().siteConfig().defaultDocuments());
    }

    @Override
    public NetFrameworkVersion netFrameworkVersion() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return new NetFrameworkVersion(inner().siteConfig().netFrameworkVersion());
    }

    @Override
    public PhpVersion phpVersion() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return new PhpVersion(inner().siteConfig().phpVersion());
    }

    @Override
    public PythonVersion pythonVersion() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return new PythonVersion(inner().siteConfig().pythonVersion());
    }

    @Override
    public String nodeVersion() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return inner().siteConfig().nodeVersion();
    }

    @Override
    public boolean remoteDebuggingEnabled() {
        if (inner().siteConfig() == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(inner().siteConfig().remoteDebuggingEnabled());
    }

    @Override
    public RemoteVisualStudioVersion remoteDebuggingVersion() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return new RemoteVisualStudioVersion(inner().siteConfig().remoteDebuggingVersion());
    }

    @Override
    public boolean webSocketsEnabled() {
        if (inner().siteConfig() == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(inner().siteConfig().webSocketsEnabled());
    }

    @Override
    public boolean alwaysOn() {
        if (inner().siteConfig() == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(inner().siteConfig().alwaysOn());
    }

    @Override
    public JavaVersion javaVersion() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return new JavaVersion(inner().siteConfig().javaVersion());
    }

    @Override
    public String javaContainer() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return inner().siteConfig().javaContainer();
    }

    @Override
    public String javaContainerVersion() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return inner().siteConfig().javaContainerVersion();
    }

    @Override
    public ManagedPipelineMode managedPipelineMode() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return inner().siteConfig().managedPipelineMode();
    }

    @Override
    public String autoSwapSlotName() {
        if (inner().siteConfig() == null) {
            return null;
        }
        return inner().siteConfig().autoSwapSlotName();
    }

    abstract Observable<SiteInner> createOrUpdateInner(String resourceGroupName, String name, SiteInner site);

    abstract Observable<SiteInner> getInner(String resourceGroupName, String name);

    abstract Observable<SiteConfigInner> createOrUpdateSiteConfig(String resourceGroupName, String name, SiteConfigInner siteConfig);

    abstract Observable<Object> deleteHostNameBinding(String hostname);

    @Override
    public Observable<FluentT> createResourceAsync() {
        if (hostNameSslStateMap.size() > 0) {
            inner().withHostNameSslStates(new ArrayList<>(hostNameSslStateMap.values()));
        }
        if (inner().siteConfig() != null & inner().siteConfig().location() == null) {
            inner().siteConfig().withLocation(inner().location());
        }
        // Construct web app observable
        return createOrUpdateInner(resourceGroupName(), name(), inner())
                // Submit hostname bindings
                .flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                    @Override
                    public Observable<SiteInner> call(final SiteInner site) {
                        List<Observable<HostNameBinding>> bindingObservables = new ArrayList<>();
                        for (HostNameBindingImpl<FluentT, FluentImplT> binding: hostNameBindingsToCreate.values()) {
                            bindingObservables.add(binding.createAsync());
                        }
                        for (String binding: hostNameBindingsToDelete) {
                            bindingObservables.add(deleteHostNameBinding(binding).map(new Func1<Object, HostNameBinding>() {
                                @Override
                                public HostNameBinding call(Object o) {
                                    return null;
                                }
                            }));
                        }
                        if (bindingObservables.isEmpty()) {
                            return Observable.just(site);
                        } else {
                            return Observable.zip(bindingObservables, new FuncN<SiteInner>() {
                                @Override
                                public SiteInner call(Object... args) {
                                    return site;
                                }
                            });
                        }
                    }
                })
                // refresh after hostname bindings
                .flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                    @Override
                    public Observable<SiteInner> call(SiteInner site) {
                        return getInner(resourceGroupName(), site.name());
                    }
                })
                .flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                    @Override
                    public Observable<SiteInner> call(final SiteInner siteInner) {
                        List<Observable<AppServiceCertificate>> certs = new ArrayList<>();
                        for (final HostNameSslBindingImpl<FluentT, FluentImplT> binding : sslBindingsToCreate.values()) {
                            certs.add(binding.newCertificate());
                            siteInner.hostNameSslStates().add(binding.inner().withToUpdate(true));
                        }
                        if (certs.isEmpty()) {
                            return Observable.just(siteInner);
                        } else {
                            return Observable.zip(certs, new FuncN<SiteInner>() {
                                @Override
                                public SiteInner call(Object... args) {
                                    return siteInner;
                                }
                            });
                        }
                    }
                })
                // refresh after hostname SSL bindings
                .flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                    @Override
                    public Observable<SiteInner> call(SiteInner site) {
                        return createOrUpdateInner(resourceGroupName(), site.name(), site);
                    }
                })
                // submit config
                .flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                    @Override
                    public Observable<SiteInner> call(final SiteInner siteInner) {
                        if (inner().siteConfig() == null) {
                            return Observable.just(siteInner);
                        }
                        return createOrUpdateSiteConfig(resourceGroupName(), name(), inner().siteConfig())
                                .flatMap(new Func1<SiteConfigInner, Observable<SiteInner>>() {
                                    @Override
                                    public Observable<SiteInner> call(SiteConfigInner siteConfigInner) {
                                        siteInner.withSiteConfig(siteConfigInner);
                                        return Observable.just(siteInner);
                                    }
                                });
                    }
                })
                // convert from inner
                .map(new Func1<SiteInner, FluentT>() {
                    @Override
                    public FluentT call(SiteInner siteInner) {
                        setInner(siteInner);
                        return normalizeProperties();
                    }
                });
    }


    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withNewFreeAppServicePlan() {
        String appServicePlanName = ResourceNamer.randomResourceName(name(), 10);
        AppServicePlan.DefinitionStages.WithCreate creatable = myManager.appServicePlans().define(appServicePlanName)
                .withRegion(region())
                .withNewResourceGroup(resourceGroupName())
                .withPricingTier(AppServicePricingTier.FREE_F1);
        addCreatableDependency(creatable);
        inner().withServerFarmId(appServicePlanName);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withNewAppServicePlan(String name, AppServicePricingTier pricingTier) {
        AppServicePlan.DefinitionStages.WithCreate creatable = myManager.appServicePlans().define(name)
                .withRegion(region())
                .withNewResourceGroup(resourceGroupName())
                .withPricingTier(pricingTier);
        addCreatableDependency(creatable);
        inner().withServerFarmId(name);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withExistingAppServicePlan(String appServicePlanName) {
        inner().withServerFarmId(appServicePlanName);
        return (FluentImplT) this;
    }

    WebAppBaseImpl withNewHostNameSslBinding(final HostNameSslBindingImpl<FluentT, FluentImplT> hostNameSslBinding) {
        if (hostNameSslBinding.newCertificate() != null) {
            sslBindingsToCreate.put(hostNameSslBinding.name(), hostNameSslBinding);
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withManagedHostnameBindings(AppServiceDomain domain, String... hostnames) {
        for(String hostname : hostnames) {
            if (hostname.equals("@") || hostname.equalsIgnoreCase(domain.name())) {
                defineHostnameBinding()
                        .withAzureManagedDomain(domain)
                        .withSubDomain(hostname)
                        .withDnsRecordType(CustomHostNameDnsRecordType.A)
                        .attach();
            } else {
                defineHostnameBinding()
                        .withAzureManagedDomain(domain)
                        .withSubDomain(hostname)
                        .withDnsRecordType(CustomHostNameDnsRecordType.CNAME)
                        .attach();
            }
        }
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public HostNameBindingImpl<FluentT, FluentImplT> defineHostnameBinding() {
        HostNameBindingInner inner = new HostNameBindingInner();
        inner.withSiteName(name());
        inner.withLocation(regionName());
        inner.withAzureResourceType(AzureResourceType.WEBSITE);
        inner.withAzureResourceName(name());
        inner.withHostNameType(HostNameType.VERIFIED);
        return new HostNameBindingImpl<>(inner, (FluentImplT) this, client);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withThirdPartyHostnameBinding(String domain, String... hostnames) {
        for(String hostname : hostnames) {
            defineHostnameBinding()
                    .withThirdPartyDomain(domain)
                    .withSubDomain(hostname)
                    .withDnsRecordType(CustomHostNameDnsRecordType.CNAME)
                    .attach();
        }
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withoutHostnameBinding(String hostname) {
        hostNameBindingsToDelete.add(hostname);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withoutSslBinding(String hostname) {
        if (hostNameSslStateMap.containsKey(hostname)) {
            hostNameSslStateMap.get(hostname).withSslState(SslState.DISABLED).withToUpdate(true);
        }
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    FluentImplT withHostNameBinding(final HostNameBindingImpl<FluentT, FluentImplT> hostNameBinding) {
        this.hostNameBindingsToCreate.put(
                hostNameBinding.name(),
                hostNameBinding);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withAppDisabledOnCreation() {
        inner().withEnabled(false);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withScmSiteAlsoStopped(boolean scmSiteAlsoStopped) {
        inner().withScmSiteAlsoStopped(scmSiteAlsoStopped);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withClientAffinityEnabled(boolean enabled) {
        inner().withClientAffinityEnabled(enabled);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withClientCertEnabled(boolean enabled) {
        inner().withClientCertEnabled(enabled);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public HostNameSslBindingImpl<FluentT, FluentImplT> defineSslBinding() {
        return new HostNameSslBindingImpl<>(new HostNameSslState(), (FluentImplT) this, myManager);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withNetFrameworkVersion(NetFrameworkVersion version) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withNetFrameworkVersion(version.toString());
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withPhpVersion(PhpVersion version) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withPhpVersion(version.toString());
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withJavaVersion(JavaVersion version) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withJavaVersion(version.toString());
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withWebContainer(WebContainer webContainer) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        String[] containerInfo = webContainer.toString().split(" ");
        inner().siteConfig().withJavaContainer(containerInfo[0]);
        inner().siteConfig().withJavaContainerVersion(containerInfo[1]);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withPythonVersion(PythonVersion version) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withPythonVersion(version.toString());
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withPlatformArchitecture(PlatformArchitecture platform) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withUse32BitWorkerProcess(platform.equals(PlatformArchitecture.X86));
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withWebSocketsEnabled(boolean enabled) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withWebSocketsEnabled(enabled);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withWebAppAlwaysOn(boolean alwaysOn) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withAlwaysOn(alwaysOn);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withManagedPipelineMode(ManagedPipelineMode managedPipelineMode) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withManagedPipelineMode(managedPipelineMode);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withAutoSwapSlotName(String slotName) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withAutoSwapSlotName(slotName);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withRemoteDebuggingEnabled(RemoteVisualStudioVersion remoteVisualStudioVersion) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withRemoteDebuggingEnabled(true);
        inner().siteConfig().withRemoteDebuggingVersion(remoteVisualStudioVersion.toString());
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withRemoteDebuggingDisabled() {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        inner().siteConfig().withRemoteDebuggingEnabled(false);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withDefaultDocument(String document) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        if (inner().siteConfig().defaultDocuments() == null) {
            inner().siteConfig().withDefaultDocuments(new ArrayList<String>());
        }
        inner().siteConfig().defaultDocuments().add(document);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withDefaultDocuments(List<String> documents) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        if (inner().siteConfig().defaultDocuments() == null) {
            inner().siteConfig().withDefaultDocuments(new ArrayList<String>());
        }
        inner().siteConfig().defaultDocuments().addAll(documents);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withoutDefaultDocument(String document) {
        if (inner().siteConfig() == null) {
            inner().withSiteConfig(new SiteConfigInner());
        }
        if (inner().siteConfig().defaultDocuments() != null) {
            inner().siteConfig().defaultDocuments().remove(document);
        }
        return (FluentImplT) this;
    }
}