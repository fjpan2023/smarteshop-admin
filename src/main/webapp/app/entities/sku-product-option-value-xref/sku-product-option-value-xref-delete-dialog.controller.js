(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SkuProductOptionValueXrefDeleteController',SkuProductOptionValueXrefDeleteController);

    SkuProductOptionValueXrefDeleteController.$inject = ['$uibModalInstance', 'entity', 'SkuProductOptionValueXref'];

    function SkuProductOptionValueXrefDeleteController($uibModalInstance, entity, SkuProductOptionValueXref) {
        var vm = this;

        vm.skuProductOptionValueXref = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SkuProductOptionValueXref.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
