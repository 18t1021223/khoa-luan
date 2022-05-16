<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- INFO HOSTS MODAL-->
<div class="modal fade" id="update-host">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header bg-success text-white">
        <h5 class="modal-title">Info user</h5>
        <button class="close" data-dismiss="modal">
          <span>&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="ct-form-user" action="<c:url value='/admin/hosts/update'/> ">

          <div class="form-group">
            <label for="email">Email</label>
            <input type="text" class="form-control" id='email' name="email" disabled readonly
                   value="<security:authentication property="principal.username" />"
            />
            <label for="role">Role</label>
            <input type="text" class="form-control" id='role' name="role" disabled readonly
                   value="<security:authentication property="principal.roles" />"
            />

            <label for="fullname">Full name</label>
            <input type="text" class="form-control" id='fullname' name="fullName"
              value="<security:authentication property="principal.fullName" />"
                   required min="1" maxlength="50"/>
            <div class="invalid-feedback" id="message-name"></div>

            <label for="address">Address</label>
            <input type="text" class="form-control" id='address' name="address"
              value="<security:authentication property="principal.address" />"
            />

          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button form="ct-form-user" class="btn btn-primary" data-toggle="modal">
          Save
        </button>
      </div>
    </div>
  </div>
</div>
<!-- INFO ADD CATEGORY MODAL-->