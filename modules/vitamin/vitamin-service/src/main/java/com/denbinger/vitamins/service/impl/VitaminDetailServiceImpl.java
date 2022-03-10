/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.denbinger.vitamins.service.impl;

import com.denbinger.vitamins.model.VitaminDetail;
import com.denbinger.vitamins.service.base.VitaminDetailServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
        property = {
                "json.web.service.context.name=foo",
                "json.web.service.context.path=VitaminDetail"
        },
        service = AopService.class
)
public class VitaminDetailServiceImpl extends VitaminDetailServiceBaseImpl {
    /**
     * addVitaminDetail: Adds a new vitamin detail record.
     *
     * @param value
     * @parampersistedVitaminId
     * @paramtypeCode
     * @returnVitaminDetail: The new instance.
     */
    public VitaminDetail addVitaminDetail(final long persistedVitaminId, final int typeCode,
                                          final String value, final ServiceContext serviceContext)
            throws PortalException {
        VitaminDetail detail = null;
        return detail;
    }

    /**
     * deleteAllVitaminDetails: Deletes all of the vitamin details.
     *
     * @parampersistedVitaminId
     */
    public void deleteAllVitaminDetails(final long persistedVitaminId) {
    }

    public void deleteVitaminDetailsByType(final long persistedVitaminId, final int typeCode) {
    }

    public VitaminDetail deleteVitaminDetail(long vitaminDetailId) throws PortalException {
        VitaminDetail detail = null;
        return detail;
    }

    public VitaminDetail deleteVitaminDetail(VitaminDetail vitaminDetail) {
        VitaminDetail detail = null;
        return detail;
    }

    /**
     * getAllVitaminDetails: Returns the full list of details for the given persisted
     * vitamin id.
     *
     * @return List The list of vitamin details.
     * @parampersistedVitaminId
     */
    public List<VitaminDetail> getAllVitaminDetails(final long persistedVitaminId) {
        return null;
    }

    /**
     * getVitaminDetailsByType: Returns the list of details for the given persisted
     * vitamin id and type.
     *
     * @return List The list of vitamin details.
     * @parampersistedVitaminId
     * @paramtypeCode
     */
    public List<VitaminDetail> getVitaminDetailsByType(final long persistedVitaminId, final int typeCode) {
        return null;
    }

}