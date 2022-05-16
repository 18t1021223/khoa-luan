<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: macvn
  Date: 25/02/2022
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12">
  <div class="sidebar">

    <div class="widget">
      <h2 class="widget-title">Bài viết gần đây</h2>
      <div class="blog-list-widget">
        <div class="list-group">
          <jsp:useBean id="recentPosts" scope="request" type="java.util.List"/>
          <c:forEach var="item" items="${recentPosts}">
            <a href="<c:url value='/posts/${item.postId}' />"
               class="list-group-item list-group-item-action flex-column align-items-start">
              <div class="w-100 justify-content-between">
                <h5 class="mb-1 navbar-post-title">${item.title}</h5>
                <small class="navbar-post-title">
                  <i class="fa fa-calendar" style="margin-right: 2px;"></i>
<%--                  <fmt:parseDate value="${ item.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="createAt" type="both"/>--%>
                  <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm"/>

                </small>
              </div>
            </a>
          </c:forEach>
        </div>
      </div><!-- end blog-list -->
    </div><!-- end widget -->
    <!--
            <div class="widget">
                <h2 class="widget-title">Advertising</h2>
                <div class="banner-spot clearfix">
                    <div class="banner-img">
                        <img src="upload/banner_04.jpg" alt="" class="img-fluid">
                    </div>
                </div>
            </div>-->

    <div class="widget">
      <h2 class="widget-title">Thể loại phổ biến</h2>
      <div class="link-widget">
        <ul>
          <%--@elvariable id="categories" type="java.util.List"--%>
          <c:forEach var="item" items="${categories}">
            <li>
              <a href="<c:url value='/categories/${item.categoryId}' />">${item.name}</a>
            </li>
          </c:forEach>
        </ul>
      </div><!-- end link-widget -->
    </div><!-- end widget -->

    <div class="widget">
      <h2 class="widget-title">Tags</h2>
      <div class="blog-title-area">
        <div class="row" style="padding: 0 15px 15px 15px">
          <jsp:useBean id="tags" scope="request" type="java.util.List"/>
          <c:forEach var="item" items="${tags}">
            <a class="tag" href="<c:url value='/hashtag/${item.tagId}'/>">${item.name}</a>
          </c:forEach>
        </div>
      </div><!-- end link-widget -->
    </div><!-- end widget -->
  </div><!-- end sidebar -->
</div>
<!-- end col -->