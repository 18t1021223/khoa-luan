<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- ADMINS-->
<section id="posts">
  <div class="container">
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-header">
            <h4>Admins</h4>
          </div>
          <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
              <th>#</th>
              <th>Email</th>
              <th>FullName</th>
              <th>Role</th>
              <th>Enable</th>
              <th></th>
            </tr>
            <tbody>
            <c:forEach var="item" items="${hosts.content}">
              <%--@elvariable id="item" type="com.blogads.entity.mysql.Admin"--%>
              <tr>
                <td>${item.adminId}</td>
                <td>${item.username}</td>
                <td>${item.fullName}</td>
                <td>${item.role}</td>
                <td>${item.enable ? 1 : 0}</td>
                <td>
                  <c:if test="${item.enable == true}">
                    <a onclick="return confirm('Disable item #${item.username}')"
                       href="<c:url value='/admin/hosts/${item.adminId}?b=false'/>">Disable</a>
                  </c:if>

                  <c:if test="${item.enable == false}">
                    <a onclick="return confirm('Enable item #${item.username}')"
                       href="<c:url value='/admin/hosts/${item.adminId}?b=true'/>">Enable</a>
                  </c:if>
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
<!-- END ADMINS-->