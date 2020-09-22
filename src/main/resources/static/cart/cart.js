angular.module('app').controller('cartController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market';

    fillTable = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.cartList = response.data;
                $http.get(contextPath + '/api/v1/cart/price')
                    .then(function (response) {
                        $scope.cartPrice = response.data;
                        console.log($scope.cartPrice);
                    });
            });

    };

    fillTable();

    $scope.isCartEmpty = function () {
        if ($scope.cartPrice === 0) {
            return true;
        } else {
            return false;
        }
    };

    $scope.decrementProductInCart = function (id) {
        $http.get(contextPath + '/api/v1/cart/decrement/' + id)
            .then(function () {
                fillTable();
            });
    };

    $scope.deleteProductFromCart = function (id) {
        $http.delete(contextPath + '/api/v1/cart/' + id)
            .then(function () {
                fillTable();
            });
    };

    $scope.addProductInCart = function (id) {
        $http.get(contextPath + '/api/v1/cart/' + id).then(function () {
            fillTable();
        });
    };
});