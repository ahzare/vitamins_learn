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

import com.denbinger.vitamins.service.VitaminDetailServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>VitaminDetailServiceUtil</code> service
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
 * @see VitaminDetailServiceSoap
 * @generated
 */
public class VitaminDetailServiceHttp {

	public static com.denbinger.vitamins.model.VitaminDetail addVitaminDetail(
			HttpPrincipal httpPrincipal, long persistedVitaminId, int typeCode,
			String value,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				VitaminDetailServiceUtil.class, "addVitaminDetail",
				_addVitaminDetailParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, persistedVitaminId, typeCode, value, serviceContext);

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

			return (com.denbinger.vitamins.model.VitaminDetail)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteAllVitaminDetails(
		HttpPrincipal httpPrincipal, long persistedVitaminId) {

		try {
			MethodKey methodKey = new MethodKey(
				VitaminDetailServiceUtil.class, "deleteAllVitaminDetails",
				_deleteAllVitaminDetailsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, persistedVitaminId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	public static void deleteVitaminDetailsByType(
		HttpPrincipal httpPrincipal, long persistedVitaminId, int typeCode) {

		try {
			MethodKey methodKey = new MethodKey(
				VitaminDetailServiceUtil.class, "deleteVitaminDetailsByType",
				_deleteVitaminDetailsByTypeParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, persistedVitaminId, typeCode);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	public static com.denbinger.vitamins.model.VitaminDetail
			deleteVitaminDetail(
				HttpPrincipal httpPrincipal, long vitaminDetailId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				VitaminDetailServiceUtil.class, "deleteVitaminDetail",
				_deleteVitaminDetailParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vitaminDetailId);

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

			return (com.denbinger.vitamins.model.VitaminDetail)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.denbinger.vitamins.model.VitaminDetail
		deleteVitaminDetail(
			HttpPrincipal httpPrincipal,
			com.denbinger.vitamins.model.VitaminDetail vitaminDetail) {

		try {
			MethodKey methodKey = new MethodKey(
				VitaminDetailServiceUtil.class, "deleteVitaminDetail",
				_deleteVitaminDetailParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, vitaminDetail);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.denbinger.vitamins.model.VitaminDetail)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.denbinger.vitamins.model.VitaminDetail>
		getAllVitaminDetails(
			HttpPrincipal httpPrincipal, long persistedVitaminId) {

		try {
			MethodKey methodKey = new MethodKey(
				VitaminDetailServiceUtil.class, "getAllVitaminDetails",
				_getAllVitaminDetailsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, persistedVitaminId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.denbinger.vitamins.model.VitaminDetail>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.denbinger.vitamins.model.VitaminDetail>
		getVitaminDetailsByType(
			HttpPrincipal httpPrincipal, long persistedVitaminId,
			int typeCode) {

		try {
			MethodKey methodKey = new MethodKey(
				VitaminDetailServiceUtil.class, "getVitaminDetailsByType",
				_getVitaminDetailsByTypeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, persistedVitaminId, typeCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.denbinger.vitamins.model.VitaminDetail>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		VitaminDetailServiceHttp.class);

	private static final Class<?>[] _addVitaminDetailParameterTypes0 =
		new Class[] {
			long.class, int.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteAllVitaminDetailsParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteVitaminDetailsByTypeParameterTypes2 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _deleteVitaminDetailParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteVitaminDetailParameterTypes4 =
		new Class[] {com.denbinger.vitamins.model.VitaminDetail.class};
	private static final Class<?>[] _getAllVitaminDetailsParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _getVitaminDetailsByTypeParameterTypes6 =
		new Class[] {long.class, int.class};

}