(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionXrefDialogController', ProductOptionXrefDialogController);

    ProductOptionXrefDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductOptionXref'];

    function ProductOptionXrefDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductOptionXref) {
        var vm = this;

        vm.productOptionXref = entity;
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
            if (vm.productOptionXref.id !== null) {
                ProductOptionXref.update(vm.productOptionXref, onSaveSuccess, onSaveError);
            } else {
                ProductOptionXref.save(vm.productOptionXref, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:productOptionXrefUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
