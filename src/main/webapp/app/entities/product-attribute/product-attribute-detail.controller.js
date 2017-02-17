(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductAttributeDetailController', ProductAttributeDetailController);

    ProductAttributeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductAttribute', 'Product'];

    function ProductAttributeDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductAttribute, Product) {
        var vm = this;

        vm.productAttribute = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:productAttributeUpdate', function(event, result) {
            vm.productAttribute = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
