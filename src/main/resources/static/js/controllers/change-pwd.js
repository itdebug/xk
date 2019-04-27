/**
 *
 * @author lrk
 * @date 2019/4/27 下午11:30
 */
app.controller('ChangePwdCtrl', function ($scope, $http, host, $timeout, SweetAlert, $state) {
    $scope.submit = function () {
        $scope.check = true;
        $timeout(function () {
            if ($("form .has-error").length > 0)
                return;
            $http.post(host + "Access/changePwd", {newPwd: $scope.password, oldPwd: $scope.oldPwd})
                .success(function (d) {
                    if (d.code == 1) {
                        $scope.logout();
                        SweetAlert.swal('修改成功', '修改密码成功，请重新登录。', 'success');
                        $state.go('signin')
                    } else SweetAlert.swal('修改失败', d.msg, 'error');
                });
        }, 100)
    }
});
