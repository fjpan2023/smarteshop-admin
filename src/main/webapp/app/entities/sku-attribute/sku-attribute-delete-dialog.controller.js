(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SkuAttributeDeleteController',SkuAttributeDeleteController);

    SkuAttributeDeleteController.$inject = ['$uibModalInstance', 'entity', 'SkuAttribute'];

    function SkuAttributeDeleteController($uibModalInstance, entity, SkuAttribute) {
        var vm = this;

        vm.skuAttribute = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SkuAttribute.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
