/**
 * Created by Barna on 2016.09.24..
 */
angular.module('docHandler.controllers').controller('FileController', ['$scope', '$window', '$routeParams', 'FileService', 'DocService', /*'FileSaver',*/ function ($scope, $window, $routeParams, FileService, DocService/*, FileSaver*/) {
    $scope.files = {};
    $scope.docs = {};
    $scope.error = "";
    $scope.myFile = null;
    $scope.versionMessage = "";
    $scope.newVersion = {
        id: "",
        data: null,
        rootFileId: null,
        versionNumber: 0,
        fileType: ""
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

    $scope.initFile = function () {
        console.log($routeParams.id);
        $scope.newVersion.rootFileId=$routeParams.id;
    }

    $scope.setFile = function (file) {
        $window.location.href="#/addFileVersion/"+file.id;
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
        }, function (response) {
            $scope.error = response;
        });
    }

    $scope.downloadLatestVersion = function (fileId, fileName) {
        FileService.downloadLatestVersion(fileId).then(function(response){
            console.log("headers:"+response.headers('type'));
            var byteString = window.atob(response.data);
            var byteNumbers = new Array(byteString.length);
            for (var i = 0; i < byteString.length; i++) {
                byteNumbers[i] = byteString.charCodeAt(i);
            }
            var byteArray = new Uint8Array(byteNumbers);
            var blob = new Blob([byteArray], {type: response.headers('type')});
            var hiddenElement = document.createElement('a');
            hiddenElement.href = window.URL.createObjectURL(blob);
            hiddenElement.download = fileName;
            hiddenElement.click();
            window.URL.revokeObjectURL(blob);
        })
    }

    $scope.deleteFile = function (id) {
        FileService.deleteFile(id).then(function () {
            $scope.getFiles();
        }, function (response) {
            $scope.error = response;
        });
    }

    $scope.uploadFile = function () {
        var fileVersion = $scope.myFile;
        console.log($scope.newVersion);
        FileService.uploadFileToUrl(fileVersion, $scope.newVersion.rootFileId).then(function () {
        }, function (response) {
            $scope.error = response;
        });
    }
}]);