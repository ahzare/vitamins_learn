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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for PersistedVitamin. This utility wraps
 * <code>com.denbinger.vitamins.service.impl.PersistedVitaminLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PersistedVitaminLocalService
 * @generated
 */
public class PersistedVitaminLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.denbinger.vitamins.service.impl.PersistedVitaminLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the persisted vitamin to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PersistedVitaminLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param persistedVitamin the persisted vitamin
	 * @return the persisted vitamin that was added
	 */
	public static PersistedVitamin addPersistedVitamin(
		PersistedVitamin persistedVitamin) {

		return getService().addPersistedVitamin(persistedVitamin);
	}

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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new persisted vitamin with the primary key. Does not add the persisted vitamin to the database.
	 *
	 * @param persistedVitaminId the primary key for the new persisted vitamin
	 * @return the new persisted vitamin
	 */
	public static PersistedVitamin createPersistedVitamin(
		long persistedVitaminId) {

		return getService().createPersistedVitamin(persistedVitaminId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the persisted vitamin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PersistedVitaminLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param persistedVitaminId the primary key of the persisted vitamin
	 * @return the persisted vitamin that was removed
	 * @throws PortalException if a persisted vitamin with the primary key could not be found
	 */
	public static PersistedVitamin deletePersistedVitamin(
			long persistedVitaminId)
		throws PortalException {

		return getService().deletePersistedVitamin(persistedVitaminId);
	}

	/**
	 * Deletes the persisted vitamin from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PersistedVitaminLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param persistedVitamin the persisted vitamin
	 * @return the persisted vitamin that was removed
	 */
	public static PersistedVitamin deletePersistedVitamin(
		PersistedVitamin persistedVitamin) {

		return getService().deletePersistedVitamin(persistedVitamin);
	}

	public static PersistedVitamin deleteVitamin(long persistedVitaminId)
		throws PortalException {

		return getService().deleteVitamin(persistedVitaminId);
	}

	public static PersistedVitamin deleteVitamin(
		PersistedVitamin persistedVitamin) {

		return getService().deleteVitamin(persistedVitamin);
	}

	public static void deleteVitamin(String surrogateId) {
		getService().deleteVitamin(surrogateId);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.denbinger.vitamins.model.impl.PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.denbinger.vitamins.model.impl.PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static PersistedVitamin fetchPersistedVitamin(
		long persistedVitaminId) {

		return getService().fetchPersistedVitamin(persistedVitaminId);
	}

	/**
	 * Returns the persisted vitamin matching the UUID and group.
	 *
	 * @param uuid the persisted vitamin's UUID
	 * @param groupId the primary key of the group
	 * @return the matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	public static PersistedVitamin fetchPersistedVitaminByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchPersistedVitaminByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
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
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the persisted vitamin with the primary key.
	 *
	 * @param persistedVitaminId the primary key of the persisted vitamin
	 * @return the persisted vitamin
	 * @throws PortalException if a persisted vitamin with the primary key could not be found
	 */
	public static PersistedVitamin getPersistedVitamin(long persistedVitaminId)
		throws PortalException {

		return getService().getPersistedVitamin(persistedVitaminId);
	}

	public static PersistedVitamin getPersistedVitamin(String surrogateId) {
		return getService().getPersistedVitamin(surrogateId);
	}

	/**
	 * Returns the persisted vitamin matching the UUID and group.
	 *
	 * @param uuid the persisted vitamin's UUID
	 * @param groupId the primary key of the group
	 * @return the matching persisted vitamin
	 * @throws PortalException if a matching persisted vitamin could not be found
	 */
	public static PersistedVitamin getPersistedVitaminByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getPersistedVitaminByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the persisted vitamins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.denbinger.vitamins.model.impl.PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @return the range of persisted vitamins
	 */
	public static List<PersistedVitamin> getPersistedVitamins(
		int start, int end) {

		return getService().getPersistedVitamins(start, end);
	}

	/**
	 * Returns all the persisted vitamins matching the UUID and company.
	 *
	 * @param uuid the UUID of the persisted vitamins
	 * @param companyId the primary key of the company
	 * @return the matching persisted vitamins, or an empty list if no matches were found
	 */
	public static List<PersistedVitamin> getPersistedVitaminsByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getPersistedVitaminsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of persisted vitamins matching the UUID and company.
	 *
	 * @param uuid the UUID of the persisted vitamins
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching persisted vitamins, or an empty list if no matches were found
	 */
	public static List<PersistedVitamin> getPersistedVitaminsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PersistedVitamin> orderByComparator) {

		return getService().getPersistedVitaminsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of persisted vitamins.
	 *
	 * @return the number of persisted vitamins
	 */
	public static int getPersistedVitaminsCount() {
		return getService().getPersistedVitaminsCount();
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

	/**
	 * Updates the persisted vitamin in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PersistedVitaminLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param persistedVitamin the persisted vitamin
	 * @return the persisted vitamin that was updated
	 */
	public static PersistedVitamin updatePersistedVitamin(
		PersistedVitamin persistedVitamin) {

		return getService().updatePersistedVitamin(persistedVitamin);
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

	public static PersistedVitaminLocalService getService() {
		return _service;
	}

	private static volatile PersistedVitaminLocalService _service;

}