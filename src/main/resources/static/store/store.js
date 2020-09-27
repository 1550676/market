angular.module('app').controller('storeController', function ($scope, $http, $localStorage) {
    const path = $localStorage.contextPath + '/api/v1/products';
    $scope.categories = $localStorage.categories;

    // формируем фильтры
    $scope.filters = {
        subTitle: '',
        minPrice: '',
        maxPrice: '',
        categoriesIds: []
    }
    // добавляем и удаляем категорию для фильтрации при нажатии на нее
    $scope.selectCategory = function (categoryId) {
        if ($scope.filters.categoriesIds.indexOf(categoryId) < 0)
            $scope.filters.categoriesIds.push(categoryId);
        else
            $scope.filters.categoriesIds.splice($scope.filters.categoriesIds.indexOf(categoryId), 1);
    };
    // заполняем таблицу товаров с учетом фильтров
    let fillTable = function () {
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

    $scope.submitFilters = function () {
        console.log($scope.filterStr);
        fillTable();
    };

    $scope.cancelFilters = function () {
        window.location.reload();
    };

    $scope.deleteProduct = function (id) {
        $http.delete(path + '/' + id).then(function () {
            window.location.reload(true);
        });
    };

    $scope.addProductInCart = function (id) {
        $http.get($localStorage.contextPath + '/api/v1/cart/' + id).then(function () {
        });
    };

});