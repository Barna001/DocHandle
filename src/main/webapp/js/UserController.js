/**
 * Created by Barna on 2016.09.24..
 */

angular.module('docHandler.controllers').controller('UserController', ['$scope', 'UserService', 'UserGroupService', function ($scope, UserService, UserGroupService) {
    $scope.users = {};
    $scope.groups = {};
    $scope.roles = {};
    $scope.error = "";
    $scope.new = {
        id: null,
        name: null,
        ownDocuments: [],
        role: null,
        groups: [],
        groupNames: null
    };

    $scope.initUsers = function () {
        $scope.getUsers();
        $scope.getRoles();
        $scope.getGroups();
    }

    $scope.getUsers = function () {
        UserService.getUsers().then(function (data) {
            $scope.users = data;
            console.log($scope.users);
        }, function (response) {
            $scope.error = response;
        })
    }

    $scope.getGroups = function () {
        UserGroupService.getUserGroups().then(function (data) {
            $scope.groups = data;
        }, function (response) {
            $scope.error = response;
        })
    }

    $scope.getRoles = function () {
        UserService.getRoles().then(function (data) {
            $scope.roles = data;
        }, function (response) {
            $scope.error = response;
        })
    }

    $scope.save = function () {
        UserService.saveUser($scope.new).then(function () {
            $scope.initUsers();
        }, function (response) {
            $scope.error = response;
        });
    }

    $scope.deleteUser = function (id) {
        UserService.deleteUser(id).then(function () {
            $scope.initUsers();
        }, function (response) {
            $scope.error = response;
        });
    }
}]);