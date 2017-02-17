(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductAttributeDeleteController',ProductAttributeDeleteController);

    ProductAttributeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductAttribute'];

    function ProductAttributeDeleteController($uibModalInstance, entity, ProductAttribute) {
        var vm = this;

        vm.productAttribute = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProductAttribute.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
