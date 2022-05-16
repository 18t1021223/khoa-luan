<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="post" scope="request" class="com.blogads.entity.mysql.Post"/>
<%--
  Created by IntelliJ IDEA.
  User: macvn
  Date: 25/02/2022
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:set var="req" value="${pageContext.request}"/>
<c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}"/>
<c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="pageUrl" value="${ baseURL }${ requestPath }${ not empty params?'?'+=params:'' }"/>

<title>${post.title}</title>

<section class="section wb">
  <div class="container">
    <div class="row">
      <div class="col-lg-9 col-md-12 col-sm-12 col-xs-12">
        <div class="page-wrapper">
          <div class="blog-title-area">
                        <span class="color-green">
                            <a href="<c:url value='/categories/${post.category.categoryId}'/>">${post.category.name}</a>
                        </span>

            <h3>${post.title}</h3>

            <div class="blog-meta big-meta">
              <small>
                <i class="fa fa-calendar" style="margin-right: 6px;"></i>
                <%--                <fmt:parseDate value="${post.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="createAt" type="both"/>--%>
                <fmt:formatDate value="${post.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
              </small>
              <c:if test="${post.author != null}">
                <small><a href="#" onclick="return false">${post.author}</a></small>
              </c:if>
              <%--                            <small><a href="#" title=""><i class="fa fa-eye"></i> 2344</a></small>--%>
            </div><!-- end meta -->

            <div class="post-sharing">
              <ul class="list-inline">

                <div id="fb-root"></div>
                <script async defer crossorigin="anonymous"
                        src="https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v13.0"
                        nonce="wl7FaWb0"></script>
                <li><a data-href="${pageUrl}"
                       data-layout="button_count"
                       data-size="large"
                       target="_blank"
                       class="fb-button btn btn-primary"
                       href="https://www.facebook.com/sharer/sharer.php?u=${pageUrl}">
                  <i class="fa fa-facebook"></i><span class="down-mobile">Share on Facebook</span></a>
                </li>
                <li>
                  <div class="zalo-share-button zl-button btn" data-href="${pageUrl}"
                       data-oaid="579745863508352884" data-customize="true"
                       style="background-color: #03a5fa !important;border-color: initial !important;">
                    <i class="zb-logo-zalo"></i> <span class="down-mobile">Share on Zalo</span></div>
                </li>
                <%--                <li><a href="#" class="gp-button btn btn-primary"><i class="fa fa-google-plus"></i></a>--%>
                <%--                </li>--%>
              </ul>
            </div>
            <!-- end post-sharing -->
          </div><!-- end title -->

          <%--                    <div class="single-post-media">--%>
          <%--                        <img src="<c:url value='/upload/${post.image}'/>" alt="img" class="img-fluid">--%>
          <%--                    </div><!-- end media -->--%>

          <div class="blog-content">
            <strong>${post.description}</strong>
            <br><br>
            ${post.content}
            <br>
          </div><!-- end content -->

          <div class="blog-title-area">
            <%--            <jsp:useBean id="tagOfPost" scope="request" type="java.util.List"/>--%>
            <c:if test="${tagOfPost != null && tagOfPost.size() > 0 }">
              <div class="tag-cloud-single">
                <div class="row">
                  <span>Tags</span>
                  <c:forEach var="item" items="${tagOfPost}">
                    <a href="<c:url value='/hashtag/${item.tagId}'/>">${item.name}</a>
                  </c:forEach>
                </div>
              </div>
              <!-- end meta -->
            </c:if>

            <%--                        <div class="post-sharing">--%>
            <%--                            <ul class="list-inline">--%>
            <%--                                <li><a href="#" class="fb-button btn btn-primary"><i class="fa fa-facebook"></i> <span--%>
            <%--                                        class="down-mobile">Share on Facebook</span></a></li>--%>
            <%--                                <li><a href="#" class="tw-button btn btn-primary"><i class="fa fa-twitter"></i> <span--%>
            <%--                                        class="down-mobile">Tweet on Twitter</span></a></li>--%>
            <%--                                <li><a href="#" class="gp-button btn btn-primary"><i class="fa fa-google-plus"></i></a>--%>
            <%--                                </li>--%>
            <%--                            </ul>--%>
            <%--                        </div>--%>
            <!-- end post-sharing -->
          </div><!-- end title -->
          <%--ads--%>
          <%--                    <div class="row">--%>
          <%--                        <div class="col-lg-12">--%>
          <%--                            <div class="banner-spot clearfix">--%>
          <%--                                <div class="banner-img">--%>
          <%--                                    <img src="upload/banner_01.jpg" alt="" class="img-fluid">--%>
          <%--                                </div>--%>
          <%--                            </div>--%>
          <%--                        </div>--%>
          <%--                    </div>--%>

          <hr class="invis1">

          <div class="custombox clearfix">
            <h4 class="small-title">Bài viết khác</h4>

            <c:forEach var="item" items="${posts.content}" varStatus="theCount">
              <div class="blog-box row">
                <div class="col-md-5">
                  <div class="post-media post-media-img">
                    <a href="<c:url value='/posts/${item.postId}' />">
                      <img src="${item.image}" alt="image"
                           class="img-fluid">
                      <div class="hovereffect"></div>
                    </a>
                  </div><!-- end media -->
                </div><!-- end col -->

                <div class="blog-meta big-meta col-md-7">
                  <h4>
                    <a class="hidden-content"
                       href="<c:url value='/posts/${item.postId}' />">${item.title}</a>
                  </h4>
                  <p class="hidden-content"
                     style="font-size: 14px;-webkit-line-clamp: 3;">${item.description}</p>
                    <%--                                    view--%>
                    <%--                                    <small>--%>
                    <%--                                        <a href="<c:url value='/posts/${item.postId}' />">--%>
                    <%--                                            <i class="fa fa-eye"></i>--%>
                    <%--                                                ${item.viewNumber}</a>--%>
                    <%--                                    </small>--%>
                  <small>
                    <a style="color: #a3a5a8;" href="<c:url value='/posts/${item.postId}' />">
                      <i class="fa fa-calendar" style="margin-right: 2px;"></i>
                        <%--                      <fmt:parseDate value="${item.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="createAt" type="both"/>--%>
                      <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                    </a>
                  </small>
                  <span class="bg-aqua">
                                        <a
                                          href="<c:url value='/categories/${item.category.categoryId}'/>">${item.category.name}</a>
                                    </span>

                </div><!-- end meta -->
              </div>
              <!-- end blog-box -->
              <c:if test="${theCount.index < posts.content.size()-1}">
                <hr class="invis">
              </c:if>
            </c:forEach>
          </div><!-- end custom-box -->

        </div><!-- end page-wrapper -->
      </div><!-- end col -->
      <c:import url="../base/_navbar.jsp"/>
    </div><!-- end row -->
  </div><!-- end container -->
</section>
<script src="https://sp.zalo.me/plugins/sdk.js"></script>