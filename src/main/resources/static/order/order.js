angular.module('app').controller('orderController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market';

    fillTable = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.cartList = response.data;
                $http.get(contextPath + '/api/v1/cart/price')
                    .then(function (response) {
                        $scope.cartPrice = response.data;
                    });
            });

    };
    fillTable();

    $scope.tryToOrderConfirm = function () {
        $http.post(contextPath + '/api/v1/order', $scope.address)
            .then(function successCallback(response) {
                    $location.path('/about');
                    window.alert(response.data.message);
                }, function errorCallback(response) {
                    window.alert(response.data.message);
                }
            );
    };

});