/**
 *
 * @author lrk
 * @date 2019/4/27 下午11:30
 */
'use strict';

/**
 * Config for the router
 */
angular.module('app')
    .run(
        ['$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ]
    )
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {

                $urlRouterProvider
                    .otherwise('/signin');
                $stateProvider
                    .state('signin', {
                        controller: 'SigninCtrl',
                        url: '/signin',
                        templateUrl: 'static/tpl/signin.html'
                    })
                    .state('app', {
                        controller: 'GlobalCtrl',
                        url: '/app',
                        templateUrl: 'static/tpl/app.html'
                    })
                    .state('app.admin', {
                        url: '/admin',
                        templateUrl: 'static/tpl/admin/admin.html'
                    })
                    .state('app.teacher', {
                        url: '/teacher',
                        templateUrl: 'static/tpl/teacher/teacher.html'
                    })
                    .state('app.student', {
                        url: '/student',
                        templateUrl: 'static/tpl/student/student.html'
                    })
                    .state('app.teacher.allCourse', {
                        controller: 'TeacherAllCourseCtrl',
                        url: '/allCourse',
                        templateUrl: 'static/tpl/teacher/all-course.html'
                    })
                    .state('app.teacher.myCourse', {
                        controller: 'TeacherMyCourseCtrl',
                        url: '/myCourse',
                        templateUrl: 'static/tpl/teacher/my-course.html'
                    })
                    .state('app.student.allCourse', {
                        controller: 'StudentAllCourseCtrl',
                        url: '/allCourse',
                        templateUrl: 'static/tpl/student/all-course.html'
                    })
                    .state('app.student.myCourse', {
                        controller: 'StudentMyCourseCtrl',
                        url: '/myCourse',
                        templateUrl: 'static/tpl/student/my-course.html'
                    })
                    .state('app.admin.dept', {
                        controller: 'DeptCtrl',
                        url: '/dept',
                        templateUrl: 'static/tpl/admin/dept.html'
                    })
                    .state('app.admin.class', {
                        controller: 'ClassCtrl',
                        url: '/class',
                        templateUrl: 'static/tpl/admin/class.html'
                    })
                    .state('app.admin.teacher', {
                        controller: 'TeacherManageCtrl',
                        url: '/teacher',
                        templateUrl: 'static/tpl/admin/teacher.html'
                    })
                    .state('app.admin.student', {
                        controller: 'StudentManageCtrl',
                        url: '/student',
                        templateUrl: 'static/tpl/admin/student.html'
                    })
                    .state('app.admin.course', {
                        controller: 'CourseManageCtrl',
                        url: '/course',
                        templateUrl: 'static/tpl/admin/course.html'
                    })
                    .state('app.admin.password', {
                        controller: 'ChangePwdCtrl',
                        url: '/password',
                        templateUrl: 'static/tpl/common/change-pwd.html'
                    })
                    .state('app.teacher.password', {
                        controller: 'ChangePwdCtrl',
                        url: '/password',
                        templateUrl: 'static/tpl/common/change-pwd.html'
                    })
                    .state('app.student.password', {
                        controller: 'ChangePwdCtrl',
                        url: '/password',
                        templateUrl: 'static/tpl/common/change-pwd.html'
                    })
                    .state('app.teacher.grade', {
                        controller: 'RecordGradeCtrl',
                        url: '/recordGrade/:cid',
                        templateUrl: 'static/tpl/teacher/grade.html'
                    })
                    .state('app.student.grade', {
                        controller: 'QueryGradeCtrl',
                        url: '/queryGrade/:cid',
                        templateUrl: 'static/tpl/student/grade.html'
                    })
            }
        ]
    );
