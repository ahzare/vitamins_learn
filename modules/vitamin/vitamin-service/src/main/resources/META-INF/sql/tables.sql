create table FOO_PersistedVitamin (
	uuid_ VARCHAR(75) null,
	persistedVitaminId LONG not null primary key,
	surrogateId VARCHAR(75) null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	groupName VARCHAR(75) null,
	articleId VARCHAR(75) null,
	description VARCHAR(75) null,
	name VARCHAR(75) null,
	type_ INTEGER
);

create table FOO_VitaminDetail (
	uuid_ VARCHAR(75) null,
	vitaminDetailId LONG not null primary key,
	persistedVitaminId LONG,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	type_ INTEGER,
	value VARCHAR(75) null
);