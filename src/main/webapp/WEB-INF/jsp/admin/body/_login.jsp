<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!--ACTIONS-->
<section id="actions" class="py-4 mb-4 bg-light">
  <div class="container">
    <div class="row"></div>
  </div>
</section>
<!--END ACTIONS-->

<!-- LOGIN-->
<section id="login">
  <div class="container">
    <div class="row">
      <div class="col-md-6 mx-auto">
        <div class="card">
          <div class="card-header">
            <h4 class="text-center">Account Login</h4>
          </div>

          <div class="card-body">
            <form action="<c:url value='/j_spring_security_check'/>" method="post">
              <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username" autofocus
                       autocomplete="true"/>
              </div>
              <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password"/>
              </div>
              <input checked id="remember" name="remember-me" type="checkbox"/>
              <label for="remember">Remember me</label>
              <br>
              <%--@elvariable id="message" type="String"--%>
              <c:if test="${message != null}">
                <small class="text-danger">${message}</small>
              </c:if>
              <input type="submit" class="btn btn-primary btn-block" value="Login"/>
              <a href="<c:url value='/admin/forgot-password'/>">Forgot password</a>
              <a href="<c:url value='/admin/register'/>">Register</a>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!-- END LOGIN-->