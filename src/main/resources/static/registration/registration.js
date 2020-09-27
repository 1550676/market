angular.module('app').controller('registrationController', function ($scope, $http, $location) {


    $scope.fieldErrors = [];
    $scope.tryToRegister = function (fieldErrors) {
        $scope.systemUser = {
            phone: "222",
            password: "222",
            matchingPassword: "222",
            firstName: "222",
            lastName: "222",
            email: "222"
        }};


        //     $http.post(contextPath + '/registration', $scope.systemUser)
        //         .then(function successCallback(response) {
        //             fieldErrors = response.data.fieldErrors;
        //             console.log(fieldErrors);
        //             console.log(response.data.fieldErrors);
        //             }, function errorCallback(response) {
        //             console.log(response.data);
        //             }
        //         );
        // };
});