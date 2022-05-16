<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="statistic" type="com.blogads.controller.admin.dto.StatisticDto"--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<title>Admin - Home</title>

<!--ACTIONS-->
<%--<section id="actions" class="py-4 mb-4 bg-light">--%>
<%--    <div class="container">--%>
<%--        <div class="row">--%>
<%--            <div class="col-md-2">--%>
<%--                <a href="#" class="btn btn-success btn-block" data-toggle="modal" data-target="#addPostModal">--%>
<%--                    Add post--%>
<%--                </a>--%>
<%--            </div>--%>
<%--            <div class="col-md-2">--%>
<%--                <a href="#" class="btn btn-success btn-block" data-toggle="modal" data-target="#addCategoryModal">--%>
<%--                    Add category--%>
<%--                </a>--%>
<%--            </div>--%>
<%--            <div class="col-md-2">--%>
<%--                <a href="#" class="btn btn-success btn-block" data-toggle="modal" data-target="#addUserModal">--%>
<%--                    Add hashtag--%>
<%--                </a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</section>--%>
<!--END ACTIONS-->

<!-- POSTS-->
<section id="posts">
  <div class="container" style="margin-top: 70px;">
    <div class="row">
      <div class="col-md-9">
        <div class="card">
          <div class="card-header">
            <h4>Latest Posts</h4>
          </div>
          <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
              <th>#</th>
              <th>Title</th>
              <th>Category</th>
              <th>Public</th>
              <th>Date</th>
              <th></th>
            </tr>
            <tbody>
            <%--@elvariable id="posts" type="org.springframework.data.domain.Page"--%>
            <c:forEach var="item" items="${posts.content}">
              <%--@elvariable id="item" type="com.blogads.entity.mysql.Post"--%>
              <tr>
                <td>${item.postId}</td>
                <td>${item.title}</td>
                <td>${item.category.name}</td>
                <td>${item.published ? 1 : 0}</td>
<%--                <fmt:parseDate value="${ item.createdAt }" pattern="yyyy-MM-ddHH:mm" var="dateTime"--%>
<%--                               type="both"/>--%>
                <td><fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yy HH:mm"/></td>
                <td><a href="<c:url value='/admin/posts/${item.postId}' />" class="btn btn-secondary">Details</a>
                </td>
              </tr>
            </c:forEach>
            </tbody>
            </thead>
          </table>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-center bg-primary text-white mb-3">
          <div class="card-body">
            <h4>Posts</h4>
            <h4><i class="fas fa-pencil-alt"></i> ${statistic.posts}</h4>
            <a href="<c:url value='/admin/posts'/>" class="btn btn-outline-light btn-sm">View</a>
          </div>
        </div>

        <div class="card text-center bg-success text-white mb-3">
          <div class="card-body">
            <h4>Categories</h4>
            <h4><i class="fas fa-folder"></i> ${statistic.categories}</h4>
            <a href="<c:url value='/admin/categories'/>" class="btn btn-outline-light btn-sm">View</a>
          </div>
        </div>

        <div class="card text-center bg-warning text-white mb-3">
          <div class="card-body">
            <h4>Tags</h4>
            <h4><i class="fas fa-users"></i> ${statistic.hashtags}</h4>
            <a href="<c:url value='/admin/hashtags'/>" class="btn btn-outline-light btn-sm">View</a>
          </div>
        </div>

      </div>
    </div>
  </div>
</section>

<!-- END POSTS-->
