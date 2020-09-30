angular.module('app').controller('registrationController', function ($scope, $http, $localStorage) {
    const path = $localStorage.contextPath + '/registration';
    let cleanErrors = function () {
        if ($localStorage.fieldErrors) {
            angular.forEach($localStorage.fieldErrors, function (item) {
                delete $scope[item.field];
            });
            delete $localStorage.fieldErrors;
        }
    };

    $scope.systemUser = {};

    $scope.tryToRegister = function () {
        cleanErrors();
        $http.post(path, $scope.systemUser)
            .then(function successCallback(response) {
                    if (response.data.fieldErrors) {
                        $localStorage.fieldErrors = response.data.fieldErrors;
                        angular.forEach($localStorage.fieldErrors, function (item) {
                            if ($scope[item.field]){
                                $scope[item.field] += '\n ' + item.message;
                            } else {
                                $scope[item.field] = '\n ' + item.message;
                            }
                        });
                    } else {
                        window.alert("You have successfully registered! Please, enter your username and password in authentication form.");
                    }
                }, function errorCallback(response) {
                    console.log(response.data);
                }
            )
    }


});