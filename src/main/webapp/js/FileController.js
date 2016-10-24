/**
 * Created by Barna on 2016.09.24..
 */
angular.module('docHandler.controllers').controller('FileController', ['$scope', 'FileService', 'DocService', function ($scope, FileService, DocService) {
    $scope.files = {};
    $scope.docs = {};
    $scope.error = "";
    $scope.chosen = null;
    $scope.myFile = null;
    $scope.versionMessage = "";
    $scope.newVersion = {
        id: "",
        data: null,
        rootFileId: null,
        versionNumber: 0
    }
    $scope.new = {
        id: null,
        name: null,
        rootDocument: null,
        latestVersionNumber: 0,
        latestVersionId: null
    }

    $scope.initDocs = function () {
        $scope.getDocs();
    }

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

    $scope.getDocs = function () {
        DocService.getDocs().then(function (data) {
            $scope.docs = data;
        }, function (response) {
            $scope.error = response;
        })
    }

    $scope.save = function () {
        FileService.saveFile($scope.new).then(function () {
            $scope.init();
        }, function (response) {
            $scope.error = response;
        });
    }

    //$scope.saveVersion = function () {
    //    FileService.addNewVersionToFile($scope.newVersion, $scope.versionMessage).then(function () {
    //    }, function (response) {
    //        $scope.error = response;
    //    });
    //    $scope.init();
    //}

    $scope.deleteFile = function (id) {
        console.log("A fájl id-ja:"+id);
        FileService.deleteFile(id).then(function () {
            $scope.init();
        },function (response) {
            $scope.error = response;
        });
    }

    $scope.deleteAll = function () {
        FileService.deleteAll().then(function () {
            $scope.init();
        }, function (response) {
            $scope.error = response;
        });
    }

    $scope.uploadFile = function () {
        var fileVersion = $scope.myFile;

        console.log('file is ');
        console.dir(fileVersion);

        FileService.uploadFileToUrl(fileVersion, $scope.newVersion.rootFileId).then(function () {
            $scope.init();
        }, function (response) {
            $scope.error = response;
        });
    }
}]);