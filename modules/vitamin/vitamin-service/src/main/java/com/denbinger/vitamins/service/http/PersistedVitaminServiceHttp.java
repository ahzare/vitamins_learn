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

package com.denbinger.vitamins.service.http;

import com.denbinger.vitamins.service.PersistedVitaminServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>PersistedVitaminServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PersistedVitaminServiceSoap
 * @generated
 */
public class PersistedVitaminServiceHttp {

	public static com.denbinger.vitamins.model.PersistedVitamin
			getPersistedVitamin(HttpPrincipal httpPrincipal, String surrogateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PersistedVitaminServiceUtil.class, "getPersistedVitamin",
				_getPersistedVitaminParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, surrogateId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.denbinger.vitamins.model.PersistedVitamin)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.denbinger.vitamins.model.PersistedVitamin
			getPersistedVitamin(
				HttpPrincipal httpPrincipal, long persistedVitaminId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PersistedVitaminServiceUtil.class, "getPersistedVitamin",
				_getPersistedVitaminParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, persistedVitaminId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.denbinger.vitamins.model.PersistedVitamin)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.denbinger.vitamins.model.PersistedVitamin
			addPersistedVitamin(
				HttpPrincipal httpPrincipal, String id, String name,
				String groupName, String description, int typeCode,
				String articleId, String[] chemicalNames, String[] properties,
				String[] attributes, String[] symptoms, String[] risks,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PersistedVitaminServiceUtil.class, "addPersistedVitamin",
				_addPersistedVitaminParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, id, name, groupName, description, typeCode,
				articleId, chemicalNames, properties, attributes, symptoms,
				risks, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.denbinger.vitamins.model.PersistedVitamin)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.denbinger.vitamins.model.PersistedVitamin
			updatePersistedVitamin(
				HttpPrincipal httpPrincipal, String oldId, String id,
				String name, String groupName, String description, int typeCode,
				String articleId, String[] chemicalNames, String[] properties,
				String[] attributes, String[] symptoms, String[] risks,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PersistedVitaminServiceUtil.class, "updatePersistedVitamin",
				_updatePersistedVitaminParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oldId, id, name, groupName, description, typeCode,
				articleId, chemicalNames, properties, attributes, symptoms,
				risks, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.denbinger.vitamins.model.PersistedVitamin)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.denbinger.vitamins.model.PersistedVitamin
			patchPersistedVitamin(
				HttpPrincipal httpPrincipal, String oldId, String id,
				String name, String groupName, String description, int typeCode,
				String articleId, String[] chemicalNames, String[] properties,
				String[] attributes, String[] symptoms, String[] risks,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PersistedVitaminServiceUtil.class, "patchPersistedVitamin",
				_patchPersistedVitaminParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oldId, id, name, groupName, description, typeCode,
				articleId, chemicalNames, properties, attributes, symptoms,
				risks, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.denbinger.vitamins.model.PersistedVitamin)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deletePersistedVitamin(
			HttpPrincipal httpPrincipal, String surrogateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PersistedVitaminServiceUtil.class, "deletePersistedVitamin",
				_deletePersistedVitaminParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, surrogateId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.denbinger.vitamins.model.PersistedVitamin>
		getAll(HttpPrincipal httpPrincipal) {

		try {
			MethodKey methodKey = new MethodKey(
				PersistedVitaminServiceUtil.class, "getAll",
				_getAllParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.denbinger.vitamins.model.PersistedVitamin>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PersistedVitaminServiceHttp.class);

	private static final Class<?>[] _getPersistedVitaminParameterTypes0 =
		new Class[] {String.class};
	private static final Class<?>[] _getPersistedVitaminParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _addPersistedVitaminParameterTypes2 =
		new Class[] {
			String.class, String.class, String.class, String.class, int.class,
			String.class, String[].class, String[].class, String[].class,
			String[].class, String[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updatePersistedVitaminParameterTypes3 =
		new Class[] {
			String.class, String.class, String.class, String.class,
			String.class, int.class, String.class, String[].class,
			String[].class, String[].class, String[].class, String[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _patchPersistedVitaminParameterTypes4 =
		new Class[] {
			String.class, String.class, String.class, String.class,
			String.class, int.class, String.class, String[].class,
			String[].class, String[].class, String[].class, String[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deletePersistedVitaminParameterTypes5 =
		new Class[] {String.class};
	private static final Class<?>[] _getAllParameterTypes6 = new Class[] {};

}