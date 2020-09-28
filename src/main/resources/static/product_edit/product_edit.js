angular.module('app').controller('productEditController', function ($scope, $http, $routeParams, $localStorage, $location) {
    const path = $localStorage.contextPath + '/api/v1/products';
    $scope.categories = $localStorage.categories;

    // если внесение изменений в товар
    if ($routeParams.id != null) {
        $http.get(path + '/' + $routeParams.id).then(function (response) {
            $scope.productForm = response.data;
            $scope.productFormCategoriesId = [];
            angular.forEach($scope.productForm.categories, function (item) {
                $scope.productFormCategoriesId.push(item.id);
            });
            // настройка галочек чекбокса
            angular.forEach($scope.categories, function (item) {
                if ($scope.productFormCategoriesId.indexOf(item.id) >= 0) {
                    item.selected = 'yes';
                }
            });
        })
        // если создание нового товара
    } else {
        $scope.productForm = {
            title: "",
            price: "",
            categories: []
        }
        $scope.productFormCategoriesId = [];
    }
    $scope.selectedCategories = [];

    $scope.productFormCategoryAdd = function (category) {
        if ($scope.productFormCategoriesId.indexOf(category.id) < 0) {
            $scope.productForm.categories.push(category)
            $scope.productFormCategoriesId.push(category.id)
        } else {
            $scope.productForm.categories.splice($scope.productFormCategoriesId.indexOf(category.id), 1);
            $scope.productFormCategoriesId.splice($scope.productFormCategoriesId.indexOf(category.id), 1);
        }
    };

    $scope.createOrUpdateProduct = function () {
        if ($scope.productForm.id == null) {
            $http.post(path, $scope.productForm).then(function () {
                window.location.reload(true);
            });
        } else {
            $http.put(path, $scope.productForm).then(function (response) {
                $location.path("/store");
            });
        }
    };
});