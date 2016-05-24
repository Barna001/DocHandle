'use strict';

/* Controllers */


var app = angular.module('docHandler.controllers', []);

app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});


app.controller('MyCtrl1', ['$scope', 'UserFactory', function ($scope, UserFactory) {
    $scope.bla = 'bla from controller';
    UserFactory.get({}, function (userFactory) {
        $scope.name = userFactory.name;
    })
}]);

app.controller('UserController', ['$scope', 'UserService', function ($scope, UserService) {
    $scope.users = {};
    $scope.roles = {};
    $scope.error = "";
    $scope.new = {
        name: "",
        role: ""
    };

    $scope.init = function () {
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
        UserService.save($scope.new).then(function () {

        }, function (response) {
            $scope.error = response;
        })
    }
}]);

app.controller('DocumentController', ['$scope', 'DocService', function ($scope, DocService) {
    $scope.docs = {};
    $scope.error = "";

    $scope.init = function () {
        $scope.getDocs();
    }

    $scope.getDocs = function () {
        DocService.getDocs().then(function (data) {
            $scope.docs = data;
        }, function (response) {
            $scope.error = response;
        })
    }
}]);

app.controller('FileController', ['$scope', 'FileService', function ($scope, FileService) {
    $scope.files = {};
    $scope.error = "";

    $scope.init = function () {
        $scope.getFiles();
    }

    $scope.getFiles = function () {
        FileService.getFiles().then(function (data) {
            $scope.files = data;
        }, function (response) {
            $scope.error = response;
        })
    }
}]);

app.controller('UserGroupController', ['$scope', 'UserGroupService', function ($scope, UserGroupService) {
    $scope.groups = {};
    $scope.error = "";

    $scope.init = function () {
        $scope.getGroups();
    }

    $scope.getGroups = function () {
        UserGroupService.getUserGroups().then(function (data) {
            $scope.groups = data;
        }, function (response) {
            $scope.error = response;
        })
    }
}]);
