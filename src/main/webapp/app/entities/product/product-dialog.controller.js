(function() {
	'use strict';
	angular
	.module('smarteshopApp')
	.controller('ProductDialogController', ProductDialogController);

	ProductDialogController.$inject = ['$timeout', '$scope', '$rootScope','$http','$state', '$stateParams',  '$uibModal','DataUtils','previousState','entity', 'Product', 'Sku', 
	                                   'RelatedProduct', 'Brand', 'Category','ProductAdditionalSku'];

	function ProductDialogController ($timeout, $scope, $rootScope, $http, $state,$stateParams, $uibModal, DataUtils,previousState, entity, Product, Sku, 
			RelatedProduct, Brand, Category, ProductAdditionalSku) {
		var vm = this;
		vm.product = entity;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.previousState = previousState.name;
		vm.save = save;
		vm.showRelatedProducts = showRelatedProducts;
		vm.editSKU = editSKU;
		vm.skus = Sku.query();
		vm.relatedproducts = RelatedProduct.query();
		vm.brands = Brand.query();
		vm.categories = Category.query();
		
		if(vm.product.id){
			ProductAdditionalSku.query({productId:entity.id, entityName:'AdditionalSku'}, function(additionalSkus){
				if(additionalSkus){
					vm.product.additionalSkus = additionalSkus;
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

		vm.datePickerOpenStatus.activeStartDate = false;
		vm.datePickerOpenStatus.activeEndDate = false;

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

		function showRelatedProducts(productId){
			$uibModal.open({
				templateUrl: 'app/entities/product/edit-relatedProducts.html',
				controller: 'EditRelatedProductsController',
				controllerAs: 'vm',
				backdrop: 'static',
				size: 'lg',
				resolve: {
					productId: productId
				}
			}).result.then(function($state) {
				//$state.go($state.name, {}, { reload: false });
			}, function($state) {
				
				//$state.go($state.name, {}, { reload: false });
			});
		};
		var unsubscribeProductOption = $rootScope.$on('smarteshopApp:relatedProductUpdate', function(event, result) {
            if(result){
            	vm.product.relatedProducts = result;
            }
       });
		$scope.$on('$destroy', unsubscribeProductOption);

		function editSKU(){
			$uibModal.open({
				templateUrl: 'app/entities/product/sku-variant.html',
				controller: 'SKUVariantController',
				controllerAs: 'vm',
				backdrop: 'static',
				size: 'lg',
				resolve: {
					entity: ['Product', function(Product) {
						return Product.get({id : $stateParams.id}).$promise;
					}]
				}
			}).result.then(function($state) {
				$state.go($state.name, {}, { reload: false });
			}, function($state) {
				$state.go($state.name);
			});

		};
		vm.showProductOptions = function showProductOptions(productId){
			$uibModal.open({
				templateUrl: 'app/entities/product/product-option-list.html',
				controller: 'ProductOptionsController',
				controllerAs: 'vm',
				backdrop: 'static',
				size: 'lg',
				resolve: {
					productId: productId
				}
			}).result.then(function($state) {
				//$state.go($state.name, {}, { reload: false });
			}, function($state) {
				
				//$state.go($state.name, {}, { reload: false });
			});
		};
		var unsubscribeProductOption = $rootScope.$on('smarteshopApp:productOptionUpdate', function(event, result) {
	            if(result){
	            	vm.product.productOptions = result;
	            }
	       });
	    $scope.$on('$destroy', unsubscribeProductOption);
	    
		vm.generateSkusByBatch = function generateSkusByBatch(productId){
			var url = "api/products/"+productId+"/generateSkusByBatch";
			$http.post(url);
		};

	}
})();
