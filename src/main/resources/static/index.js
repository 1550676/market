(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run)
        .controller('authController', function ($scope, $http, $localStorage) {
            $scope.tryToAuth = function () {
                $http.post($localStorage.contextPath + '/api/v1/auth', $scope.user)
                    .then(function successCallback(response) {
                        if (response.data.token) {
                            $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                            $localStorage.currentUser = {
                                username: $scope.user.username,
                                token: response.data.token,
                                roleList : response.data.roleList,
                                isAdmin: false
                            };
                            $localStorage.currentUser.isAdmin = $scope.isUserAdmin();
                            $scope.user.username = null;
                            $scope.user.password = null;
                            $scope.user.roleList = null;
                            console.log($localStorage.currentUser);
                        }
                    }, function errorCallback() {
                        $scope.clearUser();
                    });
            };

            $scope.isUserAdmin = function () {
                if ($localStorage.currentUser && $localStorage.currentUser.roleList.indexOf('ROLE_ADMIN') >= 0) {
                    return true;
                } else {
                    return false;
                }
            };

            $scope.tryToLogout = function () {
                $scope.clearUser();
                if ($scope.user.username) {
                    $scope.user.username = null;
                }
                if ($scope.user.password) {
                    $scope.user.password = null;
                }
                if ($scope.user.roleList) {
                    $scope.user.roleList = null;
                }
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
            })
            .when('/order', {
                templateUrl: 'order/order.html',
                controller: 'orderController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            });

        $httpProvider.interceptors.push(function ($q, $localStorage) {
            return {
                'responseError': function (rejection) {
                    if (rejection.data.message) {
                        window.alert(rejection.data.message);
                    } else {
                        var answer = JSON.parse(rejection.data);
                        window.alert(answer.message);
                    }
                    if (rejection.status === 401 || rejection.status === 403) {
                        console.log($localStorage.currentUser);
                        delete $localStorage.currentUser;
                        window.location.reload(true);
                    }
                    return $q.reject(rejection);
                }
            };
        });
    }

    function run($rootScope, $http, $localStorage) {
        $localStorage.contextPath = 'http://localhost:8189/market';
        // запрашиваем категории
        $http.get($localStorage.contextPath + "/api/v1/categories")
            .then(function (response) {
                $localStorage.categories = response.data;
            });
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
    }
})();
