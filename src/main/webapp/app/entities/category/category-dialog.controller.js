(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('CategoryDialogController', CategoryDialogController);

    CategoryDialogController.$inject = ['$timeout', '$scope', '$state','$stateParams','previousState', 'entity', 'Category', 'Product'];

    function CategoryDialogController ($timeout, $scope, $state, $stateParams, previousState, entity, Category, Product) {
        var vm = this;

        vm.category = entity;
        vm.save = save;
        vm.products = Product.query();
        vm.previousState = previousState.name;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });


        function save () {
            vm.isSaving = true;
            if (vm.category.id !== null) {
                Category.update(vm.category, onSaveSuccess, onSaveError);
            } else {
                Category.save(vm.category, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:categoryUpdate', result);
            vm.isSaving = false;
            $state.go(vm.previousState);
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
