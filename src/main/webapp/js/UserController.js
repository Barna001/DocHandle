/**
 * Created by Barna on 2016.09.24..
 */

angular.module('docHandler.controllers').controller('UserController', ['$scope', 'UserService', function ($scope, UserService) {
    $scope.users = {};
    $scope.roles = {};
    $scope.error = "";
    $scope.new = {
        id: null,
        name: null,
        ownDocuments: [],
        role: null,
        groups: []
    };

    $scope.initUsers = function () {
        $scope.getUsers();
        $scope.getRoles();
    }

    $scope.getUsers = function () {
        UserService.getUsers().then(function (data) {
            $scope.users = data;
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
        console.log("Az id:" + id);
        UserService.deleteUser(id).then(function () {
            $scope.initUsers();
        }, function (response) {
            $scope.error = response;
        });
    }

    $scope.deleteAll = function () {
        UserService.deleteAll().then(function () {
            $scope.initUsers();
        }, function (response) {
            $scope.error = response;
        });
    }
}]);