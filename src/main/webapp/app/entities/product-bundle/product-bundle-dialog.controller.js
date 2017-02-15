(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductBundleDialogController', ProductBundleDialogController);

    ProductBundleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductBundle'];

    function ProductBundleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductBundle) {
        var vm = this;

        vm.productBundle = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.productBundle.id !== null) {
                ProductBundle.update(vm.productBundle, onSaveSuccess, onSaveError);
            } else {
                ProductBundle.save(vm.productBundle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:productBundleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
