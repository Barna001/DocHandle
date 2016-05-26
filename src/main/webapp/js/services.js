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
        getRoles: getRoles
        //updateUser : updateUser,
        //deleteUser: deleteUser,
        //getCurrentUser : getCurrentUser
    };

    return service;

    function saveUser(user) {
        var deferred = $q.defer();
        $http.post("rest/users/new", user).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function getUser(name) {
        var deferred = $q.defer();
        $http.get("rest/users/userByName", {params: {name: name}}).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function getUsers() {
        var deferred = $q.defer();
        $http.get("rest/users/all").success(function (data, status) {
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
};

function DocService($http, $q) {

    var service = {
        saveDoc: saveDoc,
        getDoc: getDoc,
        getDocs: getDocs
    };

    return service;

    function saveDoc(doc) {
        var deferred = $q.defer();
        $http.post("rest/documents/new", doc).success(function (data, status) {
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
        $http.get("rest/documents/all").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }
};

function DocumentGroupService($http, $q) {

    var service = {
        getDocumentGroups: getDGroups,
        saveGroup: saveGroup
    };

    return service;

    function getDGroups() {
        var deferred = $q.defer();
        $http.get("rest/documentGroups/all").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function saveGroup(group) {
        var deferred = $q.defer();
        $http.post("rest/documentGroups/new", group).success(function (data, status) {
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
        addNewVersionToFile: addNewVersionToFile
    };

    return service;

    function getFiles() {
        var deferred = $q.defer();
        $http.get("rest/files/all").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function saveFile(file) {
        var deferred = $q.defer();
        $http.post("rest/files/new", file).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function addNewVersionToFile(fileVersion, str) {
        var deferred = $q.defer();
        $http.post("rest/files/addNewVersionString/" + str, fileVersion).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }
};

function UserGroupService($http, $q) {

    var service = {
        getUserGroups: getUGroups,
        saveGroup: saveGroup
    };

    return service;

    function getUGroups() {
        var deferred = $q.defer();
        $http.get("rest/userGroups/all").success(function (data, status) {
            deferred.resolve(data);
        }).error(function (data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function saveGroup(group) {
        var deferred = $q.defer();
        $http.post("rest/userGroups/new", group).success(function (data, status) {
            deferred.resolve(data);
        }).error(function (status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }
};
