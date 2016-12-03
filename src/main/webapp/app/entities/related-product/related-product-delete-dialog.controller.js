(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('RelatedProductDeleteController',RelatedProductDeleteController);

    RelatedProductDeleteController.$inject = ['$uibModalInstance', 'entity', 'RelatedProduct'];

    function RelatedProductDeleteController($uibModalInstance, entity, RelatedProduct) {
        var vm = this;

        vm.relatedProduct = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RelatedProduct.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
