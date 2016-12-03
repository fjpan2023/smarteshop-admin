(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'Sku', 'RelatedProduct', 'Brand', 'Category'];

    function ProductDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, Sku, RelatedProduct, Brand, Category) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
