(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('OrderPaymentDeleteController',OrderPaymentDeleteController);

    OrderPaymentDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderPayment'];

    function OrderPaymentDeleteController($uibModalInstance, entity, OrderPayment) {
        var vm = this;

        vm.orderPayment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderPayment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
