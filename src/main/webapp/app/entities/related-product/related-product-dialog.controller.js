(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('RelatedProductDialogController', RelatedProductDialogController);

    RelatedProductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RelatedProduct', 'Product'];

    function RelatedProductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RelatedProduct, Product) {
        var vm = this;

        vm.relatedProduct = entity;
        vm.clear = clear;
        vm.save = save;
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.relatedProduct.id !== null) {
                RelatedProduct.update(vm.relatedProduct, onSaveSuccess, onSaveError);
            } else {
                RelatedProduct.save(vm.relatedProduct, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:relatedProductUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
