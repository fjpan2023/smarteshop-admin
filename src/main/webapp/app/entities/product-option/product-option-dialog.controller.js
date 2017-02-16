(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionDialogController', ProductOptionDialogController);

    ProductOptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductOption', 'Product'];

    function ProductOptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductOption, Product) {
        var vm = this;

        vm.productOption = entity;
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
            if (vm.productOption.id !== null) {
                ProductOption.update(vm.productOption, onSaveSuccess, onSaveError);
            } else {
                ProductOption.save(vm.productOption, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:productOptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
