'use strict';

/* Services */

var services = angular.module('ngdemo.services', ['ngResource']);

services.factory('UserFactory', function ($resource) {
    return $resource('/rest/users/defaultUser', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        }
    })
});
