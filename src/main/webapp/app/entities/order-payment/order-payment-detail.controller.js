(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('OrderPaymentDetailController', OrderPaymentDetailController);

    OrderPaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderPayment'];

    function OrderPaymentDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderPayment) {
        var vm = this;

        vm.orderPayment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:orderPaymentUpdate', function(event, result) {
            vm.orderPayment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
