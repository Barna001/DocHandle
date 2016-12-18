'use strict';

// Declare app level module which depends on filters, and services
angular.module('docHandler', ['docHandler.filters', 'docHandler.services', 'docHandler.directives', 'docHandler.controllers']).config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/users', {templateUrl: 'partials/users.html', controller: 'UserController'})
        .when('/createUser', {templateUrl: 'partials/createUser.html', controller: 'UserController'})
        .when('/documents', {templateUrl: 'partials/documents.html', controller: 'DocumentController'})
        .when('/createDocument', {templateUrl: 'partials/createDocument.html', controller: 'DocumentController'})
        .when('/files', {templateUrl: 'partials/files.html', controller: 'FileController'})
        .when('/createFile', {templateUrl: 'partials/createFile.html', controller: 'FileController'})
        .when('/addFileVersion/:id', {templateUrl: 'partials/createFileAddVersion.html', controller: 'FileController'})
        .when('/userGroups', {templateUrl: 'partials/userGroups.html', controller: 'UserGroupController'})
        .when('/createUserGroup', {templateUrl: 'partials/createUserGroup.html', controller: 'UserGroupController'})
        .when('/documentGroups', {templateUrl: 'partials/documentGroups.html', controller: 'DocumentGroupController'})
        .when('/createDocumentGroup', {templateUrl: 'partials/createDocumentGroup.html', controller: 'DocumentGroupController'});
    $routeProvider.otherwise({redirectTo: '/users'});
}]);
