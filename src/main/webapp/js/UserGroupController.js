/**
 * Created by Barna on 2016.09.24..
 */
angular.module('docHandler.controllers').app.controller('UserGroupController', ['$scope', 'UserGroupService', 'UserService', function ($scope, UserGroupService, UserService) {
    $scope.groups = {};
    $scope.users = {};
    $scope.error = "";
    $scope.new = {
        id: null,
        name: null,
        users: []
    }

    $scope.initGroups = function () {
        UserGroupService.getUserGroups().then(function (data) {
            $scope.groups = data;
        }, function (response) {
            $scope.error = response;
        })
    }
    $scope.initUsers = function () {
        UserService.getUsers().then(function (data) {
            $scope.users = data;
        }, function (response) {
            $scope.error = response;
        })
    }

    //$scope.getGroups = function () {
    //    UserGroupService.getUserGroups().then(function (data) {
    //        $scope.groups = data;
    //    }, function (response) {
    //        $scope.error = response;
    //    })
    //}
    //$scope.getUsers = function () {
    //    UserService.getUsers().then(function (data) {
    //        $scope.users = data;
    //    }, function (response) {
    //        $scope.error = response;
    //    })
    //}

    $scope.save = function () {
        UserGroupService.saveGroup($scope.new).then(function () {
        }, function (response) {
            $scope.error = response;
        });
        $scope.initGroups();
    }

    $scope.deleteAll = function () {
        UserGroupService.deleteAll().then(function () {
        }, function (response) {
            $scope.error = response;
        });
        $scope.initGroups();
    }
}]);