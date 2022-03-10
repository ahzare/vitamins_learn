package com.denbinger.vitamins.internal.security.permission.resource;

import com.denbinger.vitamins.constants.VitaminConstants;
import com.denbinger.vitamins.model.PersistedVitamin;
import com.denbinger.vitamins.service.PersistedVitaminLocalService;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.Dictionary;

@Component(immediate = true)
public class PersistedVitaminModelResourcePermission {
    @Activate
    public void activate(BundleContext bundleContext) {
        Dictionary<String, Object> properties = new HashMapDictionary<>();
        properties.put("model.class.name", PersistedVitamin.class.getName());
        serviceRegistration = bundleContext.registerService(
                ModelResourcePermission.class,
                ModelResourcePermissionFactory.create(
                        PersistedVitamin.class,
                        PersistedVitamin::getPersistedVitaminId,
                        persistedVitaminLocalService::getPersistedVitamin,
                        portletResourcePermission,
                        (modelResourcePermission, consumer) -> {
//nothing and idunnowhy !
                        }
                ), properties
        );
    }

    @Deactivate
    public void deactivate() {
        serviceRegistration.unregister();
    }

    @Reference
    private PersistedVitaminLocalService persistedVitaminLocalService;
    @Reference(target = "(resource.name=" + VitaminConstants.RESOURCE_NAME + ")")
    private PortletResourcePermission portletResourcePermission;
    private ServiceRegistration<?> serviceRegistration;
}

