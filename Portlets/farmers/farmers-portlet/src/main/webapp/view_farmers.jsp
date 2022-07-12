<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/init.jsp" %>

<liferay-ui:success key="farmer-update-success" message="Farmer have been updated successfylly" />
<liferay-ui:success key="farmer-delete-success" message="Farmer have been deleted successfylly" />
<liferay-ui:error key="farmer-update-error" message="There is an Error in update Farmer" />
<liferay-ui:error key="farmer-delete-error" message="There is an Erron in delete Farmer" />


<%
String currentURL = PortalUtil.getCurrentURL(request);

SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

%>

<h1>Reestr of farmers</h1>

<portlet:renderURL var="homeURL"></portlet:renderURL>
<a href="<%=homeURL.toString() %>">Home</a><br/><br/>
<liferay-portlet:renderURL varImpl="iteratorURL">
<portlet:param name="mvcPath" value="/view_farmers.jsp" />
</liferay-portlet:renderURL>

<portlet:renderURL var="editFarmerURL">
    <portlet:param name="mvcPath" value="/edit_farmer.jsp"/>
    <portlet:param name="backURL" value="<%=currentURL %>"/>
</portlet:renderURL>

<a href="<%=editFarmerURL.toString()%>">Add Farmer</a><br/>
<div class="separator"></div>

<liferay-ui:search-container emptyResultsMessage="there-are-no-farmers"
total="<%=FarmerLocalServiceUtil.getFarmersCount()%>"
iteratorURL="<%=iteratorURL %>"
headerNames="Наименование, Орг.-правовая форма, ИНН, КПП, ОГРН, Район регистрации, Дата регистрации, Статус"
delta="10"
deltaConfigurable="true"
>
    <liferay-ui:search-container-results
    results="<%=FarmerLocalServiceUtil.getFarmers(searchContainer.getStart(), searchContainer.getEnd())%>"
    />

    <liferay-ui:search-container-row className="com.test.farmers.model.Farmer"
    keyProperty="farmerId" modelVar="currentFarmer">
        <liferay-ui:search-container-column-text name="Наименование фермера" property="name" />
        <liferay-ui:search-container-column-text name="Орг./правовая форма" property="legalForm" />
		<liferay-ui:search-container-column-text name="ИНН" property="inn" />
		<liferay-ui:search-container-column-text name="КПП" property="kpp" />
		<liferay-ui:search-container-column-text name="Район регистрации"
		    value='<%=DistrictLocalServiceUtil.getDistrict(currentFarmer.getDistrictId()).getName() %>' />
		<liferay-ui:search-container-column-text name="Дата регистрации"
		    value='<%=formatter.format(currentFarmer.getRegistrationDate()) %>' />
        <liferay-ui:search-container-column-text name="Статус"
            value='<%=currentFarmer.getIsArchive()==true?"Архивный":"Актуальный" %>' />
		<liferay-ui:search-container-column-jsp path="/farmer_actions.jsp" align="right" />
    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator />
</liferay-ui:search-container>

