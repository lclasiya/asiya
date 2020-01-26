$('#ero-table').bootstrapTable({
    url: '/user',
    striped: true,
    clickToSelect: true,
    singleSelect: true,
    uniqueId : 'id',
    pagination: true,
    pageNumber:1,
    pageSize: 5,
    pageList: [2,5,10,20],
    sidePagination: 'server',
    queryParamsType:'',
    columns: [
        {field: 'selectItem', radio: true},
        {field: 'id', title: 'ユーザーID'},
        {field: 'username', title: '登録名'},
        {field: 'sex', title: '性別'},
        {field: 'tend', title: '趣向'},
        {field: 'email', title: 'メールアドレス'},
        {field: 'password', title: 'パスワード'},
        {field: 'authority', title: '権限'},

    ]

});