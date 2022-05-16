<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!--ACTIONS-->
<section id="actions" class="py-4 mb-4 bg-light">
    <div class="container">
        <div class="row"></div>
    </div>
</section>
<!--END ACTIONS-->

<!-- REGISTER-->
<section id="login">
    <div class="container">
        <div class="row">
            <div class="col-md-6 mx-auto">
                <div class="card">
                    <div class="card-header">
                        <h4 class="text-center">Register</h4>
                    </div>

                    <div class="card-body">
                        <form action="<c:url value='/admin/register'/>" method="post">
                            <div class="form-group">
                                <label for="username">Email</label>
                                <input type="email" class="form-control" id="username" name="email" autofocus
                                       autocomplete="true" max="150"/>
                            </div>
                            <div class="form-group">
                                <label for="fullName">Full name</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" max="50"/>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control" id="password" name="password" max="150"/>
                            </div>
                            <div class="form-group">
                                <label for="re-password">Re-Password</label>
                                <input type="password" class="form-control" id="re-password" name="confirmPassword"
                                       max="150"/>
                            </div>
                            <br>
                            <%--@elvariable id="message" type="String"--%>
                            <c:if test="${message != null}">
                                <small class="text-danger">${message}</small>
                            </c:if>
                            <input type="submit" class="btn btn-primary btn-block" value="Register"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- END REGISTER-->