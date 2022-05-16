<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- POSTS-->
<section id="posts">
  <div class="container">
    <div class="row">
      <div class="col">
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
            <c:forEach var="item" items="${paging.content}">
              <%--@elvariable id="item" type="com.blogads.entity.mysql.Post"--%>
              <tr>
                <td>${item.postId}</td>
                <td>${item.title}</td>
                <td>${item.category.name}</td>
                <td>${item.published ? 1 : 0}</td>
<%--                <fmt:parseDate value="${ item.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="dateTime" type="both"/>--%>
                <td><fmt:formatDate value="${ item.createdAt }" pattern="dd/MM/yy HH:mm"/></td>
                <td><a href="<c:url value='/admin/posts/${item.postId}'/>" class="btn btn-secondary">Details</a>
                  <a onclick="return confirm('Delete item #${item.postId}')"
                     href="<c:url value='/admin/delete-post/${item.postId}'/>">Delete</a>
                </td>
              </tr>
            </c:forEach>
            </tbody>
            </thead>
          </table>

          <!--PAGINATION-->
          <%--@elvariable id="search" type="String"--%>
          <c:if test="${paging.content.size() > 0}">
            <nav class="ml-4">
              <ul class="pagination">
                <li class="page-item <c:if test="${paging.number + 1 <= 1}">disabled</c:if>">
                  <a href="<c:url value='?page=${paging.number}&search=${search}'/>"
                     class="page-link">Prev</a>
                </li>
                <c:forEach var="item" begin="${paging.number + 1}"
                           end="${paging.number + 5 >= paging.totalPages ? paging.totalPages : paging.number + 5 }"
                           varStatus="theCount">
                  <li class="page-item <c:if test="${paging.number + 1 == theCount.index}">active</c:if>">
                    <a href="<c:url value='?page=${theCount.index}&search=${search}'/>"
                       class="page-link ">${theCount.index}</a>
                  </li>
                </c:forEach>
                <li class="page-item <c:if test="${paging.number >= paging.totalPages - 1}">disabled</c:if>">
                  <a href="<c:url value='?page=${paging.number + 2}&search=${search}' />"
                     class="page-link">Next</a>
                </li>
              </ul>
            </nav>
          </c:if>
          <!--END PAGINATION-->
        </div>
      </div>
    </div>
  </div>
</section>
<!-- END POSTS-->