/**
 * Created by Barna on 2016.09.24..
 */
angular.module('docHandler.controllers').controller('UserGroupController', ['$scope', 'UserGroupService', function ($scope, UserGroupService) {
    $scope.groups = {};
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


    $scope.save = function () {
        UserGroupService.saveGroup($scope.new).then(function () {
            $scope.initGroups();
        }, function (response) {
            $scope.error = response;
        });
    }

    $scope.deleteGroup = function (id) {
        UserGroupService.deleteGroup(id).then(function () {
            $scope.initGroups();
        }, function (response) {
            $scope.error = response;
        });
    }

    $scope.deleteAll = function () {
        UserGroupService.deleteAll().then(function () {
            $scope.initGroups();
        }, function (response) {
            $scope.error = response;
        });
    }
}]);