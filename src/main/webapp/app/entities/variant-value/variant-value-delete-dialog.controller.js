(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('VariantValueDeleteController',VariantValueDeleteController);

    VariantValueDeleteController.$inject = ['$uibModalInstance', 'entity', 'VariantValue'];

    function VariantValueDeleteController($uibModalInstance, entity, VariantValue) {
        var vm = this;

        vm.variantValue = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VariantValue.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
