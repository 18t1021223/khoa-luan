<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!--ACTIONS-->
<section id="actions" class="py-4 mb-4 bg-light">
  <div class="container">
    <div class="row"></div>
  </div>
</section>
<!--END ACTIONS-->

<!-- NEW PASSWORD-->
<section id="login">
  <div class="container">
    <div class="row">
      <div class="col-md-6 mx-auto">
        <div class="card">
          <div class="card-header">
            <h4 class="text-center">New password</h4>
          </div>

          <div class="card-body">
            <form action="<c:url value='/admin/new-password'/>" method="post">
              <div class="form-group">
                <label for="password">New password</label>
                <input type="password" class="form-control" id="password" name="password" min="8" max="150"/>
              </div>
              <div class="form-group">
                <label for="re-password">New password</label>
                <input type="password" class="form-control" id="re-password" name="confirmPassword" min="8" max="150"/>
              </div>
              <br>
              <%--@elvariable id="message" type="String"--%>
              <c:if test="${message != null}">
                <small class="text-danger">${message}</small>
              </c:if>
              <input type="hidden" name="verifyToken" value="${verifyToken}">
              <input type="submit" class="btn btn-primary btn-block" value="Send"/>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!-- END NEW PASSWORD-->