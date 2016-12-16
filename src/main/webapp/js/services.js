'use strict';

/* Services */

var services = angular.module('docHandler.services', ['ngResource']);

services.factory('UserFactory', function ($resource) {
    return $resource('/rest/users/defaultUser', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        }
    })
});

services.factory('UserService', UserService);
services.factory('DocService', DocService);
services.factory('FileService', FileService);
services.factory('UserGroupService', UserGroupService);
services.factory('DocumentGroupService', DocumentGroupService);

function UserService($http, $q) {

    var service = {
        saveUser: saveUser,
        getUser: getUser,
        getUsers: getUsers,
        getRoles: getRoles,
        deleteUser: deleteUser
        //updateUser : updateUser,
        //getCurrentUser : getCurrentUser
    };

    return service;

    function saveUser(user) {
        var deferred = $q.defer();
        $http.post("rest/users", user).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function getUser(name) {
        var deferred = $q.defer();
        $http.get("rest/users/name", {params: {name: name}}).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function getUsers() {
        var deferred = $q.defer();
        $http.get("rest/users").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function getRoles() {
        var deferred = $q.defer();
        $http.get("rest/users/roles").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function deleteUser(id) {
        var deferred = $q.defer();
        $http.delete("rest/users?id=" + id, null).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }
};

function DocService($http, $q) {

    var service = {
        saveDoc: saveDoc,
        getDoc: getDoc,
        getDocs: getDocs,
        deleteDoc: deleteDoc
    };

    return service;

    function saveDoc(doc) {
        var deferred = $q.defer();
        $http.post("rest/documents", doc).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function getDoc(name) {
        var deferred = $q.defer();
        $http.get("rest/documents/documentByName", {params: {name: name}}).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function getDocs() {
        var deferred = $q.defer();
        $http.get("rest/documents").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function deleteDoc(id) {
        var deferred = $q.defer();
        $http.delete("rest/documents?id=" + id, null).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }
};

function DocumentGroupService($http, $q) {

    var service = {
        getDocumentGroups: getDGroups,
        saveGroup: saveGroup,
        deleteGroup: deleteGroup
    };

    return service;

    function getDGroups() {
        var deferred = $q.defer();
        $http.get("rest/documentGroups").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function saveGroup(group) {
        var deferred = $q.defer();
        $http.post("rest/documentGroups", group).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function deleteGroup(id) {
        var deferred = $q.defer();
        $http.delete("rest/documentGroups?id="+id, null).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function deleteAll() {
        var deferred = $q.defer();
        $http.delete("rest/documentGroups?id=*", null).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }
};

function FileService($http, $q) {

    var service = {
        getFiles: getFiles,
        saveFile: saveFile,
        downloadLatestVersion: downloadLatestVersion,
        deleteFile: deleteFile,
        uploadFileToUrl: uploadFileToUrl
    };

    return service;

    function getFiles() {
        var deferred = $q.defer();
        $http.get("rest/files").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function saveFile(file) {
        var deferred = $q.defer();
        $http.post("rest/files", file).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function downloadLatestVersion(fileId) {
        var deferred = $q.defer();
        $http.get("rest/files/latestVersion?fileId=" + fileId).success(function (data, status, headers) {
            deferred.resolve({'data':data,'headers':headers});
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function deleteFile(id) {
        var deferred = $q.defer();
        $http.delete("rest/files?id=" + id, null).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function uploadFileToUrl(fileVersion, fileId) {
        var deferred = $q.defer();
        var fd = new FormData();
        fd.append('file', fileVersion);

        $http.put('http://localhost:8080/rest/files/' + fileId, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function () {
                return deferred.promise;
            })
            .error(function () {
                return deferred.promise;
            });
        return deferred.promise;
    }
};

function UserGroupService($http, $q) {

    var service = {
        getUserGroups: getUGroups,
        saveGroup: saveGroup,
        deleteGroup: deleteGroup
    };

    return service;

    function getUGroups() {
        var deferred = $q.defer();
        $http.get("rest/userGroups").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function saveGroup(group) {
        var deferred = $q.defer();
        $http.post("rest/userGroups", group).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function deleteGroup(id) {
        var deferred = $q.defer();
        $http.delete("rest/userGroups?id="+id, null).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }
};
