(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('CustomerGroupDetailController', CustomerGroupDetailController);

    CustomerGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerGroup'];

    function CustomerGroupDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerGroup) {
        var vm = this;

        vm.customerGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:customerGroupUpdate', function(event, result) {
            vm.customerGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
