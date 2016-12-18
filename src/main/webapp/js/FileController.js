/**
 * Created by Barna on 2016.09.24..
 */
angular.module('docHandler.controllers').controller('FileController', ['$scope', 'FileService', 'DocService', /*'FileSaver',*/ function ($scope, FileService, DocService/*, FileSaver*/) {
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

    $scope.downloadLatestVersion = function (fileId, fileName) {
        console.log("A fájl id-ja:"+fileId);
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
        console.log("A fájl id-ja:" + id);
        FileService.deleteFile(id).then(function () {
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