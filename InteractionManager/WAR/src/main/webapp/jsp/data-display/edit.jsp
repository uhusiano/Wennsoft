<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<div class="interaction-manager">
    <div id="overlay" class="edit-overlay ui-corner-all"></div>
    <form action="<portlet:actionURL/>" method="POST">
        <div id="accordion">
            <h3 class="im-preference-header" id="entityPreferenceHeader">EntityPreferences</h3>
            <div class="detail-content" id="entityPreferences" style="width: 100%;">
                <div class="im-detail-row">
                    <label class="im-detail-row-label" for="connect">Connect</label>
                    <select id="connect" name="connect">
                        <option val="customer-connect">Customer</option>
                    </select>
                </div>
                <div class="im-detail-row">
                    <label class="im-detail-row-label" for="entityCombobox">Entity</label>
                    <select id="entity" name="entity">
                        <c:forEach var="entityMetadata" items="${entitiesMetadata}">
                            <option data-metadata-controller="<c:out value='${entityMetadata["Controller"]}'/>"
                                    value="<c:out value='${entityMetadata["Entity"]["Name"]}'/>">
                                <c:out value="${entityMetadata['Entity']['Name']}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <h3 class="im-preference-header" id="displayPreferencesHeader">DisplayPreferences</h3>
            <div class="detail-content" id="displayPreferences" style="width: 100%;">
                <div class="detail-column" style="width: 100%;">
                    <div class="im-detail-row">
                        <label class="im-detail-row-label" for="displayCombobox">Display</label>
                        <select id="display" name="display">
                            <c:forEach var="availableDisplay" items="${availableDisplays}">
                                <c:if test="${availableDisplay.visible}">
                                    <option value="<c:out value='${availableDisplay.name}'/>">
                                        <c:out value="${availableDisplay.name}"/>
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="im-detail-row im-list-preference" style="display: none;">
                        <label class="im-detail-row-label" for="filterCombobox">Filter</label>
                        <select id="filter" name="filter"></select>
                    </div>
                    <div class="im-detail-row im-list-preference" style="display: none;">
                        <label class="im-detail-row-label" for="pageSize">PageSize</label>
                        <input id="pageSize" name="pageSize" value="<c:out value='${pageSize}'/>"/>
                    </div>
                    <div class="im-detail-row im-list-preference" style="display: none;">
                        <label class="im-detail-row-label" for="exportControl">ExportControl</label>
                        <span class="center">
                            <input <c:if test="${exportControl == 'true'}">
                                       checked="checked"
                                   </c:if>
                                   id="exportControl"
                                   name="exportControl"
                                   type="checkbox"/>
                        </span>
                    </div>
                    <div class="im-detail-row im-detail-preference" style="display: none;">
                        <label class="im-detail-row-label" for="columns">Columns</label>
                        <input id="columns" name="columns" value="<c:out value='${columns}'/>"/>
                    </div>
                </div>
            </div>
            <h3 class="im-preference-header" id="fieldPreferencesHeader">FieldPreferences</h3>
            <div id="fieldPreferences">
                <table class="display" id="availableFields">
                    <thead>
                        <tr>
                            <th>Field</th>
                            <th>Type</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
                <div class="dual-table-control" id="dualTableControl" style="float: left;">
                    <button class="dual-table-control-button" id="dualTableControlButton" type="button"></button>
                </div>
                <table class="display" id="visibleFields">
                    <thead>
                        <tr>
                            <th></th>
                            <th></th>
                            <th>Field</th>
                            <th>Type</th>
                            <th id="displayControl"></th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
        <input id="fields" name="fields" type="hidden">
        <input id="save" name="save" type="hidden" value="Edit">
    </form>
</div>

<script type="text/javascript">
    $(function()
    {
        defineCustomerDataDisplayEdit("customerDataDisplayEdit");
        customerDataDisplayEdit.initialize(
        {
            Columns: "${columns}",
            Display: "${display}",
            Entity: "${entity}",
            ExoLocale: "${exoLocale}",
            ExportControl: "${exportControl}",
            Fields: "${fields}",
            Filter: "${filter}",
            PageSize: "${pageSize}",
            RequestContextPath: "${requestContextPath}",
            WindowId: "${windowId}",
        });
    });
</script>