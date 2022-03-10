package com.denbinger.vitamins.internal.security.permission.resource;

import com.denbinger.vitamins.constants.VitaminConstants;
import com.denbinger.vitamins.model.VitaminDetail;
import com.denbinger.vitamins.service.VitaminDetailLocalService;
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
public class VitaminDetailModelResourcePermissionRegistrar {
    @Activate
    public void activate(BundleContext bundleContext) {
        Dictionary<String, Object> properties = new HashMapDictionary<>();
        properties.put("model.class.name", VitaminDetail.class.getName());
        _serviceRegistration = bundleContext.registerService(
                ModelResourcePermission.class,
                ModelResourcePermissionFactory.create(
                        VitaminDetail.class,
                        VitaminDetail::getVitaminDetailId,
                        _vitaminDetailLocalService::getVitaminDetail,
                        _portletResourcePermission,
                        (modelResourcePermission, consumer) -> {
// nothing to accept
                        }),
                properties);
    }

    @Deactivate
    public void deactivate() {
        _serviceRegistration.unregister();
    }

    @Reference
    private VitaminDetailLocalService _vitaminDetailLocalService;
    @Reference(target = "(resource.name=" + VitaminConstants.RESOURCE_NAME + ")")
    private PortletResourcePermission _portletResourcePermission;
    private ServiceRegistration<ModelResourcePermission> _serviceRegistration;
}

