(function ($) {
    var is_available = true;
    var add_category_is_available = true;

    // set value modal form category
    $('table td button').click(function () {
        if (is_available) {
            $(".modal #name").val($(this).data('name'))
            $(".modal #id").val($(this).data('id'))
        }
    });

    // UPDATE
    $('#ct-form').submit(function (e) {
        e.preventDefault();
        is_available = false;
        $('#message-name').html("")
        $('#message-id').html("")
        let form = $('button[form="ct-form"]');
        form.prop("disabled", true);
        form.html("<span class=\"spinner-border spinner-border-sm\"></span> Save");
        $.ajax({
            url: $(this).attr('action'),
            xhrFields: {
                withCredentials: true
            },
            type: 'POST',
            dataType: 'html',
            data: $(this).serialize()
        }).done(function (rs) {
            location.reload();
        }).fail(function (rs) {
            is_available = true
            $('#message-name').html(rs.responseText.replaceAll('["', '').replaceAll('"]', ''))
            $('#message-name').css("display", 'block');
            form.prop("disabled", false);
            form.html("Save");
        })
    });

    //INSERT
    $('#add-form').submit(function (e) {
        e.preventDefault();
        add_category_is_available = false;
        $('#add-form-message-name').html("")
        let form = $('button[form="add-form"]');
        form.prop("disabled", true);
        form.html("<span class=\"spinner-border spinner-border-sm\"></span> Save");
        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            dataType: 'html',
            data: $(this).serialize()
        }).done(function (rs) {
            window.location = window.location.href
        }).fail(function (rs) {
            add_category_is_available = true
            $('#add-form-message-name').html(rs.responseText.replaceAll('["', '').replaceAll('"]', ''))
            $('#add-form-message-name').css("display", 'block');
            form.prop("disabled", false);
            form.html("Save");
        })
    });

    // BTN SAVE CHANGE IN SINGLE POST
    $('#update-item-btn').click(function (e) {
        e.preventDefault();
        $('#post-save').click()
    });

    $('#add-item-btn').click(function () {
        if (window.location.href.lastIndexOf('/admin/posts') >= 0) {
            window.location.href = '/admin/post';
        }
    });

    // BUTTON CLICK REMOVE IMAGE
    $('#delete-image').click(function () {
        $('#blah').attr('src', '#').width(0).height(0)
        $('input[name="currentUrlImage"]').val('')
        $('#message-image').html('')
    })

    // BUTTON CLICK UNDO IMAGE
    $('#undo-image').click(function () {
        let currentUrlImage = $('input[name="currentUrlImage"]');
        if (currentUrlImage.data('id') != '') {
            $('#blah').attr('src', currentUrlImage.data('id')).width(640).height(360)
        }
        $('#message-image').html('')
        $('input[name="currentUrlImage"]').val($('input[name="currentUrlImage"]').data('id'))
    })

    $('#year').text(new Date().getUTCFullYear());
})(jQuery);