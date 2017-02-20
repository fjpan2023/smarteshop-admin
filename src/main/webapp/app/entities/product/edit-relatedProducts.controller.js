(function() {
	'use strict';

	angular
	.module('smarteshopApp')
	.controller('EditRelatedProductsController', EditRelatedProductsController);

	EditRelatedProductsController.$inject = ['$timeout', '$http','$scope', '$state', '$uibModal','$uibModalInstance', 'productId', 'Product'];

	function EditRelatedProductsController ($timeout, $http, $scope, $state,  $uibModal, $uibModalInstance, productId, Product) {
		var vm = this;
		vm.productId = productId;
		vm.clear = clear;
		vm.save = save;
		vm.options = [];
		vm.selectedProducts = [];
		Product.query( function(relatedProducts){
			if(relatedProducts){
				vm.relatedProducts = relatedProducts;
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
			var url = "api/products/"+productId+"/relatedProducts";
			$http.post(url,{optionIds:vm.selectedProducts})
			     .success(function onSaveSuccess (result) {
			    	 	$scope.$emit('smarteshopApp:relatedProductUpdate', result);
			    	 	$uibModalInstance.close(result);
			    	 	vm.isSaving = false;
			     	});
		}

		function onSaveError () {
			vm.isSaving = false;
		}
		
	vm.toggle = function toggle (item) {
			var idx = vm.selectedProducts.indexOf(item);
			if (idx > -1) {
				vm.selectedProducts.splice(idx, 1);
			}else {
				vm.selectedProducts.push(item);
			}
		};

	}
})();
