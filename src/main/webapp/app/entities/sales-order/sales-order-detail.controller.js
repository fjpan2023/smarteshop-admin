(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SalesOrderDetailController', SalesOrderDetailController);

    SalesOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SalesOrder'];

    function SalesOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, SalesOrder) {
        var vm = this;

        vm.salesOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:salesOrderUpdate', function(event, result) {
            vm.salesOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
