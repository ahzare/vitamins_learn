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

package com.denbinger.vitamins.service.persistence.impl;

import com.denbinger.vitamins.exception.NoSuchVitaminDetailException;
import com.denbinger.vitamins.model.VitaminDetail;
import com.denbinger.vitamins.model.impl.VitaminDetailImpl;
import com.denbinger.vitamins.model.impl.VitaminDetailModelImpl;
import com.denbinger.vitamins.service.persistence.VitaminDetailPersistence;
import com.denbinger.vitamins.service.persistence.VitaminDetailUtil;
import com.denbinger.vitamins.service.persistence.impl.constants.FOOPersistenceConstants;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the vitamin detail service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = VitaminDetailPersistence.class)
public class VitaminDetailPersistenceImpl
	extends BasePersistenceImpl<VitaminDetail>
	implements VitaminDetailPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>VitaminDetailUtil</code> to access the vitamin detail persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		VitaminDetailImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the vitamin details where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the vitamin details where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @return the range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the vitamin details where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the vitamin details where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<VitaminDetail> list = null;

		if (useFinderCache) {
			list = (List<VitaminDetail>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VitaminDetail vitaminDetail : list) {
					if (!uuid.equals(vitaminDetail.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(VitaminDetailModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<VitaminDetail>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first vitamin detail in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findByUuid_First(
			String uuid, OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchByUuid_First(
			uuid, orderByComparator);

		if (vitaminDetail != null) {
			return vitaminDetail;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVitaminDetailException(sb.toString());
	}

	/**
	 * Returns the first vitamin detail in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchByUuid_First(
		String uuid, OrderByComparator<VitaminDetail> orderByComparator) {

		List<VitaminDetail> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last vitamin detail in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findByUuid_Last(
			String uuid, OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchByUuid_Last(uuid, orderByComparator);

		if (vitaminDetail != null) {
			return vitaminDetail;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVitaminDetailException(sb.toString());
	}

	/**
	 * Returns the last vitamin detail in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchByUuid_Last(
		String uuid, OrderByComparator<VitaminDetail> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<VitaminDetail> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the vitamin details before and after the current vitamin detail in the ordered set where uuid = &#63;.
	 *
	 * @param vitaminDetailId the primary key of the current vitamin detail
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vitamin detail
	 * @throws NoSuchVitaminDetailException if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail[] findByUuid_PrevAndNext(
			long vitaminDetailId, String uuid,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		uuid = Objects.toString(uuid, "");

		VitaminDetail vitaminDetail = findByPrimaryKey(vitaminDetailId);

		Session session = null;

		try {
			session = openSession();

			VitaminDetail[] array = new VitaminDetailImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, vitaminDetail, uuid, orderByComparator, true);

			array[1] = vitaminDetail;

			array[2] = getByUuid_PrevAndNext(
				session, vitaminDetail, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected VitaminDetail getByUuid_PrevAndNext(
		Session session, VitaminDetail vitaminDetail, String uuid,
		OrderByComparator<VitaminDetail> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(VitaminDetailModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						vitaminDetail)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<VitaminDetail> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the vitamin details where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (VitaminDetail vitaminDetail :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(vitaminDetail);
		}
	}

	/**
	 * Returns the number of vitamin details where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching vitamin details
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_VITAMINDETAIL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"vitaminDetail.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(vitaminDetail.uuid IS NULL OR vitaminDetail.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the vitamin detail where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVitaminDetailException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findByUUID_G(String uuid, long groupId)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchByUUID_G(uuid, groupId);

		if (vitaminDetail == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchVitaminDetailException(sb.toString());
		}

		return vitaminDetail;
	}

	/**
	 * Returns the vitamin detail where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the vitamin detail where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof VitaminDetail) {
			VitaminDetail vitaminDetail = (VitaminDetail)result;

			if (!Objects.equals(uuid, vitaminDetail.getUuid()) ||
				(groupId != vitaminDetail.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<VitaminDetail> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					VitaminDetail vitaminDetail = list.get(0);

					result = vitaminDetail;

					cacheResult(vitaminDetail);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (VitaminDetail)result;
		}
	}

	/**
	 * Removes the vitamin detail where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the vitamin detail that was removed
	 */
	@Override
	public VitaminDetail removeByUUID_G(String uuid, long groupId)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = findByUUID_G(uuid, groupId);

		return remove(vitaminDetail);
	}

	/**
	 * Returns the number of vitamin details where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching vitamin details
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_VITAMINDETAIL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"vitaminDetail.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(vitaminDetail.uuid IS NULL OR vitaminDetail.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"vitaminDetail.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the vitamin details where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the vitamin details where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @return the range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the vitamin details where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the vitamin details where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<VitaminDetail> list = null;

		if (useFinderCache) {
			list = (List<VitaminDetail>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VitaminDetail vitaminDetail : list) {
					if (!uuid.equals(vitaminDetail.getUuid()) ||
						(companyId != vitaminDetail.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(VitaminDetailModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<VitaminDetail>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first vitamin detail in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (vitaminDetail != null) {
			return vitaminDetail;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVitaminDetailException(sb.toString());
	}

	/**
	 * Returns the first vitamin detail in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<VitaminDetail> orderByComparator) {

		List<VitaminDetail> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last vitamin detail in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (vitaminDetail != null) {
			return vitaminDetail;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVitaminDetailException(sb.toString());
	}

	/**
	 * Returns the last vitamin detail in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<VitaminDetail> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<VitaminDetail> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the vitamin details before and after the current vitamin detail in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param vitaminDetailId the primary key of the current vitamin detail
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vitamin detail
	 * @throws NoSuchVitaminDetailException if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail[] findByUuid_C_PrevAndNext(
			long vitaminDetailId, String uuid, long companyId,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		uuid = Objects.toString(uuid, "");

		VitaminDetail vitaminDetail = findByPrimaryKey(vitaminDetailId);

		Session session = null;

		try {
			session = openSession();

			VitaminDetail[] array = new VitaminDetailImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, vitaminDetail, uuid, companyId, orderByComparator,
				true);

			array[1] = vitaminDetail;

			array[2] = getByUuid_C_PrevAndNext(
				session, vitaminDetail, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected VitaminDetail getByUuid_C_PrevAndNext(
		Session session, VitaminDetail vitaminDetail, String uuid,
		long companyId, OrderByComparator<VitaminDetail> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(VitaminDetailModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						vitaminDetail)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<VitaminDetail> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the vitamin details where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (VitaminDetail vitaminDetail :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(vitaminDetail);
		}
	}

	/**
	 * Returns the number of vitamin details where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching vitamin details
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_VITAMINDETAIL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"vitaminDetail.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(vitaminDetail.uuid IS NULL OR vitaminDetail.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"vitaminDetail.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByPersistedVitaminId;
	private FinderPath _finderPathWithoutPaginationFindByPersistedVitaminId;
	private FinderPath _finderPathCountByPersistedVitaminId;

	/**
	 * Returns all the vitamin details where persistedVitaminId = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @return the matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByPersistedVitaminId(
		long persistedVitaminId) {

		return findByPersistedVitaminId(
			persistedVitaminId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the vitamin details where persistedVitaminId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @return the range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByPersistedVitaminId(
		long persistedVitaminId, int start, int end) {

		return findByPersistedVitaminId(persistedVitaminId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the vitamin details where persistedVitaminId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByPersistedVitaminId(
		long persistedVitaminId, int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator) {

		return findByPersistedVitaminId(
			persistedVitaminId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the vitamin details where persistedVitaminId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findByPersistedVitaminId(
		long persistedVitaminId, int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByPersistedVitaminId;
				finderArgs = new Object[] {persistedVitaminId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPersistedVitaminId;
			finderArgs = new Object[] {
				persistedVitaminId, start, end, orderByComparator
			};
		}

		List<VitaminDetail> list = null;

		if (useFinderCache) {
			list = (List<VitaminDetail>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VitaminDetail vitaminDetail : list) {
					if (persistedVitaminId !=
							vitaminDetail.getPersistedVitaminId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

			sb.append(_FINDER_COLUMN_PERSISTEDVITAMINID_PERSISTEDVITAMINID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(VitaminDetailModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(persistedVitaminId);

				list = (List<VitaminDetail>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first vitamin detail in the ordered set where persistedVitaminId = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findByPersistedVitaminId_First(
			long persistedVitaminId,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchByPersistedVitaminId_First(
			persistedVitaminId, orderByComparator);

		if (vitaminDetail != null) {
			return vitaminDetail;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("persistedVitaminId=");
		sb.append(persistedVitaminId);

		sb.append("}");

		throw new NoSuchVitaminDetailException(sb.toString());
	}

	/**
	 * Returns the first vitamin detail in the ordered set where persistedVitaminId = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchByPersistedVitaminId_First(
		long persistedVitaminId,
		OrderByComparator<VitaminDetail> orderByComparator) {

		List<VitaminDetail> list = findByPersistedVitaminId(
			persistedVitaminId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last vitamin detail in the ordered set where persistedVitaminId = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findByPersistedVitaminId_Last(
			long persistedVitaminId,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchByPersistedVitaminId_Last(
			persistedVitaminId, orderByComparator);

		if (vitaminDetail != null) {
			return vitaminDetail;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("persistedVitaminId=");
		sb.append(persistedVitaminId);

		sb.append("}");

		throw new NoSuchVitaminDetailException(sb.toString());
	}

	/**
	 * Returns the last vitamin detail in the ordered set where persistedVitaminId = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchByPersistedVitaminId_Last(
		long persistedVitaminId,
		OrderByComparator<VitaminDetail> orderByComparator) {

		int count = countByPersistedVitaminId(persistedVitaminId);

		if (count == 0) {
			return null;
		}

		List<VitaminDetail> list = findByPersistedVitaminId(
			persistedVitaminId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the vitamin details before and after the current vitamin detail in the ordered set where persistedVitaminId = &#63;.
	 *
	 * @param vitaminDetailId the primary key of the current vitamin detail
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vitamin detail
	 * @throws NoSuchVitaminDetailException if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail[] findByPersistedVitaminId_PrevAndNext(
			long vitaminDetailId, long persistedVitaminId,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = findByPrimaryKey(vitaminDetailId);

		Session session = null;

		try {
			session = openSession();

			VitaminDetail[] array = new VitaminDetailImpl[3];

			array[0] = getByPersistedVitaminId_PrevAndNext(
				session, vitaminDetail, persistedVitaminId, orderByComparator,
				true);

			array[1] = vitaminDetail;

			array[2] = getByPersistedVitaminId_PrevAndNext(
				session, vitaminDetail, persistedVitaminId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected VitaminDetail getByPersistedVitaminId_PrevAndNext(
		Session session, VitaminDetail vitaminDetail, long persistedVitaminId,
		OrderByComparator<VitaminDetail> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

		sb.append(_FINDER_COLUMN_PERSISTEDVITAMINID_PERSISTEDVITAMINID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(VitaminDetailModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(persistedVitaminId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						vitaminDetail)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<VitaminDetail> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the vitamin details where persistedVitaminId = &#63; from the database.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 */
	@Override
	public void removeByPersistedVitaminId(long persistedVitaminId) {
		for (VitaminDetail vitaminDetail :
				findByPersistedVitaminId(
					persistedVitaminId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(vitaminDetail);
		}
	}

	/**
	 * Returns the number of vitamin details where persistedVitaminId = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @return the number of matching vitamin details
	 */
	@Override
	public int countByPersistedVitaminId(long persistedVitaminId) {
		FinderPath finderPath = _finderPathCountByPersistedVitaminId;

		Object[] finderArgs = new Object[] {persistedVitaminId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_VITAMINDETAIL_WHERE);

			sb.append(_FINDER_COLUMN_PERSISTEDVITAMINID_PERSISTEDVITAMINID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(persistedVitaminId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_PERSISTEDVITAMINID_PERSISTEDVITAMINID_2 =
			"vitaminDetail.persistedVitaminId = ?";

	private FinderPath _finderPathWithPaginationFindBypersistedVitaminIdType;
	private FinderPath _finderPathWithoutPaginationFindBypersistedVitaminIdType;
	private FinderPath _finderPathCountBypersistedVitaminIdType;

	/**
	 * Returns all the vitamin details where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @return the matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findBypersistedVitaminIdType(
		long persistedVitaminId, int type) {

		return findBypersistedVitaminIdType(
			persistedVitaminId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the vitamin details where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @return the range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findBypersistedVitaminIdType(
		long persistedVitaminId, int type, int start, int end) {

		return findBypersistedVitaminIdType(
			persistedVitaminId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the vitamin details where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findBypersistedVitaminIdType(
		long persistedVitaminId, int type, int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator) {

		return findBypersistedVitaminIdType(
			persistedVitaminId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the vitamin details where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching vitamin details
	 */
	@Override
	public List<VitaminDetail> findBypersistedVitaminIdType(
		long persistedVitaminId, int type, int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindBypersistedVitaminIdType;
				finderArgs = new Object[] {persistedVitaminId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBypersistedVitaminIdType;
			finderArgs = new Object[] {
				persistedVitaminId, type, start, end, orderByComparator
			};
		}

		List<VitaminDetail> list = null;

		if (useFinderCache) {
			list = (List<VitaminDetail>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VitaminDetail vitaminDetail : list) {
					if ((persistedVitaminId !=
							vitaminDetail.getPersistedVitaminId()) ||
						(type != vitaminDetail.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

			sb.append(
				_FINDER_COLUMN_PERSISTEDVITAMINIDTYPE_PERSISTEDVITAMINID_2);

			sb.append(_FINDER_COLUMN_PERSISTEDVITAMINIDTYPE_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(VitaminDetailModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(persistedVitaminId);

				queryPos.add(type);

				list = (List<VitaminDetail>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first vitamin detail in the ordered set where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findBypersistedVitaminIdType_First(
			long persistedVitaminId, int type,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchBypersistedVitaminIdType_First(
			persistedVitaminId, type, orderByComparator);

		if (vitaminDetail != null) {
			return vitaminDetail;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("persistedVitaminId=");
		sb.append(persistedVitaminId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchVitaminDetailException(sb.toString());
	}

	/**
	 * Returns the first vitamin detail in the ordered set where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchBypersistedVitaminIdType_First(
		long persistedVitaminId, int type,
		OrderByComparator<VitaminDetail> orderByComparator) {

		List<VitaminDetail> list = findBypersistedVitaminIdType(
			persistedVitaminId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last vitamin detail in the ordered set where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vitamin detail
	 * @throws NoSuchVitaminDetailException if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail findBypersistedVitaminIdType_Last(
			long persistedVitaminId, int type,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchBypersistedVitaminIdType_Last(
			persistedVitaminId, type, orderByComparator);

		if (vitaminDetail != null) {
			return vitaminDetail;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("persistedVitaminId=");
		sb.append(persistedVitaminId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchVitaminDetailException(sb.toString());
	}

	/**
	 * Returns the last vitamin detail in the ordered set where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vitamin detail, or <code>null</code> if a matching vitamin detail could not be found
	 */
	@Override
	public VitaminDetail fetchBypersistedVitaminIdType_Last(
		long persistedVitaminId, int type,
		OrderByComparator<VitaminDetail> orderByComparator) {

		int count = countBypersistedVitaminIdType(persistedVitaminId, type);

		if (count == 0) {
			return null;
		}

		List<VitaminDetail> list = findBypersistedVitaminIdType(
			persistedVitaminId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the vitamin details before and after the current vitamin detail in the ordered set where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * @param vitaminDetailId the primary key of the current vitamin detail
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vitamin detail
	 * @throws NoSuchVitaminDetailException if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail[] findBypersistedVitaminIdType_PrevAndNext(
			long vitaminDetailId, long persistedVitaminId, int type,
			OrderByComparator<VitaminDetail> orderByComparator)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = findByPrimaryKey(vitaminDetailId);

		Session session = null;

		try {
			session = openSession();

			VitaminDetail[] array = new VitaminDetailImpl[3];

			array[0] = getBypersistedVitaminIdType_PrevAndNext(
				session, vitaminDetail, persistedVitaminId, type,
				orderByComparator, true);

			array[1] = vitaminDetail;

			array[2] = getBypersistedVitaminIdType_PrevAndNext(
				session, vitaminDetail, persistedVitaminId, type,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected VitaminDetail getBypersistedVitaminIdType_PrevAndNext(
		Session session, VitaminDetail vitaminDetail, long persistedVitaminId,
		int type, OrderByComparator<VitaminDetail> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_VITAMINDETAIL_WHERE);

		sb.append(_FINDER_COLUMN_PERSISTEDVITAMINIDTYPE_PERSISTEDVITAMINID_2);

		sb.append(_FINDER_COLUMN_PERSISTEDVITAMINIDTYPE_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(VitaminDetailModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(persistedVitaminId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						vitaminDetail)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<VitaminDetail> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the vitamin details where persistedVitaminId = &#63; and type = &#63; from the database.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 */
	@Override
	public void removeBypersistedVitaminIdType(
		long persistedVitaminId, int type) {

		for (VitaminDetail vitaminDetail :
				findBypersistedVitaminIdType(
					persistedVitaminId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(vitaminDetail);
		}
	}

	/**
	 * Returns the number of vitamin details where persistedVitaminId = &#63; and type = &#63;.
	 *
	 * @param persistedVitaminId the persisted vitamin ID
	 * @param type the type
	 * @return the number of matching vitamin details
	 */
	@Override
	public int countBypersistedVitaminIdType(
		long persistedVitaminId, int type) {

		FinderPath finderPath = _finderPathCountBypersistedVitaminIdType;

		Object[] finderArgs = new Object[] {persistedVitaminId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_VITAMINDETAIL_WHERE);

			sb.append(
				_FINDER_COLUMN_PERSISTEDVITAMINIDTYPE_PERSISTEDVITAMINID_2);

			sb.append(_FINDER_COLUMN_PERSISTEDVITAMINIDTYPE_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(persistedVitaminId);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_PERSISTEDVITAMINIDTYPE_PERSISTEDVITAMINID_2 =
			"vitaminDetail.persistedVitaminId = ? AND ";

	private static final String _FINDER_COLUMN_PERSISTEDVITAMINIDTYPE_TYPE_2 =
		"vitaminDetail.type = ?";

	public VitaminDetailPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(VitaminDetail.class);

		setModelImplClass(VitaminDetailImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the vitamin detail in the entity cache if it is enabled.
	 *
	 * @param vitaminDetail the vitamin detail
	 */
	@Override
	public void cacheResult(VitaminDetail vitaminDetail) {
		entityCache.putResult(
			VitaminDetailImpl.class, vitaminDetail.getPrimaryKey(),
			vitaminDetail);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {vitaminDetail.getUuid(), vitaminDetail.getGroupId()},
			vitaminDetail);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the vitamin details in the entity cache if it is enabled.
	 *
	 * @param vitaminDetails the vitamin details
	 */
	@Override
	public void cacheResult(List<VitaminDetail> vitaminDetails) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (vitaminDetails.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (VitaminDetail vitaminDetail : vitaminDetails) {
			if (entityCache.getResult(
					VitaminDetailImpl.class, vitaminDetail.getPrimaryKey()) ==
						null) {

				cacheResult(vitaminDetail);
			}
		}
	}

	/**
	 * Clears the cache for all vitamin details.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(VitaminDetailImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the vitamin detail.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(VitaminDetail vitaminDetail) {
		entityCache.removeResult(VitaminDetailImpl.class, vitaminDetail);
	}

	@Override
	public void clearCache(List<VitaminDetail> vitaminDetails) {
		for (VitaminDetail vitaminDetail : vitaminDetails) {
			entityCache.removeResult(VitaminDetailImpl.class, vitaminDetail);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(VitaminDetailImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		VitaminDetailModelImpl vitaminDetailModelImpl) {

		Object[] args = new Object[] {
			vitaminDetailModelImpl.getUuid(),
			vitaminDetailModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, vitaminDetailModelImpl, false);
	}

	/**
	 * Creates a new vitamin detail with the primary key. Does not add the vitamin detail to the database.
	 *
	 * @param vitaminDetailId the primary key for the new vitamin detail
	 * @return the new vitamin detail
	 */
	@Override
	public VitaminDetail create(long vitaminDetailId) {
		VitaminDetail vitaminDetail = new VitaminDetailImpl();

		vitaminDetail.setNew(true);
		vitaminDetail.setPrimaryKey(vitaminDetailId);

		String uuid = PortalUUIDUtil.generate();

		vitaminDetail.setUuid(uuid);

		vitaminDetail.setCompanyId(CompanyThreadLocal.getCompanyId());

		return vitaminDetail;
	}

	/**
	 * Removes the vitamin detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param vitaminDetailId the primary key of the vitamin detail
	 * @return the vitamin detail that was removed
	 * @throws NoSuchVitaminDetailException if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail remove(long vitaminDetailId)
		throws NoSuchVitaminDetailException {

		return remove((Serializable)vitaminDetailId);
	}

	/**
	 * Removes the vitamin detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the vitamin detail
	 * @return the vitamin detail that was removed
	 * @throws NoSuchVitaminDetailException if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail remove(Serializable primaryKey)
		throws NoSuchVitaminDetailException {

		Session session = null;

		try {
			session = openSession();

			VitaminDetail vitaminDetail = (VitaminDetail)session.get(
				VitaminDetailImpl.class, primaryKey);

			if (vitaminDetail == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVitaminDetailException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(vitaminDetail);
		}
		catch (NoSuchVitaminDetailException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected VitaminDetail removeImpl(VitaminDetail vitaminDetail) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(vitaminDetail)) {
				vitaminDetail = (VitaminDetail)session.get(
					VitaminDetailImpl.class, vitaminDetail.getPrimaryKeyObj());
			}

			if (vitaminDetail != null) {
				session.delete(vitaminDetail);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (vitaminDetail != null) {
			clearCache(vitaminDetail);
		}

		return vitaminDetail;
	}

	@Override
	public VitaminDetail updateImpl(VitaminDetail vitaminDetail) {
		boolean isNew = vitaminDetail.isNew();

		if (!(vitaminDetail instanceof VitaminDetailModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(vitaminDetail.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					vitaminDetail);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in vitaminDetail proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom VitaminDetail implementation " +
					vitaminDetail.getClass());
		}

		VitaminDetailModelImpl vitaminDetailModelImpl =
			(VitaminDetailModelImpl)vitaminDetail;

		if (Validator.isNull(vitaminDetail.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			vitaminDetail.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (vitaminDetail.getCreateDate() == null)) {
			if (serviceContext == null) {
				vitaminDetail.setCreateDate(date);
			}
			else {
				vitaminDetail.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!vitaminDetailModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				vitaminDetail.setModifiedDate(date);
			}
			else {
				vitaminDetail.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(vitaminDetail);
			}
			else {
				vitaminDetail = (VitaminDetail)session.merge(vitaminDetail);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			VitaminDetailImpl.class, vitaminDetailModelImpl, false, true);

		cacheUniqueFindersCache(vitaminDetailModelImpl);

		if (isNew) {
			vitaminDetail.setNew(false);
		}

		vitaminDetail.resetOriginalValues();

		return vitaminDetail;
	}

	/**
	 * Returns the vitamin detail with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the vitamin detail
	 * @return the vitamin detail
	 * @throws NoSuchVitaminDetailException if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVitaminDetailException {

		VitaminDetail vitaminDetail = fetchByPrimaryKey(primaryKey);

		if (vitaminDetail == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVitaminDetailException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return vitaminDetail;
	}

	/**
	 * Returns the vitamin detail with the primary key or throws a <code>NoSuchVitaminDetailException</code> if it could not be found.
	 *
	 * @param vitaminDetailId the primary key of the vitamin detail
	 * @return the vitamin detail
	 * @throws NoSuchVitaminDetailException if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail findByPrimaryKey(long vitaminDetailId)
		throws NoSuchVitaminDetailException {

		return findByPrimaryKey((Serializable)vitaminDetailId);
	}

	/**
	 * Returns the vitamin detail with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param vitaminDetailId the primary key of the vitamin detail
	 * @return the vitamin detail, or <code>null</code> if a vitamin detail with the primary key could not be found
	 */
	@Override
	public VitaminDetail fetchByPrimaryKey(long vitaminDetailId) {
		return fetchByPrimaryKey((Serializable)vitaminDetailId);
	}

	/**
	 * Returns all the vitamin details.
	 *
	 * @return the vitamin details
	 */
	@Override
	public List<VitaminDetail> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the vitamin details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @return the range of vitamin details
	 */
	@Override
	public List<VitaminDetail> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the vitamin details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of vitamin details
	 */
	@Override
	public List<VitaminDetail> findAll(
		int start, int end,
		OrderByComparator<VitaminDetail> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the vitamin details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VitaminDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vitamin details
	 * @param end the upper bound of the range of vitamin details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of vitamin details
	 */
	@Override
	public List<VitaminDetail> findAll(
		int start, int end, OrderByComparator<VitaminDetail> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<VitaminDetail> list = null;

		if (useFinderCache) {
			list = (List<VitaminDetail>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_VITAMINDETAIL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_VITAMINDETAIL;

				sql = sql.concat(VitaminDetailModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<VitaminDetail>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the vitamin details from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (VitaminDetail vitaminDetail : findAll()) {
			remove(vitaminDetail);
		}
	}

	/**
	 * Returns the number of vitamin details.
	 *
	 * @return the number of vitamin details
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_VITAMINDETAIL);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "vitaminDetailId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_VITAMINDETAIL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return VitaminDetailModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the vitamin detail persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new VitaminDetailModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", VitaminDetail.class.getName()));

		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByPersistedVitaminId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPersistedVitaminId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"persistedVitaminId"}, true);

		_finderPathWithoutPaginationFindByPersistedVitaminId =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByPersistedVitaminId", new String[] {Long.class.getName()},
				new String[] {"persistedVitaminId"}, true);

		_finderPathCountByPersistedVitaminId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPersistedVitaminId", new String[] {Long.class.getName()},
			new String[] {"persistedVitaminId"}, false);

		_finderPathWithPaginationFindBypersistedVitaminIdType =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findBypersistedVitaminIdType",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"persistedVitaminId", "type_"}, true);

		_finderPathWithoutPaginationFindBypersistedVitaminIdType =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findBypersistedVitaminIdType",
				new String[] {Long.class.getName(), Integer.class.getName()},
				new String[] {"persistedVitaminId", "type_"}, true);

		_finderPathCountBypersistedVitaminIdType = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBypersistedVitaminIdType",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"persistedVitaminId", "type_"}, false);

		_setVitaminDetailUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setVitaminDetailUtilPersistence(null);

		entityCache.removeCache(VitaminDetailImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private void _setVitaminDetailUtilPersistence(
		VitaminDetailPersistence vitaminDetailPersistence) {

		try {
			Field field = VitaminDetailUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, vitaminDetailPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = FOOPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FOOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FOOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_VITAMINDETAIL =
		"SELECT vitaminDetail FROM VitaminDetail vitaminDetail";

	private static final String _SQL_SELECT_VITAMINDETAIL_WHERE =
		"SELECT vitaminDetail FROM VitaminDetail vitaminDetail WHERE ";

	private static final String _SQL_COUNT_VITAMINDETAIL =
		"SELECT COUNT(vitaminDetail) FROM VitaminDetail vitaminDetail";

	private static final String _SQL_COUNT_VITAMINDETAIL_WHERE =
		"SELECT COUNT(vitaminDetail) FROM VitaminDetail vitaminDetail WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "vitaminDetail.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No VitaminDetail exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No VitaminDetail exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		VitaminDetailPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();
	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class VitaminDetailModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return new Object[0];
				}

				return null;
			}

			VitaminDetailModelImpl vitaminDetailModelImpl =
				(VitaminDetailModelImpl)baseModel;

			long columnBitmask = vitaminDetailModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(vitaminDetailModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						vitaminDetailModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(vitaminDetailModelImpl, columnNames, original);
			}

			return null;
		}

		private static Object[] _getValue(
			VitaminDetailModelImpl vitaminDetailModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						vitaminDetailModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = vitaminDetailModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}