<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- POSTS-->
<section id="categories">
  <div class="container">
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-header">
            <h4>Latest Categories</h4>
          </div>
          <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
              <th>#</th>
              <th>Name</th>
              <th></th>
            </tr>
            <tbody>
            <c:forEach var="item" items="${paging.content}">
              <%--@elvariable id="item" type="com.blogads.entity.mysql.Category"--%>
              <tr>
                <td>${item.categoryId}</td>
                <td>${item.name}</td>
                <td>
                  <sec:authorize access="hasAuthority('ADMIN')">
                    <button data-id="${item.categoryId}" data-name="${item.name}" class="btn btn-secondary"
                            data-toggle="modal" data-target="#update">Details
                    </button>
                  </sec:authorize>
                  <a onclick="return confirm('Delete item #${item.categoryId}')"
                     href="<c:url value='/admin/delete-category/${item.categoryId}'/>">Delete</a>
                </td>
              </tr>
            </c:forEach>
            </tbody>
            </thead>
          </table>

          <!--PAGINATION-->
          <%--@elvariable id="search" type="String"--%>
          <%--@elvariable id="paging" type="org.springframework.data.domain.Page"--%>
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
<c:import url="/WEB-INF/jsp/admin/base/_category_modal.jsp"/>