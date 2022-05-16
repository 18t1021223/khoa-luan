<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="search" type="String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<c:if test="${search != null}">
  <section id="search" class="py-4 mb-4 bg-light">
    <div class="container">
      <div class="row">
        <div class="col-md-2 mb-2">
          <c:if test="${!requestPath.equals('/admin/hosts')}">
            <a href="#" id="add-item-btn" class="btn btn-success btn-block" data-toggle="modal"
               data-target="#add">Add</a>
          </c:if>
        </div>
        <div class="col-md-6 ml-auto">
          <form action="<c:url value=''/> " method="get">
            <div class="input-group">
              <input type="text" name="search" value="${search}" class="form-control" placeholder="Search">
              <div class="input-group-append">
                <button class="btn btn-primary">Search</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>
</c:if>