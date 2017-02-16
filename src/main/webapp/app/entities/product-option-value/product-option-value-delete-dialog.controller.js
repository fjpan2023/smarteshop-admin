(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionValueDeleteController',ProductOptionValueDeleteController);

    ProductOptionValueDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductOptionValue'];

    function ProductOptionValueDeleteController($uibModalInstance, entity, ProductOptionValue) {
        var vm = this;

        vm.productOptionValue = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProductOptionValue.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
