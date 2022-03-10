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

package com.denbinger.vitamins.service.impl;

import com.denbinger.vitamins.model.PersistedVitamin;
import com.denbinger.vitamins.service.base.PersistedVitaminServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=foo",
		"json.web.service.context.path=PersistedVitamin"
	},
	service = AopService.class
)
public class PersistedVitaminServiceImpl
	extends PersistedVitaminServiceBaseImpl {
	@Reference(
			policy = ReferencePolicy.DYNAMIC,
			policyOption= ReferencePolicyOption.GREEDY,
			target ="(model.class.name=com.denbinger.vitamins.model.PersistedVitamin)"
	)
	private volatile ModelResourcePermission<PersistedVitamin>
			_persistedVitaminModelResourcePermission;
	public PersistedVitamin getPersistedVitamin(final String surrogateId)
	throws PortalException{
		PersistedVitamin vitamin =
				persistedVitaminLocalService.getPersistedVitamin(surrogateId);
		_persistedVitaminModelResourcePermission.check(
				getPermissionChecker(), vitamin,ActionKeys.VIEW);
		return vitamin;
	}
	public PersistedVitamin getPersistedVitamin(final long persistedVitaminId)
	throws PortalException{
		PersistedVitamin vitamin =
				persistedVitaminLocalService.getPersistedVitamin(
						persistedVitaminId);
		_persistedVitaminModelResourcePermission.check(
				getPermissionChecker(), vitamin,ActionKeys.VIEW);
		return vitamin;
	}
	public PersistedVitamin addPersistedVitamin(final String id,final String name,
											  final String groupName,
											  final String description,
											  final int typeCode,
											  final String articleId,
											  final String[]chemicalNames,
											  final String[] properties,
											  final String[] attributes,
											  final String[] symptoms,
											  final String[] risks,
											  final ServiceContext serviceContext)
	throws PortalException{
		ModelResourcePermissionHelper.check(
				_persistedVitaminModelResourcePermission,getPermissionChecker(),
				serviceContext.getScopeGroupId(),0,
				ActionKeys.ADD_ENTRY);
		return persistedVitaminLocalService.addPersistedVitamin(id, name,
				groupName,description,typeCode,articleId,chemicalNames,
				properties, attributes, symptoms, risks,serviceContext);
	}
	public PersistedVitamin updatePersistedVitamin(final String oldId,
												 final String id,
												 final String name,
												 final String groupName,
												 final String description,
												 final int typeCode,
												 final String articleId,
												 final String[]chemicalNames,
												 final String[] properties,
												 final String[] attributes,
												 final String[] symptoms,
												 final String[] risks,
												 final ServiceContext serviceContext)
	throws PortalException{
		_persistedVitaminModelResourcePermission.check(
				getPermissionChecker(),
				persistedVitaminLocalService.getPersistedVitamin(oldId),
				ActionKeys.UPDATE);
		return persistedVitaminLocalService.updatePersistedVitamin(oldId, id,
				name,groupName, description,typeCode,articleId,chemicalNames,
				properties, attributes, symptoms, risks,serviceContext);
	}
	public PersistedVitamin patchPersistedVitamin(final String oldId,final String id,
												final String name,
												final String groupName,
												final String description,
												final int typeCode,
												final String articleId,
												final String[]chemicalNames,
												final String[] properties,
												final String[] attributes,
												final String[] symptoms,
												final String[] risks,
												final ServiceContext serviceContext)
	throws PortalException{
		_persistedVitaminModelResourcePermission.check(
				getPermissionChecker(),
				persistedVitaminLocalService.getPersistedVitamin(oldId),
				ActionKeys.UPDATE);
		return persistedVitaminLocalService.patchPersistedVitamin(oldId, id,
				name,groupName, description,
				typeCode,articleId,chemicalNames, properties, attributes,
				symptoms, risks,serviceContext);
	}
	public void deletePersistedVitamin(final String surrogateId)
	throws PortalException {
		_persistedVitaminModelResourcePermission.check(
				getPermissionChecker(),
				persistedVitaminLocalService.getPersistedVitamin(surrogateId),
				ActionKeys.DELETE);
		persistedVitaminLocalService.deleteVitamin(surrogateId);
	}
	public List<PersistedVitamin> getAll(){
		return persistedVitaminPersistence.findAll();
	}
	private static final Logger _log =
			LoggerFactory.getLogger(PersistedVitaminServiceImpl.class);

}