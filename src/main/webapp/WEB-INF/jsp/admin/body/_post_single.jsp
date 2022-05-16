<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<script src="https://cdn.tiny.cloud/1/zwyq1ulm55ffqv5qidmr5sgomtd0e6w8yw9ke27mtad19gnw/tinymce/5/tinymce.min.js"
        referrerpolicy="origin"></script>
<%--@elvariable id="post" type="com.blogads.entity.mysql.Post"--%>
<!--ACTIONS-->
<section id="actions" class="py-4 mb-4 bg-light">
  <div class="container">
    <div class="row">
      <div class="col-md-3 mb-2">
        <a href="<c:url value='/admin/home'/>" class="btn btn-light btn-block">
          <i class="fas fa-arrow-left"></i> Back To Dashboard
        </a>
      </div>
      <div class="col-md-3 mb-2">
        <a href='#' id="update-item-btn" class="btn btn-success btn-block">
          <i class="fas fa-check"></i> Save Changes
        </a>
      </div>
      <c:if test="${post.postId > 0 }">
        <div class="col-md-3 mb-2">
          <a href="<c:url value='/admin/delete-post/${post.postId}'/>"
             onclick="return confirm('Delete item #${post.postId}')" class="btn btn-danger btn-block">
            <i class="fas fa-trash"></i> Delete Post
          </a>
        </div>
      </c:if>
    </div>
  </div>
</section>
<!--END ACTIONS-->

<!-- DETAILS-->
<section id="details">
  <div class="container">
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-header" style="display: flex;justify-content: space-between;align-items: center;">
            <div><h4>Post</h4></div>
            <c:if test="${post.postId > 0}">
              <div>
                <span>
                  <i class="fa fa-eye" style="margin-right: 5px;"></i>${post.viewNumber} -
                  <i class="fa fa-calendar" style="margin-right: 5px;"></i>
<%--                  <fmt:parseDate value="${ post.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="createAt" type="both"/>--%>
<%--                  <fmt:parseDate value="${ post.updatedAt }" pattern="yyyy-MM-dd'T'HH:mm" var="updateAt" type="both"/>--%>
                  <fmt:formatDate value="${ post.createdAt }" pattern="dd/MM/yy HH:mm"/> -
                  <fmt:formatDate value="${ post.updatedAt }" pattern="dd/MM/yy HH:mm"/>
                </span>
              </div>
            </c:if>
          </div>
          <div class="card-body">
            <c:set var="formUrl" value="/admin/post"/>
            <c:if test="${post.postId > 0}">
              <c:set var="formUrl" value="/admin/update-post/${post.postId}"/>
            </c:if>
            <form action="${formUrl}" method="post" enctype="multipart/form-data">
              <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                  <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" class="form-control" id='title' name="title" required maxlength="70"
                           value="${post.title}"/>
                    <div class="invalid-feedback" id="message-title"></div>
                  </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                  <label for="author">Author</label>
                  <input readonly type="text" class="form-control" id='author' name="author" maxlength="50"
                         value="${post.author == null ? post.admin.fullName : post.author}"/>
                  <div class="invalid-feedback" id="message-author"></div>
                </div>
              </div>

              <div class="form-group">
                <label for="description">Description</label>
                <textarea type="text" id='description' class="form-control" name="description"
                          maxlength="300">${post.description}</textarea>
              </div>
              <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                  <div class="form-group">
                    <label for="category">Category</label>
                    <select class="form-control" name='category' id='category' required>
                      <%--@elvariable id="categories" type="java.util.List"--%>
                      <%--@elvariable id="item" type="com.blogads.entity.mysql.Category"--%>
                      <c:choose>
                        <c:when test="${categories.size()==0}">
                          <option value=""></option>
                        </c:when>
                        <c:otherwise>
                          <c:forEach var="item" items="${categories}">
                            <option value="${item.categoryId}"
                                    <c:if
                                      test="${post.category.categoryId == item.categoryId}">selected</c:if>>${item.name}</option>
                          </c:forEach>
                        </c:otherwise>
                      </c:choose>
                    </select>
                    <div class="invalid-feedback" id="message-category"></div>
                  </div>
                </div>

                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-4">
                  <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                      <div class="form-group">
                        <label for="image">Upload image</label>
                        <div class="custom-file">
                          <input type="file" onchange="readURL(this);"
                                 class="custom-file-input"
                                 id="image" name="image"
                                 accept="image/*"/>
                          <label for="image" class="custom-file-label">Choose File</label>
                        </div>
                        <div class="invalid-feedback" id="message-image"></div>
                        <input type="hidden" name="currentUrlImage" value="${post.image}" data-id="${post.image}">
                      </div>
                    </div>
                    <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                      <span id="delete-image" title='delete' class="btn btn-danger" style="margin-top: 2rem;">X</span>
                    </div>
                    <c:if test="${post.postId > 0}">
                      <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                      <span title="undo" id="undo-image"><i class='fas fa-undo'
                                                            style='font-size:24px;margin-top: 2rem;padding-top: 7px; cursor: pointer;'></i></span>
                      </div>
                    </c:if>
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                  <div class="row">

                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                      <div class="form-group">
                        <label>Hashtag</label>
                        <div class="custom-checkbox" style="height: 230px; overflow-y: scroll;">
                          <%--@elvariable id="hashtags" type="java.util.List"--%>
                          <%--@elvariable id="tag" type="com.blogads.entity.mysql.Tag"--%>
                          <c:choose>
                            <c:when test="${post.postId == 0}">
                              <c:forEach var="tag" items="${hashtags}">
                                <input type="checkbox" id="check${tag.tagId}" value="${tag.tagId}" name="hashtag">
                                <label for="check${tag.tagId}">${tag.name}</label><br>
                              </c:forEach>
                            </c:when>
                            <c:otherwise>
                              <c:set var="eqs" value="false"/>
                              <c:forEach var="tag" items="${hashtags}">
                                <c:set var="eqs" value="false"/>
                                <c:forEach var="tagOfPost" items="${post.postTags}">
                                  <c:if test="${tagOfPost.tag.tagId == tag.tagId}">
                                    <c:set var="eqs" value="true"/>
                                  </c:if>
                                </c:forEach>
                                <input type="checkbox" id="check${tag.tagId}"
                                       value="${tag.tagId}" name="hashtag" <c:if test="${eqs == true}">checked</c:if>>
                                <label for="check${tag.tagId}">${tag.name}</label><br>
                              </c:forEach>
                            </c:otherwise>
                          </c:choose>
                        </div>
                      </div>
                    </div>

                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                      <div class="custom-control custom-checkbox mb-3">
                        <input type="checkbox" class="custom-control-input" id="customCheck" name="published"
                               <c:if test="${post.published}">checked</c:if> >
                        <label class="custom-control-label" for="customCheck">Public post</label>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12" id="region-image">
                  <c:if test="${post.postId > 0 }">
                    <img id="blah" src="${post.image}" alt="" width="640" height="360"/>
                  </c:if>
                  <c:if test="${post.postId == 0 }">
                    <img id="blah" src="#" alt=""/>
                  </c:if>
                </div>
              </div>
              <div class="form-group">
                <label for="open-source-plugins">Body</label>
                <textarea name="content" class="form-control" id="open-source-plugins"
                          maxlength="2147483647">${post.content}</textarea>
              </div>
              <button class="btn btn-success" id="post-save">Save</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!-- END DETAILS-->

