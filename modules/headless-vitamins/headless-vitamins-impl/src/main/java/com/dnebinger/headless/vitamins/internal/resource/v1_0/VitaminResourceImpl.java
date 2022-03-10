package com.dnebinger.headless.vitamins.internal.resource.v1_0;

import com.denbinger.vitamins.constants.PersistedVitaminType;
import com.denbinger.vitamins.model.PersistedVitamin;
import com.denbinger.vitamins.model.VitaminDetail;
import com.denbinger.vitamins.service.PersistedVitaminService;
import com.dnebinger.headless.vitamins.dto.v1_0.Vitamin;
import com.dnebinger.headless.vitamins.internal.odata.entity.v1_0.VitaminEntityModel;
import com.dnebinger.headless.vitamins.internal.util.CreatorUtil;
import com.dnebinger.headless.vitamins.resource.v1_0.VitaminResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dave Nebinger
 */

@Component(
        properties = "OSGI-INF/liferay/rest/v1_0/vitamin.properties",
        scope = ServiceScope.PROTOTYPE, service = VitaminResource.class
)
public class VitaminResourceImpl extends BaseVitaminResourceImpl {
    @Reference
    private Portal portal;
    @Reference
    private UserLocalService userLocalService;
    @Reference
    private PersistedVitaminService persistedVitaminService;
    private static final Logger _log =
            LoggerFactory.getLogger(VitaminResourceImpl.class);

    protected ServiceContext _getServiceContext() throws PortalException {
        ServiceContext serviceContext =
                new ServiceContext();
        serviceContext.setCompanyId(contextCompany.getCompanyId());
// need the current user in the service context.
// will get easier in newer version of the REST Builder plugin...
// but for now, this is the only game in town.
        long userId = PrincipalThreadLocal.getUserId();
        serviceContext.setUserId(userId);
        return serviceContext;
    }

    protected int _toTypeCode(final Vitamin.Type type) {
        if (type == null)
            return PersistedVitaminType.OTHER;
        switch (type) {
            case VITAMIN:
                return PersistedVitaminType.VITAMIN;
            case MINERAL:
                return PersistedVitaminType.MINERAL;
        }
        return PersistedVitaminType.OTHER;
    }

    protected Vitamin _toVitamin(PersistedVitamin pv) throws PortalException {
        return new Vitamin() {{
            creator = CreatorUtil.toCreator(portal,
                    userLocalService.getUser(pv.getUserId()));
            articleId = pv.getArticleId();
            group = pv.getGroupName();
            description = pv.getDescription();
            id = pv.getSurrogateId();
            name = pv.getName();
            type = _toVitaminType(pv.getType());
            attributes = ListUtil.toArray(pv.getAttributes(), VALUE_ACCESSOR);
            chemicalNames = ListUtil.toArray(pv.getChemicalNames(), VALUE_ACCESSOR);
            properties = ListUtil.toArray(pv.getProperties(), VALUE_ACCESSOR);
            risks = ListUtil.toArray(pv.getRisks(), VALUE_ACCESSOR);
            symptoms = ListUtil.toArray(pv.getSymptoms(), VALUE_ACCESSOR);
        }};
    }

    protected Vitamin.Type _toVitaminType(int typeCode) {
        if (typeCode == PersistedVitaminType.VITAMIN) {
            return Vitamin.Type.VITAMIN;
        }
        if (typeCode == PersistedVitaminType.MINERAL) {
            return Vitamin.Type.MINERAL;
        }
        return Vitamin.Type.OTHER;
    }

    protected static final Accessor<VitaminDetail, String> VALUE_ACCESSOR =
            new Accessor<VitaminDetail, String>() {
                @Override
                public String get(VitaminDetail vd) {
                    return vd.getValue();
                }

                @Override
                public Class<String> getAttributeClass() {
                    return String.class;
                }

                @Override
                public Class<VitaminDetail> getTypeClass() {
                    return VitaminDetail.class;
                }
            };
    private VitaminEntityModel _vitaminEntityModel = new VitaminEntityModel();

    @Override
    public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
            throws Exception {
        return _vitaminEntityModel;
    }

    @Override
    public Page<Vitamin> getVitaminsPage(String search, Filter filter,
                                         Pagination pagination, Sort[] sorts)
            throws Exception {
        System.out.println("getVitaminsPage");
        List<Vitamin> list = persistedVitaminService.getAll()
                .stream().map(persistedVitamin -> {
                    try {
                        return _toVitamin(persistedVitamin);
                    } catch (PortalException e) {
                        e.printStackTrace();
                    }
                    return new Vitamin();
                }).collect(Collectors.toList());
        return Page.of(list);
    }

    @Override
    public void deleteVitamin(@NotNull String vitaminId) throws Exception {
        try {
// super easy case, just pass through to the service layer.
            persistedVitaminService.deletePersistedVitamin(vitaminId);
        } catch (Exception e) {
            _log.error("Error deleting vitamin: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Vitamin getVitamin(@NotNull String vitaminId) throws Exception {
        try {
// fetch the entity class...
            PersistedVitamin pv = persistedVitaminService.getPersistedVitamin(vitaminId);
            return _toVitamin(pv);
        } catch (Exception e) {
            _log.error("Error getting vitamin [" + vitaminId + "]: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Vitamin postVitamin(Vitamin v) throws Exception {
        System.out.println("postVitamin");
        if (_log.isDebugEnabled()) {
            _log.debug("Need to create a new vitamin: %s\n", v.toString());
        }
        _log.warn("hi ali");
        try {
            PersistedVitamin pv = persistedVitaminService.addPersistedVitamin(
                    v.getId(), v.getName(), v.getGroup(), v.getDescription(),
                    _toTypeCode(v.getType()), v.getArticleId(), v.getChemicalNames(),
                    v.getProperties(), v.getAttributes(), v.getSymptoms(),
                    v.getRisks(), _getServiceContext());
            return _toVitamin(pv);
        } catch (Exception e) {
            _log.error("Error creating vitamin: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Vitamin patchVitamin(@NotNull String vitaminId, Vitamin v)
            throws Exception {
        try {
            PersistedVitamin pv = persistedVitaminService.patchPersistedVitamin(vitaminId,
                    v.getId(), v.getName(), v.getGroup(), v.getDescription(),
                    _toTypeCode(v.getType()), v.getArticleId(), v.getChemicalNames(),
                    v.getProperties(), v.getAttributes(), v.getSymptoms(),
                    v.getRisks(), _getServiceContext());
            return _toVitamin(pv);
        } catch (Exception e) {
            _log.error("Error patching vitamin: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Vitamin putVitamin(@NotNull String vitaminId, Vitamin v) throws Exception {
        try {
            PersistedVitamin pv = persistedVitaminService.updatePersistedVitamin(vitaminId,
                    v.getId(), v.getName(), v.getGroup(), v.getDescription(),
                    _toTypeCode(v.getType()), v.getArticleId(), v.getChemicalNames(),
                    v.getProperties(), v.getAttributes(), v.getSymptoms(),
                    v.getRisks(), _getServiceContext());
            return _toVitamin(pv);
        } catch (Exception e) {
            _log.error("Error putting vitamin: " + e.getMessage(), e);
            throw e;
        }
    }
}