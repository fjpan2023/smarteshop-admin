(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('BrandDialogController', BrandDialogController);

    BrandDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams','previousState', 'DataUtils', 'entity', 'Brand'];

    function BrandDialogController ($timeout, $scope, $state, $stateParams, previousState, DataUtils, entity, Brand) {
        var vm = this;
        vm.brand = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.previousState = previousState.name;
        $scope.summernoteroptions = {
				height: 300,
		};
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            if (vm.brand.id !== null) {
                Brand.update(vm.brand, onSaveSuccess, onSaveError);
            } else {
                Brand.save(vm.brand, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:brandUpdate', result);
            vm.isSaving = false;
            $state.go('brand');
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.setImage = function ($file, brand) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        brand.image = base64Data;
                        brand.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
