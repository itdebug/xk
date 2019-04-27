/**
 *
 * @author lrk
 * @date 2019/4/27 下午11:29
 */
app.controller("AppCtrl", function ($scope, $state) {
    $scope.app = {
        name: "中国最强-选课管理系统 "
    };
    $scope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            if (toState.name !== 'signin' && !sessionStorage.getItem('token')) {
                event.preventDefault();
                $state.go('signin');
            }
        })
});
