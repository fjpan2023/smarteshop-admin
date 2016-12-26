(function() {
	'use strict';

	angular
	.module('smarteshopApp')
	.controller('SKUVariantController', SKUVariantController);

	SKUVariantController.$inject = ['$timeout', '$scope', '$state', '$stateParams', '$uibModal','$uibModalInstance', 'entity', 'Variant'];

	function SKUVariantController ($timeout, $scope, $state, $stateParams, $uibModal, $uibModalInstance, entity, Variant) {
		var vm = this;
		vm.variant = entity;
		vm.clear = clear;
		vm.save = save;
		vm.variants = [];
		Variant.query( function(variants){
			if(variants){
				vm.variants = variants;
			}        	
		});


		$timeout(function (){
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear () {
			$uibModalInstance.dismiss('cancel');
		}
		function save () {
			vm.isSaving = true;
			if (vm.variant.id !== null) {
				Variant.update(vm.variant, onSaveSuccess, onSaveError);
			} else {
				Variant.save(vm.variant, onSaveSuccess, onSaveError);
			}
		}
		function onSaveSuccess (result) {
			$scope.$emit('smarteshopApp:variantUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError () {
			vm.isSaving = false;
		}
	}
})();
