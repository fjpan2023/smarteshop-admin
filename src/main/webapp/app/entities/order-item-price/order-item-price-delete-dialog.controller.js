(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('OrderItemPriceDeleteController',OrderItemPriceDeleteController);

    OrderItemPriceDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderItemPrice'];

    function OrderItemPriceDeleteController($uibModalInstance, entity, OrderItemPrice) {
        var vm = this;

        vm.orderItemPrice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderItemPrice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
