<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: macvn
  Date: 24/02/2022
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:choose>
  <c:when test="${tag != null}">
    <title>${tag.name} - Forest Time</title>
  </c:when>
  <c:when test="${category != null}">
    <title>${category.name} - Forest Time</title>
  </c:when>
  <c:otherwise>
    <title>Forest time - blog chia sẻ kiến thức</title>
  </c:otherwise>
</c:choose>

<section class="section first-section">
  <div class="container-fluid">
    <div class="masonry-blog clearfix">
      <jsp:useBean id="hostPosts" scope="request" type="java.util.List"/>
      <c:forEach var="item" items="${hostPosts}" varStatus="theCount">
        <div class="
                    <c:choose>
                        <c:when test="${theCount.index == 0}"> left-side </c:when>
                        <c:when test="${theCount.index == 1}"> center-side </c:when>
                        <c:otherwise>right-side</c:otherwise>
                    </c:choose>">

          <div class="masonry-box post-media" style="height: 270px;width: auto;">
            <img src="${item.image}" alt="img" class="img-fluid">
            <div class="shadoweffect" data-postid="${item.postId}">
              <div class="shadow-desc">
                <div class="blog-meta">
                                        <span class="bg-aqua"><a
                                          href="<c:url value='/categories/${item.category.categoryId}' />"
                                          title="">${item.category.name}</a>
                                        </span>
                  <h4>
                    <a class="hidden-content"
                       href="<c:url value='/posts/${item.postId}'/>">${item.title}</a>
                  </h4>
                  <small>
                    <a href="<c:url value='/posts/${item.postId}'/>">
                        <%--                      <fmt:parseDate value="${item.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="createAt" type="both"/>--%>
                        <%--                      <fmt:formatDate value="${createAt}"/>--%>
                      <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                    </a>
                  </small>
                </div><!-- end meta -->
              </div><!-- end shadow-desc -->
            </div><!-- end shadow -->
          </div><!-- end post-media -->
        </div>
      </c:forEach>
    </div><!-- end masonry -->
  </div>
</section>

<section class="section wb">
  <div class="container">
    <div class="row">
      <div class="col-lg-9 col-md-12 col-sm-12 col-xs-12">
        <div class="page-wrapper">
          <div class="blog-list clearfix">

            <%--@elvariable id="posts" type="org.springframework.data.domain.Page"--%>
            <c:forEach var="item" items="${posts.content}" varStatus="theCount">
              <div class="blog-box row">
                <div class="col-md-5">
                  <div class="post-media post-media-img">
                    <a href="<c:url value='/posts/${item.postId}' />">
                      <img src="${item.image}" alt="img"
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
                     style="font-size: 16px;-webkit-line-clamp: 3;">${item.description}</p>
                    <%--                                    view--%>
                  <small>
                    <a href="<c:url value='/posts/${item.postId}' />">
                      <i class="fa fa-eye"></i>
                        ${item.viewNumber}</a>
                  </small>
                  <small>
                    <a href="<c:url value='/posts/${item.postId}' />">
                      <i class="fa fa-calendar" style="margin-right: 2px;"></i>
                        <%--                      <fmt:parseDate value="${ item.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="createAt"--%>
                        <%--                                     type="both"/>--%>
                        <%--                      <fmt:formatDate value="${createAt}"/>--%>
                      <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                    </a>
                  </small>
                  <small>
                    <a href="<c:url value='/admin/${item.admin.adminId}' />">${item.admin.fullName}</a>
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
            <%--                        ads--%>
            <%--                        <div class="row">--%>
            <%--                            <div class="col-lg-10 offset-lg-1">--%>
            <%--                                <div class="banner-spot clearfix">--%>
            <%--                                    <div class="banner-img">--%>
            <%--                                        <img src="upload/banner_05.jpg" alt="" class="img-fluid">--%>
            <%--                                    </div><!-- end banner-img -->--%>
            <%--                                </div><!-- end banner -->--%>
            <%--                            </div><!-- end col -->--%>
            <%--                        </div>--%>
          </div><!-- end blog-list -->
        </div><!-- end page-wrapper -->

        <hr class="invis">

        <div class="row">
          <div class="col-md-12">
            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-start">
                <%--PRE--%>
                <c:if test="${posts.number + 1 > 1}">
                  <li class="page-item">
                    <a class="page-link"
                       href="<c:url value='?page=${posts.number}' />">Prev</a>
                  </li>
                </c:if>
                <c:if test="${posts.totalPages > 1}">
                  <c:forEach var="item" begin="${posts.number + 1}"
                             end="${posts.number + 5 >= posts.totalPages ? posts.totalPages : posts.number + 5 }"
                             varStatus="theCount">
                    <li class="page-item">
                      <c:choose>
                        <c:when test="${theCount.index != posts.number + 1}">
                          <a class="page-link"
                             href="<c:url value='?page=${theCount.index}'/> ">${theCount.index}</a>
                        </c:when>
                        <c:otherwise>
                          <p class="page-link active">${theCount.index}</p>
                        </c:otherwise>
                      </c:choose>
                    </li>
                  </c:forEach>
                </c:if>
                <%--NEXT--%>
                <c:if test="${posts.number < posts.totalPages - 1}">
                  <li class="page-item">
                    <a class="page-link"
                       href="<c:url value='?page=${posts.number + 2}' />">Next</a>
                  </li>
                </c:if>
              </ul>
            </nav>
          </div><!-- end col -->
        </div><!-- end row -->
      </div><!-- end col -->
      <c:import url="../base/_navbar.jsp"/>
    </div><!-- end row -->
  </div><!-- end container -->
</section>