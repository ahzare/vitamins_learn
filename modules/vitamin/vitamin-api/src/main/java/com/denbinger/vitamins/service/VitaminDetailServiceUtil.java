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

import java.util.List;

/**
 * Provides the remote service utility for VitaminDetail. This utility wraps
 * <code>com.denbinger.vitamins.service.impl.VitaminDetailServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see VitaminDetailService
 * @generated
 */
public class VitaminDetailServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.denbinger.vitamins.service.impl.VitaminDetailServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * addVitaminDetail: Adds a new vitamin detail record.
	 *
	 * @param value
	 * @parampersistedVitaminId
	 * @paramtypeCode
	 * @returnVitaminDetail: The new instance.
	 */
	public static VitaminDetail addVitaminDetail(
			long persistedVitaminId, int typeCode, String value,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addVitaminDetail(
			persistedVitaminId, typeCode, value, serviceContext);
	}

	/**
	 * deleteAllVitaminDetails: Deletes all of the vitamin details.
	 *
	 * @parampersistedVitaminId
	 */
	public static void deleteAllVitaminDetails(long persistedVitaminId) {
		getService().deleteAllVitaminDetails(persistedVitaminId);
	}

	public static VitaminDetail deleteVitaminDetail(long vitaminDetailId)
		throws PortalException {

		return getService().deleteVitaminDetail(vitaminDetailId);
	}

	public static VitaminDetail deleteVitaminDetail(
		VitaminDetail vitaminDetail) {

		return getService().deleteVitaminDetail(vitaminDetail);
	}

	public static void deleteVitaminDetailsByType(
		long persistedVitaminId, int typeCode) {

		getService().deleteVitaminDetailsByType(persistedVitaminId, typeCode);
	}

	/**
	 * getAllVitaminDetails: Returns the full list of details for the given persisted
	 * vitamin id.
	 *
	 * @return List The list of vitamin details.
	 * @parampersistedVitaminId
	 */
	public static List<VitaminDetail> getAllVitaminDetails(
		long persistedVitaminId) {

		return getService().getAllVitaminDetails(persistedVitaminId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * getVitaminDetailsByType: Returns the list of details for the given persisted
	 * vitamin id and type.
	 *
	 * @return List The list of vitamin details.
	 * @parampersistedVitaminId
	 * @paramtypeCode
	 */
	public static List<VitaminDetail> getVitaminDetailsByType(
		long persistedVitaminId, int typeCode) {

		return getService().getVitaminDetailsByType(
			persistedVitaminId, typeCode);
	}

	public static VitaminDetailService getService() {
		return _service;
	}

	private static volatile VitaminDetailService _service;

}