(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('OrderItemPriceDetailController', OrderItemPriceDetailController);

    OrderItemPriceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderItemPrice'];

    function OrderItemPriceDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderItemPrice) {
        var vm = this;

        vm.orderItemPrice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:orderItemPriceUpdate', function(event, result) {
            vm.orderItemPrice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
