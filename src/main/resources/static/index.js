(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run)
        .controller('authController', function ($scope, $http, $localStorage, $location) {
            const contextPath = 'http://localhost:8189/market';

            $scope.tryToAuth = function () {
                $http.post(contextPath + '/auth', $scope.user)
                    .then(function successCallback(response) {
                        if (response.data.token) {
                            $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                            $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};
                            $scope.user.username = null;
                            $scope.user.password = null;
                            window.location.reload(true);
                            console.log($localStorage.currentUser);
                        }
                    }, function errorCallback(response) {
                        window.alert(response.data.message);
                        $scope.clearUser();
                    });
            };

            $scope.tryToLogout = function () {
                $scope.clearUser();
                if ($scope.user.username) {
                    $scope.user.username = null;
                }
                if ($scope.user.password) {
                    $scope.user.password = null;
                }
                $location.path("/about");
            };

            $scope.clearUser = function () {
                delete $localStorage.currentUser;
                $http.defaults.headers.common.Authorization = '';
            };

            $scope.isUserLoggedIn = function () {
                if ($localStorage.currentUser) {
                    return true;
                } else {
                    return false;
                }
            };
        });

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/about', {
                templateUrl: 'about/about.html',
                controller: 'aboutController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/product_edit', {
                templateUrl: 'product_edit/product_edit.html',
                controller: 'productEditController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            });

        $httpProvider.interceptors.push(function ($q) {
            return {
                'responseError': function (rejection, $localStorage, $http) {
                    var defer = $q.defer();
                    if (rejection.status == 401 || rejection.status == 403) {
                        console.log('error: 401-403 ' + rejection.status);
                        if (!(localStorage.getItem("localUser") === null)) {
                            delete $localStorage.currentUser;
                            $http.defaults.headers.common.Authorization = '';
                        }
                    }
                    return $q.reject(rejection);
                }
            };
        });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {
            console.log($localStorage.currentUser)
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
    }
})();
