(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductAttributeDialogController', ProductAttributeDialogController);

    ProductAttributeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductAttribute', 'Product'];

    function ProductAttributeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductAttribute, Product) {
        var vm = this;

        vm.productAttribute = entity;
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
            if (vm.productAttribute.id !== null) {
                ProductAttribute.update(vm.productAttribute, onSaveSuccess, onSaveError);
            } else {
                ProductAttribute.save(vm.productAttribute, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:productAttributeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
