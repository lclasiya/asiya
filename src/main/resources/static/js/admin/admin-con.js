$(function () {
    
    $(".user-action").click(function() {
        $.ajax({
            url: "/user/add",
            success: function(data){
                $("#userFormContainer").html(data);
            },
            error : function(data) {
                toastr.error("処理ミス");
            }
        });
    });
    $(".user-edit").click(function () {
        var row=$("#ero-table").bootstrapTable('getSelections');
        if (row == false){
            alert("一つデータを選んでください");
            return false;
        }
        else {
            var ids;
            ids = row[0].id;
        }
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: "/user/edit/"+ids,
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function(data){
                $("#userFormContainer").html(data);
            },
            error : function(data) {
                toastr.error("処理ミス");
            }
        });
    });

    $(".user-delete").click(function () {
        var row=$("#ero-table").bootstrapTable('getSelections');
        if (row == false){
            alert("一つデータを選んでください");
            return false;
        }
        else {
            var ids;
            ids = row[0].id;
        }
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: "/user/" + ids,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function(data){
                if (data.success) {
                    window.location="/admin";
                } else {
                    toastr.error(data.message);
                }
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    $("#submitEdit").click(function() {
        $.ajax({
            url: "/user",
            type: 'POST',
            data:$('#userForm').serialize(),
            success: function(data){
                $('#userForm')[0].reset();

                if (data.success) {
                    // 从新刷新主界面
                    window.location="/admin";
                } else {
                    toastr.error(data.message);
                }

            },
            error : function() {
                toastr.error("error!");
            }
        });
    });








});