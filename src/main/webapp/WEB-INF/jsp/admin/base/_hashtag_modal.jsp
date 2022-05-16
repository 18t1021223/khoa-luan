<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<!-- INFO TAG MODAL-->
<div class="modal fade" id="update">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header bg-success text-white">
        <h5 class="modal-title">Info tag</h5>
        <button class="close" data-dismiss="modal">
          <span>&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="ct-form" action="<c:url value='/admin/update-hashtag'/> ">
          <div class="form-group">
            <label for="name">Tag name</label>
            <input type="text" class="form-control" id='name' name="name" required min="1" maxlength="50"/>
            <div class="invalid-feedback" id="message-name"></div>
            <input type="hidden" class="form-control" id='id' name="id"/>
            <div class="invalid-feedback" id="message-id"></div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button form="ct-form" class="btn btn-primary" data-toggle="modal">
          Save
        </button>
      </div>
    </div>
  </div>
</div>
<!-- INFO ADD TAG MODAL-->

<!-- ADD TAG MODAL-->
<div class="modal fade" id="add">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header bg-success text-white">
        <h5 class="modal-title">Add Tag</h5>
        <button class="close" data-dismiss="modal">
          <span>&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="add-form" action="<c:url value='/admin/insert-hashtag'/> ">
          <div class="form-group">
            <label for="add-form-name">Tag name</label>
            <input type="text" class="form-control" id="add-form-name" name="name" required min="1"
                   max="50"/>
            <div class="invalid-feedback" id="add-form-message-name"></div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button form="add-form" class="btn btn-primary" data-toggle="modal">
          Save
        </button>
      </div>
    </div>
  </div>
</div>
<!-- END ADD TAG MODAL-->

