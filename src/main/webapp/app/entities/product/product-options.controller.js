(function() {
	'use strict';

	angular
	.module('smarteshopApp')
	.controller('ProductOptionsController', ProductOptionsController);

	ProductOptionsController.$inject = ['$timeout','$http', '$scope', '$state', '$uibModal','$uibModalInstance', 'productId', 'ProductOption'];

	function ProductOptionsController ($timeout, $http, $scope, $state,  $uibModal, $uibModalInstance, productId, ProductOption) {
		var vm = this;
		vm.productId = productId;
		vm.clear = clear;
		vm.save = save;
		vm.options = [];
		vm.selectedOptions = [];
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
			var url = "api/products/"+productId+"/productOptions";
			$http.post(url,{optionIds:vm.selectedOptions})
			     .success(function onSaveSuccess (result) {
			    	 	$scope.$emit('smarteshopApp:productOptionUpdate', result);
			    	 	$uibModalInstance.close(result);
			    	 	vm.isSaving = false;
			     	});
		}

		function onSaveError () {
			vm.isSaving = false;
		}
		
		vm.toggle = function toggle (item) {
			var idx = vm.selectedOptions.indexOf(item);
			if (idx > -1) {
				vm.selectedOptions.splice(idx, 1);
			}else {
				vm.selectedOptions.push(item);
			}
		};

	}
})();
