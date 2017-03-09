(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('FulfillmentGroupItemDeleteController',FulfillmentGroupItemDeleteController);

    FulfillmentGroupItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'FulfillmentGroupItem'];

    function FulfillmentGroupItemDeleteController($uibModalInstance, entity, FulfillmentGroupItem) {
        var vm = this;

        vm.fulfillmentGroupItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FulfillmentGroupItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
