(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ItemOrderDeleteController',ItemOrderDeleteController);

    ItemOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'ItemOrder'];

    function ItemOrderDeleteController($uibModalInstance, entity, ItemOrder) {
        var vm = this;

        vm.itemOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ItemOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