<script>
    var useDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
    tinymce.init({
        selector: 'textarea#open-source-plugins',
        paste_data_images: true,
        plugins: 'preview paste importcss searchreplace autolink autosave directionality code visualblocks visualchars fullscreen image link codesample table charmap hr pagebreak nonbreaking anchor advlist lists wordcount  textpattern noneditable help charmap quickbars lineheight',
        extended_valid_elements: 'a[href|target=_blank]',
        imagetools_cors_hosts: ['picsum.photos'],
        menubar: 'file edit view insert format tools table help',
        toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify lineheight | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen code preview save | insertfile image template link anchor codesample | ltr rtl',
        lineheight_formats: "8px 9px 10px 11px 12px 14px 16px 18px 20px 22px 24px 26px 36px",
        fontsize_formats: "8px 9px 10px 11px 12px 14px 15px 16px 17px 18px 19px 20px 22px 24px 26px 36px",
        toolbar_sticky: true,
        autosave_ask_before_unload: true,
        autosave_interval: '30s',
        autosave_prefix: '{path}{query}-{id}-',
        autosave_restore_when_empty: false,
        autosave_retention: '2m',
        image_advtab: true,
        automatic_uploads: true,
        importcss_append: true,
        file_picker_callback: function (cb, value, meta) {
            var input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');
            /*
              Note: In modern browsers input[type="file"] is functional without
              even adding it to the DOM, but that might not be the case in some older
              or quirky browsers like IE, so you might want to add it to the DOM
              just in case, and visually hide it. And do not forget do remove it
              once you do not need it anymore.
            */
            input.onchange = function () {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onload = function () {
                    /*
                      Note: Now we need to register the blob in TinyMCEs image blob
                      registry. In the next release this part hopefully won't be
                      necessary, as we are looking to handle it internally.
                    */
                    var id = 'blobid' + (new Date()).getTime();
                    var blobCache = tinymce.activeEditor.editorUpload.blobCache;
                    var base64 = reader.result.split(',')[1];
                    var blobInfo = blobCache.create(id, file, base64);
                    blobCache.add(blobInfo);
                    /* call the callback and populate the Title field with the file name */
                    cb(blobInfo.blobUri(), {title: file.name});
                };
                reader.readAsDataURL(file);
            };
            input.click();
        },
        images_upload_handler: function (blobInfo, success, failure) {
            var image_size = blobInfo.blob().size / 1000;  // image size in kbytes
            var max_size = 1000;                // max size in kbytes
            if (image_size > max_size) {
                failure('Image is too large( ' + image_size + ') ,Maximum image size is:' + max_size / 1000 + ' MB', {remove: true});
            }
        },
        height: 700,
        image_caption: true,
        quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
        noneditable_noneditable_class: 'mceNonEditable',
        toolbar_mode: 'sliding',
        contextmenu: 'link image table',
        skin: useDarkMode ? 'oxide-dark' : 'oxide',
        content_css: useDarkMode ? 'dark' : 'default',
        content_style: 'body { font-family:Noto Serif,sans-serif; font-size:17px; font-weight: 300; line-height: 31px} img{ margin: 10px 13px 10px 0px;}'
    });

    function readURL(input) {
        if (input.files && input.files[0]) {
            if (input.files[0].size / 1000 / 1000 > 1) {
                $('#message-image').html('File size exceeds 1MB').css('display', 'block')
                return
            }
            $('#message-image').html('').css('display', 'none')
            let reader = new FileReader();
            reader.onload = function (e) {
                $('#blah').attr('src', e.target.result).width(640).height(360);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>