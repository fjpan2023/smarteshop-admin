(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionXrefDeleteController',ProductOptionXrefDeleteController);

    ProductOptionXrefDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductOptionXref'];

    function ProductOptionXrefDeleteController($uibModalInstance, entity, ProductOptionXref) {
        var vm = this;

        vm.productOptionXref = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProductOptionXref.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
