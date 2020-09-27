angular.module('app').controller('orderController', function ($scope, $http, $location, $localStorage) {


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

    $scope.tryToOrderConfirm = function () {
        $http.post($localStorage.contextPath + '/api/v1/order', $scope.address)
            .then(function (response) {
                $location.path('/about');
                window.alert(response.data.message);
            });
    };

});