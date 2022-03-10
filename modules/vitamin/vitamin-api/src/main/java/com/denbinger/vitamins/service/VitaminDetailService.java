/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.denbinger.vitamins.service;

import com.denbinger.vitamins.model.VitaminDetail;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for VitaminDetail. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see VitaminDetailServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface VitaminDetailService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.denbinger.vitamins.service.impl.VitaminDetailServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the vitamin detail remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link VitaminDetailServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * addVitaminDetail: Adds a new vitamin detail record.
	 *
	 * @param value
	 * @parampersistedVitaminId
	 * @paramtypeCode
	 * @returnVitaminDetail: The new instance.
	 */
	public VitaminDetail addVitaminDetail(
			long persistedVitaminId, int typeCode, String value,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * deleteAllVitaminDetails: Deletes all of the vitamin details.
	 *
	 * @parampersistedVitaminId
	 */
	public void deleteAllVitaminDetails(long persistedVitaminId);

	public VitaminDetail deleteVitaminDetail(long vitaminDetailId)
		throws PortalException;

	public VitaminDetail deleteVitaminDetail(VitaminDetail vitaminDetail);

	public void deleteVitaminDetailsByType(
		long persistedVitaminId, int typeCode);

	/**
	 * getAllVitaminDetails: Returns the full list of details for the given persisted
	 * vitamin id.
	 *
	 * @return List The list of vitamin details.
	 * @parampersistedVitaminId
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<VitaminDetail> getAllVitaminDetails(long persistedVitaminId);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * getVitaminDetailsByType: Returns the list of details for the given persisted
	 * vitamin id and type.
	 *
	 * @return List The list of vitamin details.
	 * @parampersistedVitaminId
	 * @paramtypeCode
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<VitaminDetail> getVitaminDetailsByType(
		long persistedVitaminId, int typeCode);

}