(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionDeleteController',ProductOptionDeleteController);

    ProductOptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductOption'];

    function ProductOptionDeleteController($uibModalInstance, entity, ProductOption) {
        var vm = this;

        vm.productOption = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProductOption.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
