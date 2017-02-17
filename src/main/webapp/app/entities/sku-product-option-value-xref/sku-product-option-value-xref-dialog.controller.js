(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SkuProductOptionValueXrefDialogController', SkuProductOptionValueXrefDialogController);

    SkuProductOptionValueXrefDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SkuProductOptionValueXref', 'ProductOptionValue'];

    function SkuProductOptionValueXrefDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SkuProductOptionValueXref, ProductOptionValue) {
        var vm = this;

        vm.skuProductOptionValueXref = entity;
        vm.clear = clear;
        vm.save = save;
        vm.productoptionvalues = ProductOptionValue.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.skuProductOptionValueXref.id !== null) {
                SkuProductOptionValueXref.update(vm.skuProductOptionValueXref, onSaveSuccess, onSaveError);
            } else {
                SkuProductOptionValueXref.save(vm.skuProductOptionValueXref, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:skuProductOptionValueXrefUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
