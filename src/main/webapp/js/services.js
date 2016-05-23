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

services.factory('UserService',UserService)

UserService.$inject = [ '$http', '$q' ];
function UserService($http, $q) {

    var service = {
        saveUser : saveUser,
        getUser : getUser
        //getUsers : getUsers,
        //updateUser : updateUser,
        //deleteUser: deleteUser,
        //getCurrentUser : getCurrentUser
    };

    return service;

    function saveUser(userName) {
        var deferred = $q.defer();
        $http.post("rest/users/new", userName).success(function(data, status) {
            deferred.resolve(data);
        }).error(function(status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    function getUser(name) {
        var deferred = $q.defer();
        $http.get("rest/users/userByName",{params:{name:name}}).success(function(data, status) {
            deferred.resolve(data);
        }).error(function(data, status) {
            deferred.reject(status);
        });
        return deferred.promise;
    }

    //function getUsers(){
    //    var deferred = $q.defer();
    //    $http.get("api/user").success(function(data, status) {
    //        deferred.resolve(data);
    //    }).error(function(data, status) {
    //        deferred.reject(status);
    //    });
    //    return deferred.promise;
    //}
    //
    //function updateUser(user) {
    //    user.id = user.userId;
    //    //must delete, because the PUT would not work
    //    delete user['links'];
    //    delete user['userId'];
    //    delete user['password-re'];
    //    var deferred = $q.defer();
    //    $http.put("api/user/"+user.id, user).success(function(data, status) {
    //        deferred.resolve(data);
    //    }).error(function(data, status) {
    //        deferred.reject(status);
    //    });
    //    return deferred.promise;
    //
    //}
    //
    //function getCurrentUser() {
    //    var deferred = $q.defer();
    //    $http.get("api/user/username").success(function(data, status) {
    //        deferred.resolve(data);
    //    }).error(function(status) {
    //        deferred.reject(status);
    //    });
    //    return deferred.promise;
    //}
    //
    //function deleteUser(id) {
    //    var deferred = $q.defer();
    //    $http.delete("api/user/"+id).success(function(data, status) {
    //        deferred.resolve(data);
    //    }).error(function(status) {
    //        deferred.reject(status);
    //    });
    //    return deferred.promise;
    //}

}