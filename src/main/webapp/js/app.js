'use strict';

// Declare app level module which depends on filters, and services
angular.module('docHandler', ['docHandler.filters', 'docHandler.services', 'docHandler.directives', 'docHandler.controllers']).config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'MyCtrl1'})
        .when('/users', {templateUrl: 'partials/users.html', controller: 'UserController'})
        .when('/documents', {templateUrl: 'partials/documents.html', controller: 'DocumentController'})
        .when('/files', {templateUrl: 'partials/files.html', controller: 'FileController'})
        .when('/userGroups', {templateUrl: 'partials/userGroups.html', controller: 'UserGroupController'});
    $routeProvider.otherwise({redirectTo: '/users'});
}]);
