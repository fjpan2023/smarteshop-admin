(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SkuDialogController', SkuDialogController);

    SkuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sku', 'Product'];

    function SkuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sku, Product) {
        var vm = this;

        vm.sku = entity;
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
            if (vm.sku.id !== null) {
                Sku.update(vm.sku, onSaveSuccess, onSaveError);
            } else {
                Sku.save(vm.sku, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:skuUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
