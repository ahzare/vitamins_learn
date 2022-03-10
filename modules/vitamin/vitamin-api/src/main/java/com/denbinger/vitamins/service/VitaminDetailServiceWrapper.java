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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link VitaminDetailService}.
 *
 * @author Brian Wing Shun Chan
 * @see VitaminDetailService
 * @generated
 */
public class VitaminDetailServiceWrapper
	implements ServiceWrapper<VitaminDetailService>, VitaminDetailService {

	public VitaminDetailServiceWrapper(
		VitaminDetailService vitaminDetailService) {

		_vitaminDetailService = vitaminDetailService;
	}

	/**
	 * addVitaminDetail: Adds a new vitamin detail record.
	 *
	 * @param value
	 * @parampersistedVitaminId
	 * @paramtypeCode
	 * @returnVitaminDetail: The new instance.
	 */
	@Override
	public com.denbinger.vitamins.model.VitaminDetail addVitaminDetail(
			long persistedVitaminId, int typeCode, String value,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _vitaminDetailService.addVitaminDetail(
			persistedVitaminId, typeCode, value, serviceContext);
	}

	/**
	 * deleteAllVitaminDetails: Deletes all of the vitamin details.
	 *
	 * @parampersistedVitaminId
	 */
	@Override
	public void deleteAllVitaminDetails(long persistedVitaminId) {
		_vitaminDetailService.deleteAllVitaminDetails(persistedVitaminId);
	}

	@Override
	public com.denbinger.vitamins.model.VitaminDetail deleteVitaminDetail(
			long vitaminDetailId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _vitaminDetailService.deleteVitaminDetail(vitaminDetailId);
	}

	@Override
	public com.denbinger.vitamins.model.VitaminDetail deleteVitaminDetail(
		com.denbinger.vitamins.model.VitaminDetail vitaminDetail) {

		return _vitaminDetailService.deleteVitaminDetail(vitaminDetail);
	}

	@Override
	public void deleteVitaminDetailsByType(
		long persistedVitaminId, int typeCode) {

		_vitaminDetailService.deleteVitaminDetailsByType(
			persistedVitaminId, typeCode);
	}

	/**
	 * getAllVitaminDetails: Returns the full list of details for the given persisted
	 * vitamin id.
	 *
	 * @return List The list of vitamin details.
	 * @parampersistedVitaminId
	 */
	@Override
	public java.util.List<com.denbinger.vitamins.model.VitaminDetail>
		getAllVitaminDetails(long persistedVitaminId) {

		return _vitaminDetailService.getAllVitaminDetails(persistedVitaminId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _vitaminDetailService.getOSGiServiceIdentifier();
	}

	/**
	 * getVitaminDetailsByType: Returns the list of details for the given persisted
	 * vitamin id and type.
	 *
	 * @return List The list of vitamin details.
	 * @parampersistedVitaminId
	 * @paramtypeCode
	 */
	@Override
	public java.util.List<com.denbinger.vitamins.model.VitaminDetail>
		getVitaminDetailsByType(long persistedVitaminId, int typeCode) {

		return _vitaminDetailService.getVitaminDetailsByType(
			persistedVitaminId, typeCode);
	}

	@Override
	public VitaminDetailService getWrappedService() {
		return _vitaminDetailService;
	}

	@Override
	public void setWrappedService(VitaminDetailService vitaminDetailService) {
		_vitaminDetailService = vitaminDetailService;
	}

	private VitaminDetailService _vitaminDetailService;

}