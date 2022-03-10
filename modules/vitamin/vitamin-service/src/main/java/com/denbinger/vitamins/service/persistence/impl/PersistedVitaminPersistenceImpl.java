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

import com.denbinger.vitamins.exception.NoSuchPersistedVitaminException;
import com.denbinger.vitamins.model.PersistedVitamin;
import com.denbinger.vitamins.model.impl.PersistedVitaminImpl;
import com.denbinger.vitamins.model.impl.PersistedVitaminModelImpl;
import com.denbinger.vitamins.service.persistence.PersistedVitaminPersistence;
import com.denbinger.vitamins.service.persistence.PersistedVitaminUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the persisted vitamin service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = PersistedVitaminPersistence.class)
public class PersistedVitaminPersistenceImpl
	extends BasePersistenceImpl<PersistedVitamin>
	implements PersistedVitaminPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PersistedVitaminUtil</code> to access the persisted vitamin persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PersistedVitaminImpl.class.getName();

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
	 * Returns all the persisted vitamins where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the persisted vitamins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @return the range of matching persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the persisted vitamins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<PersistedVitamin> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the persisted vitamins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<PersistedVitamin> orderByComparator,
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

		List<PersistedVitamin> list = null;

		if (useFinderCache) {
			list = (List<PersistedVitamin>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PersistedVitamin persistedVitamin : list) {
					if (!uuid.equals(persistedVitamin.getUuid())) {
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

			sb.append(_SQL_SELECT_PERSISTEDVITAMIN_WHERE);

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
				sb.append(PersistedVitaminModelImpl.ORDER_BY_JPQL);
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

				list = (List<PersistedVitamin>)QueryUtil.list(
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
	 * Returns the first persisted vitamin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin findByUuid_First(
			String uuid, OrderByComparator<PersistedVitamin> orderByComparator)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = fetchByUuid_First(
			uuid, orderByComparator);

		if (persistedVitamin != null) {
			return persistedVitamin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPersistedVitaminException(sb.toString());
	}

	/**
	 * Returns the first persisted vitamin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchByUuid_First(
		String uuid, OrderByComparator<PersistedVitamin> orderByComparator) {

		List<PersistedVitamin> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last persisted vitamin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin findByUuid_Last(
			String uuid, OrderByComparator<PersistedVitamin> orderByComparator)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = fetchByUuid_Last(
			uuid, orderByComparator);

		if (persistedVitamin != null) {
			return persistedVitamin;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPersistedVitaminException(sb.toString());
	}

	/**
	 * Returns the last persisted vitamin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchByUuid_Last(
		String uuid, OrderByComparator<PersistedVitamin> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<PersistedVitamin> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the persisted vitamins before and after the current persisted vitamin in the ordered set where uuid = &#63;.
	 *
	 * @param persistedVitaminId the primary key of the current persisted vitamin
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a persisted vitamin with the primary key could not be found
	 */
	@Override
	public PersistedVitamin[] findByUuid_PrevAndNext(
			long persistedVitaminId, String uuid,
			OrderByComparator<PersistedVitamin> orderByComparator)
		throws NoSuchPersistedVitaminException {

		uuid = Objects.toString(uuid, "");

		PersistedVitamin persistedVitamin = findByPrimaryKey(
			persistedVitaminId);

		Session session = null;

		try {
			session = openSession();

			PersistedVitamin[] array = new PersistedVitaminImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, persistedVitamin, uuid, orderByComparator, true);

			array[1] = persistedVitamin;

			array[2] = getByUuid_PrevAndNext(
				session, persistedVitamin, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PersistedVitamin getByUuid_PrevAndNext(
		Session session, PersistedVitamin persistedVitamin, String uuid,
		OrderByComparator<PersistedVitamin> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_PERSISTEDVITAMIN_WHERE);

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
			sb.append(PersistedVitaminModelImpl.ORDER_BY_JPQL);
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
						persistedVitamin)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PersistedVitamin> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the persisted vitamins where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (PersistedVitamin persistedVitamin :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(persistedVitamin);
		}
	}

	/**
	 * Returns the number of persisted vitamins where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching persisted vitamins
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PERSISTEDVITAMIN_WHERE);

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
		"persistedVitamin.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(persistedVitamin.uuid IS NULL OR persistedVitamin.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the persisted vitamin where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPersistedVitaminException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin findByUUID_G(String uuid, long groupId)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = fetchByUUID_G(uuid, groupId);

		if (persistedVitamin == null) {
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

			throw new NoSuchPersistedVitaminException(sb.toString());
		}

		return persistedVitamin;
	}

	/**
	 * Returns the persisted vitamin where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the persisted vitamin where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchByUUID_G(
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

		if (result instanceof PersistedVitamin) {
			PersistedVitamin persistedVitamin = (PersistedVitamin)result;

			if (!Objects.equals(uuid, persistedVitamin.getUuid()) ||
				(groupId != persistedVitamin.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_PERSISTEDVITAMIN_WHERE);

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

				List<PersistedVitamin> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					PersistedVitamin persistedVitamin = list.get(0);

					result = persistedVitamin;

					cacheResult(persistedVitamin);
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
			return (PersistedVitamin)result;
		}
	}

	/**
	 * Removes the persisted vitamin where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the persisted vitamin that was removed
	 */
	@Override
	public PersistedVitamin removeByUUID_G(String uuid, long groupId)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = findByUUID_G(uuid, groupId);

		return remove(persistedVitamin);
	}

	/**
	 * Returns the number of persisted vitamins where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching persisted vitamins
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PERSISTEDVITAMIN_WHERE);

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
		"persistedVitamin.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(persistedVitamin.uuid IS NULL OR persistedVitamin.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"persistedVitamin.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the persisted vitamins where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the persisted vitamins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @return the range of matching persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the persisted vitamins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PersistedVitamin> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the persisted vitamins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PersistedVitamin> orderByComparator,
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

		List<PersistedVitamin> list = null;

		if (useFinderCache) {
			list = (List<PersistedVitamin>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PersistedVitamin persistedVitamin : list) {
					if (!uuid.equals(persistedVitamin.getUuid()) ||
						(companyId != persistedVitamin.getCompanyId())) {

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

			sb.append(_SQL_SELECT_PERSISTEDVITAMIN_WHERE);

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
				sb.append(PersistedVitaminModelImpl.ORDER_BY_JPQL);
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

				list = (List<PersistedVitamin>)QueryUtil.list(
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
	 * Returns the first persisted vitamin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<PersistedVitamin> orderByComparator)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (persistedVitamin != null) {
			return persistedVitamin;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPersistedVitaminException(sb.toString());
	}

	/**
	 * Returns the first persisted vitamin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<PersistedVitamin> orderByComparator) {

		List<PersistedVitamin> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last persisted vitamin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<PersistedVitamin> orderByComparator)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (persistedVitamin != null) {
			return persistedVitamin;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPersistedVitaminException(sb.toString());
	}

	/**
	 * Returns the last persisted vitamin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<PersistedVitamin> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<PersistedVitamin> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the persisted vitamins before and after the current persisted vitamin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param persistedVitaminId the primary key of the current persisted vitamin
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a persisted vitamin with the primary key could not be found
	 */
	@Override
	public PersistedVitamin[] findByUuid_C_PrevAndNext(
			long persistedVitaminId, String uuid, long companyId,
			OrderByComparator<PersistedVitamin> orderByComparator)
		throws NoSuchPersistedVitaminException {

		uuid = Objects.toString(uuid, "");

		PersistedVitamin persistedVitamin = findByPrimaryKey(
			persistedVitaminId);

		Session session = null;

		try {
			session = openSession();

			PersistedVitamin[] array = new PersistedVitaminImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, persistedVitamin, uuid, companyId, orderByComparator,
				true);

			array[1] = persistedVitamin;

			array[2] = getByUuid_C_PrevAndNext(
				session, persistedVitamin, uuid, companyId, orderByComparator,
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

	protected PersistedVitamin getByUuid_C_PrevAndNext(
		Session session, PersistedVitamin persistedVitamin, String uuid,
		long companyId, OrderByComparator<PersistedVitamin> orderByComparator,
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

		sb.append(_SQL_SELECT_PERSISTEDVITAMIN_WHERE);

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
			sb.append(PersistedVitaminModelImpl.ORDER_BY_JPQL);
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
						persistedVitamin)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PersistedVitamin> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the persisted vitamins where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (PersistedVitamin persistedVitamin :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(persistedVitamin);
		}
	}

	/**
	 * Returns the number of persisted vitamins where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching persisted vitamins
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PERSISTEDVITAMIN_WHERE);

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
		"persistedVitamin.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(persistedVitamin.uuid IS NULL OR persistedVitamin.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"persistedVitamin.companyId = ?";

	private FinderPath _finderPathFetchBySurrogateId;
	private FinderPath _finderPathCountBySurrogateId;

	/**
	 * Returns the persisted vitamin where surrogateId = &#63; or throws a <code>NoSuchPersistedVitaminException</code> if it could not be found.
	 *
	 * @param surrogateId the surrogate ID
	 * @return the matching persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin findBySurrogateId(String surrogateId)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = fetchBySurrogateId(surrogateId);

		if (persistedVitamin == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("surrogateId=");
			sb.append(surrogateId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPersistedVitaminException(sb.toString());
		}

		return persistedVitamin;
	}

	/**
	 * Returns the persisted vitamin where surrogateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param surrogateId the surrogate ID
	 * @return the matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchBySurrogateId(String surrogateId) {
		return fetchBySurrogateId(surrogateId, true);
	}

	/**
	 * Returns the persisted vitamin where surrogateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param surrogateId the surrogate ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchBySurrogateId(
		String surrogateId, boolean useFinderCache) {

		surrogateId = Objects.toString(surrogateId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {surrogateId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchBySurrogateId, finderArgs, this);
		}

		if (result instanceof PersistedVitamin) {
			PersistedVitamin persistedVitamin = (PersistedVitamin)result;

			if (!Objects.equals(
					surrogateId, persistedVitamin.getSurrogateId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_PERSISTEDVITAMIN_WHERE);

			boolean bindSurrogateId = false;

			if (surrogateId.isEmpty()) {
				sb.append(_FINDER_COLUMN_SURROGATEID_SURROGATEID_3);
			}
			else {
				bindSurrogateId = true;

				sb.append(_FINDER_COLUMN_SURROGATEID_SURROGATEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSurrogateId) {
					queryPos.add(surrogateId);
				}

				List<PersistedVitamin> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchBySurrogateId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {surrogateId};
							}

							_log.warn(
								"PersistedVitaminPersistenceImpl.fetchBySurrogateId(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					PersistedVitamin persistedVitamin = list.get(0);

					result = persistedVitamin;

					cacheResult(persistedVitamin);
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
			return (PersistedVitamin)result;
		}
	}

	/**
	 * Removes the persisted vitamin where surrogateId = &#63; from the database.
	 *
	 * @param surrogateId the surrogate ID
	 * @return the persisted vitamin that was removed
	 */
	@Override
	public PersistedVitamin removeBySurrogateId(String surrogateId)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = findBySurrogateId(surrogateId);

		return remove(persistedVitamin);
	}

	/**
	 * Returns the number of persisted vitamins where surrogateId = &#63;.
	 *
	 * @param surrogateId the surrogate ID
	 * @return the number of matching persisted vitamins
	 */
	@Override
	public int countBySurrogateId(String surrogateId) {
		surrogateId = Objects.toString(surrogateId, "");

		FinderPath finderPath = _finderPathCountBySurrogateId;

		Object[] finderArgs = new Object[] {surrogateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PERSISTEDVITAMIN_WHERE);

			boolean bindSurrogateId = false;

			if (surrogateId.isEmpty()) {
				sb.append(_FINDER_COLUMN_SURROGATEID_SURROGATEID_3);
			}
			else {
				bindSurrogateId = true;

				sb.append(_FINDER_COLUMN_SURROGATEID_SURROGATEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSurrogateId) {
					queryPos.add(surrogateId);
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

	private static final String _FINDER_COLUMN_SURROGATEID_SURROGATEID_2 =
		"persistedVitamin.surrogateId = ?";

	private static final String _FINDER_COLUMN_SURROGATEID_SURROGATEID_3 =
		"(persistedVitamin.surrogateId IS NULL OR persistedVitamin.surrogateId = '')";

	private FinderPath _finderPathFetchByName;
	private FinderPath _finderPathCountByName;

	/**
	 * Returns the persisted vitamin where name = &#63; or throws a <code>NoSuchPersistedVitaminException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin findByName(String name)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = fetchByName(name);

		if (persistedVitamin == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPersistedVitaminException(sb.toString());
		}

		return persistedVitamin;
	}

	/**
	 * Returns the persisted vitamin where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the persisted vitamin where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching persisted vitamin, or <code>null</code> if a matching persisted vitamin could not be found
	 */
	@Override
	public PersistedVitamin fetchByName(String name, boolean useFinderCache) {
		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByName, finderArgs, this);
		}

		if (result instanceof PersistedVitamin) {
			PersistedVitamin persistedVitamin = (PersistedVitamin)result;

			if (!Objects.equals(name, persistedVitamin.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_PERSISTEDVITAMIN_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				List<PersistedVitamin> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByName, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {name};
							}

							_log.warn(
								"PersistedVitaminPersistenceImpl.fetchByName(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					PersistedVitamin persistedVitamin = list.get(0);

					result = persistedVitamin;

					cacheResult(persistedVitamin);
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
			return (PersistedVitamin)result;
		}
	}

	/**
	 * Removes the persisted vitamin where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the persisted vitamin that was removed
	 */
	@Override
	public PersistedVitamin removeByName(String name)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = findByName(name);

		return remove(persistedVitamin);
	}

	/**
	 * Returns the number of persisted vitamins where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching persisted vitamins
	 */
	@Override
	public int countByName(String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByName;

		Object[] finderArgs = new Object[] {name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PERSISTEDVITAMIN_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"persistedVitamin.name = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(persistedVitamin.name IS NULL OR persistedVitamin.name = '')";

	public PersistedVitaminPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PersistedVitamin.class);

		setModelImplClass(PersistedVitaminImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the persisted vitamin in the entity cache if it is enabled.
	 *
	 * @param persistedVitamin the persisted vitamin
	 */
	@Override
	public void cacheResult(PersistedVitamin persistedVitamin) {
		entityCache.putResult(
			PersistedVitaminImpl.class, persistedVitamin.getPrimaryKey(),
			persistedVitamin);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				persistedVitamin.getUuid(), persistedVitamin.getGroupId()
			},
			persistedVitamin);

		finderCache.putResult(
			_finderPathFetchBySurrogateId,
			new Object[] {persistedVitamin.getSurrogateId()}, persistedVitamin);

		finderCache.putResult(
			_finderPathFetchByName, new Object[] {persistedVitamin.getName()},
			persistedVitamin);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the persisted vitamins in the entity cache if it is enabled.
	 *
	 * @param persistedVitamins the persisted vitamins
	 */
	@Override
	public void cacheResult(List<PersistedVitamin> persistedVitamins) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (persistedVitamins.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PersistedVitamin persistedVitamin : persistedVitamins) {
			if (entityCache.getResult(
					PersistedVitaminImpl.class,
					persistedVitamin.getPrimaryKey()) == null) {

				cacheResult(persistedVitamin);
			}
		}
	}

	/**
	 * Clears the cache for all persisted vitamins.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PersistedVitaminImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the persisted vitamin.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PersistedVitamin persistedVitamin) {
		entityCache.removeResult(PersistedVitaminImpl.class, persistedVitamin);
	}

	@Override
	public void clearCache(List<PersistedVitamin> persistedVitamins) {
		for (PersistedVitamin persistedVitamin : persistedVitamins) {
			entityCache.removeResult(
				PersistedVitaminImpl.class, persistedVitamin);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(PersistedVitaminImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PersistedVitaminModelImpl persistedVitaminModelImpl) {

		Object[] args = new Object[] {
			persistedVitaminModelImpl.getUuid(),
			persistedVitaminModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, persistedVitaminModelImpl, false);

		args = new Object[] {persistedVitaminModelImpl.getSurrogateId()};

		finderCache.putResult(
			_finderPathCountBySurrogateId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchBySurrogateId, args, persistedVitaminModelImpl,
			false);

		args = new Object[] {persistedVitaminModelImpl.getName()};

		finderCache.putResult(
			_finderPathCountByName, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByName, args, persistedVitaminModelImpl, false);
	}

	/**
	 * Creates a new persisted vitamin with the primary key. Does not add the persisted vitamin to the database.
	 *
	 * @param persistedVitaminId the primary key for the new persisted vitamin
	 * @return the new persisted vitamin
	 */
	@Override
	public PersistedVitamin create(long persistedVitaminId) {
		PersistedVitamin persistedVitamin = new PersistedVitaminImpl();

		persistedVitamin.setNew(true);
		persistedVitamin.setPrimaryKey(persistedVitaminId);

		String uuid = PortalUUIDUtil.generate();

		persistedVitamin.setUuid(uuid);

		persistedVitamin.setCompanyId(CompanyThreadLocal.getCompanyId());

		return persistedVitamin;
	}

	/**
	 * Removes the persisted vitamin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param persistedVitaminId the primary key of the persisted vitamin
	 * @return the persisted vitamin that was removed
	 * @throws NoSuchPersistedVitaminException if a persisted vitamin with the primary key could not be found
	 */
	@Override
	public PersistedVitamin remove(long persistedVitaminId)
		throws NoSuchPersistedVitaminException {

		return remove((Serializable)persistedVitaminId);
	}

	/**
	 * Removes the persisted vitamin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the persisted vitamin
	 * @return the persisted vitamin that was removed
	 * @throws NoSuchPersistedVitaminException if a persisted vitamin with the primary key could not be found
	 */
	@Override
	public PersistedVitamin remove(Serializable primaryKey)
		throws NoSuchPersistedVitaminException {

		Session session = null;

		try {
			session = openSession();

			PersistedVitamin persistedVitamin = (PersistedVitamin)session.get(
				PersistedVitaminImpl.class, primaryKey);

			if (persistedVitamin == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPersistedVitaminException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(persistedVitamin);
		}
		catch (NoSuchPersistedVitaminException noSuchEntityException) {
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
	protected PersistedVitamin removeImpl(PersistedVitamin persistedVitamin) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(persistedVitamin)) {
				persistedVitamin = (PersistedVitamin)session.get(
					PersistedVitaminImpl.class,
					persistedVitamin.getPrimaryKeyObj());
			}

			if (persistedVitamin != null) {
				session.delete(persistedVitamin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (persistedVitamin != null) {
			clearCache(persistedVitamin);
		}

		return persistedVitamin;
	}

	@Override
	public PersistedVitamin updateImpl(PersistedVitamin persistedVitamin) {
		boolean isNew = persistedVitamin.isNew();

		if (!(persistedVitamin instanceof PersistedVitaminModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(persistedVitamin.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					persistedVitamin);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in persistedVitamin proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PersistedVitamin implementation " +
					persistedVitamin.getClass());
		}

		PersistedVitaminModelImpl persistedVitaminModelImpl =
			(PersistedVitaminModelImpl)persistedVitamin;

		if (Validator.isNull(persistedVitamin.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			persistedVitamin.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (persistedVitamin.getCreateDate() == null)) {
			if (serviceContext == null) {
				persistedVitamin.setCreateDate(date);
			}
			else {
				persistedVitamin.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!persistedVitaminModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				persistedVitamin.setModifiedDate(date);
			}
			else {
				persistedVitamin.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(persistedVitamin);
			}
			else {
				persistedVitamin = (PersistedVitamin)session.merge(
					persistedVitamin);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			PersistedVitaminImpl.class, persistedVitaminModelImpl, false, true);

		cacheUniqueFindersCache(persistedVitaminModelImpl);

		if (isNew) {
			persistedVitamin.setNew(false);
		}

		persistedVitamin.resetOriginalValues();

		return persistedVitamin;
	}

	/**
	 * Returns the persisted vitamin with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the persisted vitamin
	 * @return the persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a persisted vitamin with the primary key could not be found
	 */
	@Override
	public PersistedVitamin findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPersistedVitaminException {

		PersistedVitamin persistedVitamin = fetchByPrimaryKey(primaryKey);

		if (persistedVitamin == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPersistedVitaminException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return persistedVitamin;
	}

	/**
	 * Returns the persisted vitamin with the primary key or throws a <code>NoSuchPersistedVitaminException</code> if it could not be found.
	 *
	 * @param persistedVitaminId the primary key of the persisted vitamin
	 * @return the persisted vitamin
	 * @throws NoSuchPersistedVitaminException if a persisted vitamin with the primary key could not be found
	 */
	@Override
	public PersistedVitamin findByPrimaryKey(long persistedVitaminId)
		throws NoSuchPersistedVitaminException {

		return findByPrimaryKey((Serializable)persistedVitaminId);
	}

	/**
	 * Returns the persisted vitamin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param persistedVitaminId the primary key of the persisted vitamin
	 * @return the persisted vitamin, or <code>null</code> if a persisted vitamin with the primary key could not be found
	 */
	@Override
	public PersistedVitamin fetchByPrimaryKey(long persistedVitaminId) {
		return fetchByPrimaryKey((Serializable)persistedVitaminId);
	}

	/**
	 * Returns all the persisted vitamins.
	 *
	 * @return the persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the persisted vitamins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @return the range of persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the persisted vitamins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findAll(
		int start, int end,
		OrderByComparator<PersistedVitamin> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the persisted vitamins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PersistedVitaminModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of persisted vitamins
	 * @param end the upper bound of the range of persisted vitamins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of persisted vitamins
	 */
	@Override
	public List<PersistedVitamin> findAll(
		int start, int end,
		OrderByComparator<PersistedVitamin> orderByComparator,
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

		List<PersistedVitamin> list = null;

		if (useFinderCache) {
			list = (List<PersistedVitamin>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PERSISTEDVITAMIN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PERSISTEDVITAMIN;

				sql = sql.concat(PersistedVitaminModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PersistedVitamin>)QueryUtil.list(
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
	 * Removes all the persisted vitamins from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PersistedVitamin persistedVitamin : findAll()) {
			remove(persistedVitamin);
		}
	}

	/**
	 * Returns the number of persisted vitamins.
	 *
	 * @return the number of persisted vitamins
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_PERSISTEDVITAMIN);

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
		return "persistedVitaminId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PERSISTEDVITAMIN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PersistedVitaminModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the persisted vitamin persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new PersistedVitaminModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", PersistedVitamin.class.getName()));

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

		_finderPathFetchBySurrogateId = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchBySurrogateId",
			new String[] {String.class.getName()}, new String[] {"surrogateId"},
			true);

		_finderPathCountBySurrogateId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySurrogateId",
			new String[] {String.class.getName()}, new String[] {"surrogateId"},
			false);

		_finderPathFetchByName = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByName",
			new String[] {String.class.getName()}, new String[] {"name"}, true);

		_finderPathCountByName = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] {String.class.getName()}, new String[] {"name"},
			false);

		_setPersistedVitaminUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setPersistedVitaminUtilPersistence(null);

		entityCache.removeCache(PersistedVitaminImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private void _setPersistedVitaminUtilPersistence(
		PersistedVitaminPersistence persistedVitaminPersistence) {

		try {
			Field field = PersistedVitaminUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, persistedVitaminPersistence);
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

	private static final String _SQL_SELECT_PERSISTEDVITAMIN =
		"SELECT persistedVitamin FROM PersistedVitamin persistedVitamin";

	private static final String _SQL_SELECT_PERSISTEDVITAMIN_WHERE =
		"SELECT persistedVitamin FROM PersistedVitamin persistedVitamin WHERE ";

	private static final String _SQL_COUNT_PERSISTEDVITAMIN =
		"SELECT COUNT(persistedVitamin) FROM PersistedVitamin persistedVitamin";

	private static final String _SQL_COUNT_PERSISTEDVITAMIN_WHERE =
		"SELECT COUNT(persistedVitamin) FROM PersistedVitamin persistedVitamin WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "persistedVitamin.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PersistedVitamin exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PersistedVitamin exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PersistedVitaminPersistenceImpl.class);

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

	private static class PersistedVitaminModelArgumentsResolver
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

			PersistedVitaminModelImpl persistedVitaminModelImpl =
				(PersistedVitaminModelImpl)baseModel;

			long columnBitmask = persistedVitaminModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					persistedVitaminModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						persistedVitaminModelImpl.getColumnBitmask(columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(PersistedVitaminPersistenceImpl.
						FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
							finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					persistedVitaminModelImpl, columnNames, original);
			}

			return null;
		}

		private static Object[] _getValue(
			PersistedVitaminModelImpl persistedVitaminModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						persistedVitaminModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = persistedVitaminModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |= PersistedVitaminModelImpl.getColumnBitmask(
				"name");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}