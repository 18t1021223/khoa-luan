<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark p-0">
  <div class="container">
    <a href="<c:url value='/'/>" class="navbar-brand">Web page</a>

    <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
      <ul class="navbar-nav">
        <li class="nav-item px-2">
          <a href="<c:url value='/admin/home'/>"
             class="nav-link <c:if test="${requestPath.equals('/admin/home')}">active</c:if>">Dashboard</a>
        </li>
        <li class="nav-item px-2">
          <a href="<c:url value='/admin/posts'/>"
             class="nav-link <c:if test="${requestPath.equals('/admin/posts')}">active</c:if>">Posts</a>
        </li>
        <li class="nav-item px-2">
          <a href="<c:url value='/admin/categories'/>"
             class="nav-link <c:if test="${requestPath.equals('/admin/categories')}">active</c:if>">Categories</a>
        </li>
        <li class="nav-item px-2">
          <a href="<c:url value='/admin/hashtags'/>"
             class="nav-link <c:if test="${requestPath.equals('/admin/hashtags')}">active</c:if>">Tags</a>
        </li>
        <sec:authorize access="hasAnyAuthority('ADMIN')">
          <li class="nav-item px-2">
            <a href="<c:url value='/admin/hosts'/>"
               class="nav-link <c:if test="${requestPath.equals('/admin/hosts')}">active</c:if>">Hosts</a>
          </li>
        </sec:authorize>
      </ul>

      <c:if test="${!requestPath.equals('/admin/register') && !requestPath.equals('/admin/forgot-password') && !requestPath.contains('/admin/new-password')}">

        <div class="dropdown navbar-nav ml-auto">
          <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
            Tùy chọn
          </button>
          <div class="dropdown-menu">
            <a class="dropdown-item" data-toggle="modal" data-target="#update-host">Information
            </a>
            <a class="dropdown-item" href="<c:url value='/admin/change-password'/>">Change password</a>
            <a href="<c:url value='/logout'/>" class="dropdown-item">
              Logout
            </a>
          </div>
        </div>
        <c:import url="/WEB-INF/jsp/admin/base/_hosts_modal.jsp"/>
      </c:if>
    </div>
  </div>
</nav>
