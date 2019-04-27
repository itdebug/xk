/**
 *
 * @author lrk
 * @date 2019/4/27 下午11:30
 */
app.controller('CourseManageCtrl', function ($scope, $modal, $http, host, $state, SweetAlert, $timeout) {
    $http.get(host + 'Admin/course/getAll').success(function (d) {
        if (d.code == 1)
            $scope.courses = d.data;
        if (d.data.length == 0) $('.table').dataTable();
    });
    $scope.add = function () {
        $modal.open({
            templateUrl: 'add.html',
            controller: function ($scope, $modalInstance) {
                $scope.course = {};
                $scope.ok = function () {
                    $scope.check = true;
                    $timeout(function () {
                        if ($('form .has-error').length > 0)
                            return;
                        $http.post(host + 'Admin/course/add', $scope.course).success(function (d) {
                            if (d.code == 1)
                                SweetAlert.swal("增加成功", "新增“" + $scope.course.name + "”", "success");
                            else
                                SweetAlert.swal("增加失败", d.msg, "error");
                            $modalInstance.close();
                            $state.reload();
                        });
                    }, 100)
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss();
                }
            },
            size: 'md'
        });
    };
    $scope.del = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.courses[i].id;
        });
        if (ids.length == 0) {
            SweetAlert.swal("提示", "请选择删除的课程", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "是否确定删除选中的课程？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false
            },
            function (ok) {
                if (ok) {
                    $http.post(host + 'Admin/course/del', {ids: ids}).success(function (d) {
                        if (d.code == 1) {
                            SweetAlert.swal("删除成功", "成功删除选中的课程", "success");
                            $state.reload();
                        }
                        else
                            SweetAlert.swal("删除失败", d.msg, "error");
                    });
                }
            });
    };
    $scope.edit = function () {
        var ids = getSelectedList();
        if (getSelectedList().length != 1) {
            SweetAlert.swal("提示", "请选择一个编辑的课程", "warning");
            return;
        }
        var id = ids[0];
        var course = $scope.courses[id];
        $modal.open({
            templateUrl: 'add.html',
            controller: function ($scope, $modalInstance) {
                $scope.course = course;
                $scope.ok = function () {
                    $scope.check = true;
                    $timeout(function () {
                        if ($('form .has-error').length > 0)
                            return;
                        $http.post(host + 'Admin/course/upd', $scope.course).success(function (d) {
                            if (d.code == 1) {
                                SweetAlert.swal("编辑成功", "", "success");
                                $state.reload()
                            }
                            else
                                SweetAlert.swal("编辑失败", d.msg, "error");
                            $modalInstance.close();
                            $state.reload();
                        });
                    }, 100)
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss();
                }
            },
            size: 'md'
        });
    };
});
