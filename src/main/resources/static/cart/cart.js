angular.module('app').controller('cartController', function ($scope, $http, $localStorage) {

    fillTable = function () {
        $http.get($localStorage.contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.cartList = response.data;
                $http.get($localStorage.contextPath + '/api/v1/cart/price')
                    .then(function (response) {
                        $scope.cartPrice = response.data;
                    });
            });

    };

    fillTable();

    $scope.isCartEmpty = function () {
        if ($scope.cartPrice > 0) {
            return false;
        } else {
            return true;
        }
    };

    $scope.decrementProductInCart = function (id) {
        $http.get($localStorage.contextPath + '/api/v1/cart/decrement/' + id)
            .then(function () {
                fillTable();
            });
    };

    $scope.deleteProductFromCart = function (id) {
        $http.delete($localStorage.contextPath + '/api/v1/cart/' + id)
            .then(function () {
                fillTable();
            });
    };

    $scope.addProductInCart = function (id) {
        $http.get($localStorage.contextPath + '/api/v1/cart/' + id).then(function () {
            fillTable();
        });
    };
});