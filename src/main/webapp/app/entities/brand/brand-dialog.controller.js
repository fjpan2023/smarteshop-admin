(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('BrandDialogController', BrandDialogController);

    BrandDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams','previousState', 'DataUtils', 'entity', 'Brand','Attachment'];
    function BrandDialogController ($timeout, $scope, $state, $stateParams, previousState, DataUtils, entity, Brand, Attachment) {

        var vm = this;
        vm.brand = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.previousState = previousState.name;
        vm.save = save;
        var list = Attachment.query({entityId:entity.id, entityName:'Brand'});
        if(list){
        	vm.image = list[0];
        }
        
        
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
                	var attachment = new Object();
                	attachment.entityName = 'Brand';
                	attachment.entityId = brand.id;
                	attachment.name = $file.name;
                	attachment.content = base64Data;
                	attachment.contentType = $file.type;
                	Attachment.save(attachment);       	 
                    $scope.$apply(function() {
                        brand.image = base64Data;
                        brand.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
