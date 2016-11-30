(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('BrandDialogController', BrandDialogController);

    BrandDialogController.$inject = ['$timeout', '$scope', '$stateParams', 'DataUtils', 'entity', 'Brand'];

    function BrandDialogController ($timeout, $scope, $stateParams, DataUtils, entity, Brand) {
        var vm = this;

        vm.brand = entity;
//        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

//        function clear () {
//            $uibModalInstance.dismiss('cancel');
//        }

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
           // $uibModalInstance.close(result);
            vm.isSaving = false;
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
