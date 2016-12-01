(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductDialogController', ProductDialogController);

    ProductDialogController.$inject = ['$timeout', '$scope','$state', '$stateParams', 'previousState','entity', 'Product', 'Sku', 'RelatedProduct', 'Brand', 'Category'];

    function ProductDialogController ($timeout, $scope, $state,$stateParams, previousState, entity, Product, Sku, RelatedProduct, Brand, Category) {
        var vm = this;

        vm.product = entity;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.previousState = previousState.name;
        vm.save = save;
        vm.skus = Sku.query();
        vm.relatedproducts = RelatedProduct.query();
        vm.brands = Brand.query();
        vm.categories = Category.query();
        

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

//        function clear () {
//            $uibModalInstance.dismiss('cancel');
//        }

        function save () {
            vm.isSaving = true;
            if (vm.product.id !== null) {
                Product.update(vm.product, onSaveSuccess, onSaveError);
            } else {
                Product.save(vm.product, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:productUpdate', result);
            vm.isSaving = false;
            $state.go('product');
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fromDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
