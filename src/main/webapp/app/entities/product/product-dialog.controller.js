(function() {
	'use strict';

	angular
	.module('smarteshopApp')
	.controller('ProductDialogController', ProductDialogController);

	ProductDialogController.$inject = ['$timeout', '$scope','$state', '$stateParams', 'DataUtils','previousState','entity', 'Product', 'Sku', 
	                                   'RelatedProduct', 'Brand', 'Category','Attachment'];

	function ProductDialogController ($timeout, $scope, $state,$stateParams, DataUtils,previousState, entity, Product, Sku, 
			RelatedProduct, Brand, Category, Attachment) {
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
		vm.product.images= [];
		if(vm.product.id){
			Attachment.query({entityId:entity.id, entityName:'Product'}, function(attachments){
				if(attachments){
					vm.product.images = attachments;
				}        	
			});
		}
		$scope.summernoteroptions = {
				height: 300,
		};

		$timeout(function (){
			angular.element('.form-group:eq(1)>input').focus();
		});

		function save () {
			vm.isSaving = true;
			if (vm.product.id) {
				vm.product.attachments = [];
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

		vm.setImage = function ($file, product) {
			if ($file && $file.$error === 'pattern') {
				return;
			}
			if ($file) {            	
				DataUtils.toBase64($file, function(base64Data) {
					var attachment = new Object();
					attachment.entityName = 'Product';
					attachment.name = $file.name;
					attachment.content = base64Data;
					attachment.contentType = $file.type;
					if(vm.product.id){
						Attachment.save(attachment);       	 
					}
					$scope.$apply(function() {
						vm.product.images.push(attachment);
					});

				});
			}
		};
		
	function showNotRelatedProducts(ids){
		
		
	}

	}
})();
