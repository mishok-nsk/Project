<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "backURL");
long farmerId = ParamUtil.getLong(request, "farmerId");
Farmer selecteFarmer = null;
String title = "Add farmer";
Date date = new Date();
List<District> farmerFields = DistrictLocalServiceUtil.getFarmerDistricts(farmerId);
if (farmerId > 0) {
    selecteFarmer = FarmerLocalServiceUtil.getFarmer(farmerId);
    date = selecteFarmer.getRegistrationDate();
}
SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
String dateString = df.format(date);

%>
<a href="<%=redirect %>">Back</a><br/><br/>

<portlet:actionURL var="editFarmerActionURL" windowState="normal" name="editFarmer" />
<h2><%=title %></h2>
<aui:form action="<%=editFarmerActionURL %>" name="<portlet:namespace />form">
    <aui:model-context bean="<%=selecteFarmer %>" model="<%=Farmer.class %>" />
    <aui:fieldset>
        <aui:input name="name" label="Наименование фермера">
            <aui:validator name="required" errorMessage="Please enter farmer name." />
        </aui:input>
        <aui:input name="legalForm" label="Орг./правовая форма"/>
		<aui:input name="inn" label="ИНН">
			<aui:validator name="required" errorMessage="Please enter farmer name." />
		</aui:input>
		<aui:input name="kpp" label="КПП"/>
		<aui:input name="ogrn" label="ОГРН"/>
		<aui:select name="districtId" label="Район регистрации">
            <%
            List<District> districtList=DistrictLocalServiceUtil.getDistricts();
            for(District district:districtList){%>
            <aui:option value="<%=district.getDistrictId()%>" label="<%=district.getName()%>"></aui:option>
            <%}%>
        </aui:select>

        <aui:select name="districtField" label="Районы посевных полей" multiple="true" size="10">
            <%
            List<District> districtList=DistrictLocalServiceUtil.getDistricts();
            for(District district:districtList){%>
            <aui:option value="<%=district.getDistrictId()%>" label="<%=district.getName()%>" selected='<%=farmerFields.contains(district) %>' ></aui:option>
            <%}%>
        </aui:select>

        <label class="aui-field-label">Дата регистрации</label>
        <input name="<portlet:namespace/>registrationDate" class="form-control date" type="text" placeholder="dd-mm-yyyy" value="<%= dateString %>">
        <aui:script>
        AUI().use('aui-datepicker', function(A) {
            new A.DatePicker({
                trigger: '.date',
                mask: '%d-%m-%Y',
                popover: {
                  zIndex: 1000
                }
            });
        });
        </aui:script>

        <aui:input name="farmerId" type="hidden" />
    </aui:fieldset>
    <aui:button-row>

            <aui:button type="submit"></aui:button>

    </aui:button-row>
</aui:form>