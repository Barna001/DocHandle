/**
 * Created by Barna on 2016.09.24..
 */
angular.module('docHandler.controllers').controller('DocumentGroupController', ['$scope', 'DocumentGroupService', function ($scope, DocumentGroupService) {
    $scope.groups = {};
    $scope.error = "";
    $scope.new = {
        id: null,
        name: null,
        description: null,
        documents: []
    }

    $scope.init = function () {
        $scope.getGroups();
    }

    $scope.getGroups = function () {
        DocumentGroupService.getDocumentGroups().then(function (data) {
            $scope.groups = data;
        }, function (response) {
            $scope.error = response;
        })
    }

    $scope.save = function () {
        DocumentGroupService.saveGroup($scope.new).then(function () {
        }, function (response) {
            $scope.error = response;
        });
        $scope.init();
    }

    $scope.deleteAll = function () {
        DocumentGroupService.deleteAll().then(function () {
        }, function (response) {
            $scope.error = response;
        });
        $scope.init();
    }
}]);