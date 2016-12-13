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
        FileService.downloadLatestVersion(fileId).then(function(data){
            console.log("size:"+data.length);
            //var bytes = [];
            //for(var i=0;i<data.length;++i){
            //    var code = data.charCodeAt(i);
            //    bytes = bytes.concat([code & 0xff, code / 256 >>>0]);
            //}
            //console.log(data);
            var blob = new Blob([bytes], {type: "application/msword"});
            //FileSaver.saveAs(data, "name");
            var hiddenElement = document.createElement('a');
            hiddenElement.href = window.URL.createObjectURL(blob);
            hiddenElement.download = fileName;
            hiddenElement.click();
            //var clickEvent = new MouseEvent("click",{
            //    "view":window,
            //    "bubbles":true,
            //    "cancelable":false
            //});
            //hiddenElement.dispatchEvent(clickEvent);
            window.URL.revokeObjectURL(blob);
        })
    }

    $scope.deleteFile = function (id) {
        console.log("A fájl id-ja:"+id);
        FileService.deleteFile(id).then(function () {
            $scope.init();
        },function (response) {
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