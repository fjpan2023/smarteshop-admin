(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SkuAttributeDialogController', SkuAttributeDialogController);

    SkuAttributeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SkuAttribute', 'Sku'];

    function SkuAttributeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SkuAttribute, Sku) {
        var vm = this;

        vm.skuAttribute = entity;
        vm.clear = clear;
        vm.save = save;
        vm.skus = Sku.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.skuAttribute.id !== null) {
                SkuAttribute.update(vm.skuAttribute, onSaveSuccess, onSaveError);
            } else {
                SkuAttribute.save(vm.skuAttribute, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:skuAttributeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
