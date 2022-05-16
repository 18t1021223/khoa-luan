<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!--ACTIONS-->
<section id="actions" class="py-4 mb-4 bg-light">
  <div class="container">
    <div class="row"></div>
  </div>
</section>
<!--END ACTIONS-->

<!-- FORGOT PASSWORD-->
<section id="login">
  <div class="container">
    <div class="row">
      <div class="col-md-6 mx-auto">
        <div class="card">
          <div class="card-header">
            <h4 class="text-center">Forgot password</h4>
          </div>

          <div class="card-body">
            <form action="<c:url value='/admin/forgot-password'/>" method="post">
              <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="mail" value="${email}"/>
              </div>
              <br>
              <%--@elvariable id="message" type="String"--%>
              <c:if test="${message != null}">
                <small class="text-danger">${message}</small>
              </c:if>
              <input type="submit" class="btn btn-primary btn-block" value="Send"/>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!-- END FORGOT PASSWORD-->