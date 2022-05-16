<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: macvn
  Date: 24/02/2022
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="collapse top-search" id="collapseExample">
  <div class="card card-block">
    <div class="newsletter-widget text-center">
      <form class="form-inline" action="<c:url value='/search'/>">
        <input type="text" class="form-control" name="search" placeholder="Tìm kiếm">
        <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
      </form>
    </div><!-- end newsletter -->
  </div>
</div>
<!-- end top-search -->

<div class="topbar-section">
  <div class="container-fluid">
    <div class="row">
      <div class="col-lg-4 col-md-6 col-sm-6 hidden-xs-down">
        <a href="<c:url value='/admin/'/>">Trang admin</a>
      </div><!-- end col -->

      <div class="col-lg-4 hidden-md-down">
      </div><!-- end col -->

      <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">
        <div class="topsearch text-right">
          <a data-toggle="collapse" href="#collapseExample" aria-expanded="false"
             aria-controls="collapseExample"><i class="fa fa-search"></i> Tìm kiếm</a>
        </div><!-- end search -->
      </div><!-- end col -->
    </div><!-- end row -->
  </div><!-- end header-logo -->
</div>
<!-- end topbar -->

<div class="header-section">
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <div class="logo">
          <a href="<c:url value='/' />">
            <img src="<c:url value='/assets/user/img/version/foresttime.png'/>" alt="logo">
          </a>
        </div><!-- end logo -->
      </div>
    </div><!-- end row -->
  </div><!-- end header-logo -->
</div>
<!-- end header -->

<header class="header">
  <div class="container">
    <nav class="navbar navbar-inverse navbar-toggleable-md">
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#Forest-Timemenu"
              aria-controls="Forest Timemenu" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-md-center" id="Forest-Timemenu">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a style="font-weight: 400;" class="nav-link color-green-hover" href="<c:url value='/' />">Trang chủ</a>
          </li>
          <%--@elvariable id="categories" type="java.util.List"--%>
          <c:forEach var="item" items="${categories}">
            <li class="nav-item">
              <a style="font-weight: 400;" class="nav-link color-green-hover"
                 href="<c:url value='/categories/${item.categoryId}' />">${item.name}</a>
            </li>
          </c:forEach>
        </ul>
      </div>
    </nav>
  </div><!-- end container -->
</header>
<!-- end header -->