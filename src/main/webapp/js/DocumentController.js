/**
 * Created by Barna on 2016.09.24..
 */
angular.module('docHandler.controllers').controller('DocumentController', ['$scope', 'DocService', 'DocumentGroupService', 'UserService', function ($scope, DocService, DocumentGroupService, UserService) {
    $scope.docs = {};
    $scope.groups = {};
    $scope.users = {};
    $scope.error = "";
    $scope.new = {
        id: null,
        name: null,
        content: null,
        creationDate: null,
        modificationDate: null,
        owner: null,
        containingGroups: []
    }

    $scope.init = function () {
        $scope.getDocs();
    }

    $scope.initGroupsAndUsers = function () {
        $scope.getGroups();
        $scope.getUsers();
    }

    $scope.getDocs = function () {
        DocService.getDocs().then(function (data) {
            $scope.docs = data;
        }, function (response) {
            $scope.error = response;
        })
    }
    $scope.getGroups = function () {
        DocumentGroupService.getDocumentGroups().then(function (data) {
            $scope.groups = data;
        }, function (response) {
            $scope.error = response;
        })
    }
    $scope.getUsers = function () {
        UserService.getUsers().then(function (data) {
            $scope.users = data;
        }, function (response) {
            $scope.error = response;
        })
    }

    $scope.save = function () {
        DocService.saveDoc($scope.new).then(function () {
            $scope.init();
        }, function (response) {
            $scope.error = response;
        });
    }

    $scope.deleteAll = function () {
        DocService.deleteAll().then(function () {
            $scope.init();
        }, function (response) {
            $scope.error = response;
        });
    }
}]);