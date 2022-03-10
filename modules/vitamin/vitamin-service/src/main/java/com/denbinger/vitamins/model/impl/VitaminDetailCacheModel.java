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

package com.denbinger.vitamins.model.impl;

import com.denbinger.vitamins.model.VitaminDetail;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing VitaminDetail in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class VitaminDetailCacheModel
	implements CacheModel<VitaminDetail>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof VitaminDetailCacheModel)) {
			return false;
		}

		VitaminDetailCacheModel vitaminDetailCacheModel =
			(VitaminDetailCacheModel)object;

		if (vitaminDetailId == vitaminDetailCacheModel.vitaminDetailId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, vitaminDetailId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", vitaminDetailId=");
		sb.append(vitaminDetailId);
		sb.append(", persistedVitaminId=");
		sb.append(persistedVitaminId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", type=");
		sb.append(type);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public VitaminDetail toEntityModel() {
		VitaminDetailImpl vitaminDetailImpl = new VitaminDetailImpl();

		if (uuid == null) {
			vitaminDetailImpl.setUuid("");
		}
		else {
			vitaminDetailImpl.setUuid(uuid);
		}

		vitaminDetailImpl.setVitaminDetailId(vitaminDetailId);
		vitaminDetailImpl.setPersistedVitaminId(persistedVitaminId);
		vitaminDetailImpl.setGroupId(groupId);
		vitaminDetailImpl.setCompanyId(companyId);
		vitaminDetailImpl.setUserId(userId);

		if (userName == null) {
			vitaminDetailImpl.setUserName("");
		}
		else {
			vitaminDetailImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			vitaminDetailImpl.setCreateDate(null);
		}
		else {
			vitaminDetailImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			vitaminDetailImpl.setModifiedDate(null);
		}
		else {
			vitaminDetailImpl.setModifiedDate(new Date(modifiedDate));
		}

		vitaminDetailImpl.setType(type);

		if (value == null) {
			vitaminDetailImpl.setValue("");
		}
		else {
			vitaminDetailImpl.setValue(value);
		}

		vitaminDetailImpl.resetOriginalValues();

		return vitaminDetailImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		vitaminDetailId = objectInput.readLong();

		persistedVitaminId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		type = objectInput.readInt();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(vitaminDetailId);

		objectOutput.writeLong(persistedVitaminId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeInt(type);

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public String uuid;
	public long vitaminDetailId;
	public long persistedVitaminId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int type;
	public String value;

}