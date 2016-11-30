(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('RelatedProductDetailController', RelatedProductDetailController);

    RelatedProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RelatedProduct', 'Product'];

    function RelatedProductDetailController($scope, $rootScope, $stateParams, previousState, entity, RelatedProduct, Product) {
        var vm = this;

        vm.relatedProduct = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:relatedProductUpdate', function(event, result) {
            vm.relatedProduct = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
