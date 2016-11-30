(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('BrandDialogController', BrandDialogController);

    BrandDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', 'DataUtils', 'entity', 'Brand'];

    function BrandDialogController ($timeout, $scope, $state, $stateParams, DataUtils, entity, Brand) {
        var vm = this;
        vm.brand = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        $scope.options = {
				height: 300,
//				
//				toolbar: [
//				          // [groupName, [list of button]]
//				          ['style', ['bold', 'italic', 'underline', 'clear']],
//				          ['font', ['strikethrough', 'superscript', 'subscript']],
//				          ['fontsize', ['fontsize']],
//				          ['color', ['color']],
//				          ['para', ['ul', 'ol', 'paragraph']],
//				          ['height', ['height']]
//				        ],
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
