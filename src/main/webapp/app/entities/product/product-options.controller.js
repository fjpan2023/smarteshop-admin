(function() {
	'use strict';

	angular
	.module('smarteshopApp')
	.controller('ProductOptionsController', ProductOptionsController);

	ProductOptionsController.$inject = ['$timeout', '$scope', '$state', '$stateParams', '$uibModal','$uibModalInstance', 'productId', 'ProductOption'];

	function ProductOptionsController ($timeout, $scope, $state, $stateParams, $uibModal, $uibModalInstance, productId, ProductOption) {
		var vm = this;
		vm.productId = productId;
		vm.clear = clear;
		vm.save = save;
		vm.options = [];
		ProductOption.query( function(options){
			if(options){
				vm.options = options;
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
				ProductOption.update(vm.variant, onSaveSuccess, onSaveError);
			} else {
				ProductOption.save(vm.variant, onSaveSuccess, onSaveError);
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
