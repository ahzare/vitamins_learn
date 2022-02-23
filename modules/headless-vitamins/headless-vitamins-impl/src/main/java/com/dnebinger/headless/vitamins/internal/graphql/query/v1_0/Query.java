package com.dnebinger.headless.vitamins.internal.graphql.query.v1_0;

import com.dnebinger.headless.vitamins.dto.v1_0.Vitamin;
import com.dnebinger.headless.vitamins.resource.v1_0.VitaminResource;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Dave Nebinger
 * @generated
 */
@Generated("")
public class Query {

	public static void setVitaminResourceComponentServiceObjects(
		ComponentServiceObjects<VitaminResource>
			vitaminResourceComponentServiceObjects) {

		_vitaminResourceComponentServiceObjects =
			vitaminResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {vitamins(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the list of vitamins and minerals. Results can be paginated, filtered, searched, and sorted."
	)
	public VitaminPage vitamins(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_vitaminResourceComponentServiceObjects,
			this::_populateResourceContext,
			vitaminResource -> new VitaminPage(
				vitaminResource.getVitaminsPage(
					search,
					_filterBiFunction.apply(vitaminResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(vitaminResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {vitamin(vitaminId: ___){name, id, chemicalNames, properties, group, description, articleId, type, attributes, risks, symptoms, creator}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the vitamin/mineral via its ID.")
	public Vitamin vitamin(@GraphQLName("vitaminId") String vitaminId)
		throws Exception {

		return _applyComponentServiceObjects(
			_vitaminResourceComponentServiceObjects,
			this::_populateResourceContext,
			vitaminResource -> vitaminResource.getVitamin(vitaminId));
	}

	@GraphQLName("VitaminPage")
	public class VitaminPage {

		public VitaminPage(Page vitaminPage) {
			actions = vitaminPage.getActions();

			items = vitaminPage.getItems();
			lastPage = vitaminPage.getLastPage();
			page = vitaminPage.getPage();
			pageSize = vitaminPage.getPageSize();
			totalCount = vitaminPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Vitamin> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(VitaminResource vitaminResource)
		throws Exception {

		vitaminResource.setContextAcceptLanguage(_acceptLanguage);
		vitaminResource.setContextCompany(_company);
		vitaminResource.setContextHttpServletRequest(_httpServletRequest);
		vitaminResource.setContextHttpServletResponse(_httpServletResponse);
		vitaminResource.setContextUriInfo(_uriInfo);
		vitaminResource.setContextUser(_user);
		vitaminResource.setGroupLocalService(_groupLocalService);
		vitaminResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<VitaminResource>
		_vitaminResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}