(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SalesOrderDeleteController',SalesOrderDeleteController);

    SalesOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'SalesOrder'];

    function SalesOrderDeleteController($uibModalInstance, entity, SalesOrder) {
        var vm = this;

        vm.salesOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SalesOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
