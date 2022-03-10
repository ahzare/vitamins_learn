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

package com.denbinger.vitamins.model.impl;

import com.denbinger.vitamins.constants.VitaminDetailType;
import com.denbinger.vitamins.model.VitaminDetail;
import com.denbinger.vitamins.service.VitaminDetailLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PersistedVitaminImpl extends PersistedVitaminBaseImpl {
    public List<VitaminDetail> getAllVitaminDetails() {
        return VitaminDetailLocalServiceUtil.
                getAllVitaminDetails(getPersistedVitaminId());
    }

    /**
     * getSymptoms: Returns the list of symptom details.
     * @return List
     */
    public List<VitaminDetail> getSymptoms() {
        return VitaminDetailLocalServiceUtil.
                getVitaminDetailsByType(getPersistedVitaminId(),
                        VitaminDetailType.SYMPTOM);
    }

    /**
     * getAttributes: Returns the list of attribute details.
     * @return List
     */
    public List<VitaminDetail> getAttributes() {
        return VitaminDetailLocalServiceUtil.
                getVitaminDetailsByType(getPersistedVitaminId(),
                        VitaminDetailType.ATTRIBUTE);
    }

    /**
     * getProperties: Returns the list of property details.
     * @return List
     */
    public List<VitaminDetail> getProperties() {
        return VitaminDetailLocalServiceUtil.
                getVitaminDetailsByType(getPersistedVitaminId(),
                        VitaminDetailType.PROPERTY);
    }

    /**
     * getChemicalNames: Returns the list of chemical name details.
     * @return List
     */
    public List<VitaminDetail> getChemicalNames() {
        return VitaminDetailLocalServiceUtil.
                getVitaminDetailsByType(getPersistedVitaminId(),
                        VitaminDetailType.CHEMICAL_NAME);
    }

    /**
     * getRisks: Returns the list of risk details.
     * @return List
     */
    public List<VitaminDetail> getRisks() {
        return VitaminDetailLocalServiceUtil.
                getVitaminDetailsByType(getPersistedVitaminId(),
                        VitaminDetailType.RISK);
    }

}