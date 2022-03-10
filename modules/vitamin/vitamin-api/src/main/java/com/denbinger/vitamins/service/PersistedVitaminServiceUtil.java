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

import com.denbinger.vitamins.model.PersistedVitamin;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for PersistedVitamin. This utility wraps
 * <code>com.denbinger.vitamins.service.impl.PersistedVitaminServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PersistedVitaminService
 * @generated
 */
public class PersistedVitaminServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.denbinger.vitamins.service.impl.PersistedVitaminServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static PersistedVitamin addPersistedVitamin(
			String id, String name, String groupName, String description,
			int typeCode, String articleId, String[] chemicalNames,
			String[] properties, String[] attributes, String[] symptoms,
			String[] risks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addPersistedVitamin(
			id, name, groupName, description, typeCode, articleId,
			chemicalNames, properties, attributes, symptoms, risks,
			serviceContext);
	}

	public static void deletePersistedVitamin(String surrogateId)
		throws PortalException {

		getService().deletePersistedVitamin(surrogateId);
	}

	public static List<PersistedVitamin> getAll() {
		return getService().getAll();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static PersistedVitamin getPersistedVitamin(long persistedVitaminId)
		throws PortalException {

		return getService().getPersistedVitamin(persistedVitaminId);
	}

	public static PersistedVitamin getPersistedVitamin(String surrogateId)
		throws PortalException {

		return getService().getPersistedVitamin(surrogateId);
	}

	public static PersistedVitamin patchPersistedVitamin(
			String oldId, String id, String name, String groupName,
			String description, int typeCode, String articleId,
			String[] chemicalNames, String[] properties, String[] attributes,
			String[] symptoms, String[] risks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().patchPersistedVitamin(
			oldId, id, name, groupName, description, typeCode, articleId,
			chemicalNames, properties, attributes, symptoms, risks,
			serviceContext);
	}

	public static PersistedVitamin updatePersistedVitamin(
			String oldId, String id, String name, String groupName,
			String description, int typeCode, String articleId,
			String[] chemicalNames, String[] properties, String[] attributes,
			String[] symptoms, String[] risks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updatePersistedVitamin(
			oldId, id, name, groupName, description, typeCode, articleId,
			chemicalNames, properties, attributes, symptoms, risks,
			serviceContext);
	}

	public static PersistedVitaminService getService() {
		return _service;
	}

	private static volatile PersistedVitaminService _service;

}