(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('VariantDeleteController',VariantDeleteController);

    VariantDeleteController.$inject = ['$uibModalInstance', 'entity', 'Variant'];

    function VariantDeleteController($uibModalInstance, entity, Variant) {
        var vm = this;

        vm.variant = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Variant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
