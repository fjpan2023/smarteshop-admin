(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductBundleDeleteController',ProductBundleDeleteController);

    ProductBundleDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductBundle'];

    function ProductBundleDeleteController($uibModalInstance, entity, ProductBundle) {
        var vm = this;

        vm.productBundle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProductBundle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
