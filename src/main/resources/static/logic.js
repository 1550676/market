var app = angular.module('app', ['ngRoute', 'ngStorage']);
var contextPath = 'http://localhost:8189/market'

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'homepage.html'
        })
        .when('/shop', {
            templateUrl: 'shop.html',
            controller: 'shopController'
        })
        .when('/create_or_update_product', {
            templateUrl: 'create_or_update_product.html',
            controller: 'createOrUpdateProductController'
        })
});

app.controller('authController', function ($scope, $http, $localStorage) {
    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function (response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = { username: $scope.user.username, token: response.data.token };
                    window.location.reload(true);
                }
            });
    };

    $scope.tryToLogout = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
        window.location.reload(true);
    };
});

app.controller('shopController', function ($scope, $http, $localStorage)  {
    console.log($localStorage.currentUser);
    if ($localStorage.currentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    }
    const path = contextPath + '/api/v1/products';
    // запрашиваем категории
    $http.get("http://localhost:8189/market/api/v1/categories")
        .then(function (response) {
            $scope.categories = response.data;
        });
    // формируем фильтры
    $scope.filters = {
        subTitle : '',
        minPrice : '',
        maxPrice : '',
        categoriesIds : []
    }
    // добавляем и удаляем категорию для фильтрации при нажатии на нее
    $scope.selectCategory = function (categoryId) {
        if ($scope.filters.categoriesIds.indexOf(categoryId) < 0)
            $scope.filters.categoriesIds.push(categoryId);
        else
            $scope.filters.categoriesIds.splice($scope.filters.categoriesIds.indexOf(categoryId), 1);
    };
    // заполняем таблицу товаров с учетом фильтров
    fillTable = function () {
        if ($scope.filters.subTitle === null)
            $scope.filters.subTitle = '';
        if ($scope.filters.maxPrice === null)
            $scope.filters.maxPrice = '';
        if ($scope.filters.minPrice === null)
            $scope.filters.minPrice = '';
        $scope.filterStr = '?sub_title=' + $scope.filters.subTitle + '&max_price=' + $scope.filters.maxPrice + '&min_price=' + $scope.filters.minPrice;
        $scope.categoryFilter = '';
        angular.forEach($scope.filters.categoriesIds, function (item) {
            $scope.categoryFilter = $scope.categoryFilter + '&category=' + item;
        });
        $http.get(path + $scope.filterStr + $scope.categoryFilter)
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };
    fillTable();

    $scope.deleteProduct = function (id) {
        $http.delete(path + '/' + id).then(function () {
            window.location.reload(true);
        });
    };

    $scope.submitFilters = function () {
        console.log($scope.filterStr);
        fillTable();
    };

    $scope.cancelFilters = function () {
        window.location.reload();
    };

});


app.controller('productEditController', function ($scope, $http, $routeParams, $localStorage) {
    if ($localStorage.currentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    }
    const path = contextPath + '/api/v1/products';
        // запрашиваем категории
        $http.get("http://localhost:8189/market/api/v1/categories")
            .then(function (response) {
                $scope.categories = response.data;
            });
        // если внесение изменений в товар
        if ($routeParams.id != null) {
            $http.get(path + '/' + $routeParams.id).then(function (response) {
                console.log(response);

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
                    window.location.href = contextPath + '/index.html#!/shop';
                });
            }
        };
    }
);