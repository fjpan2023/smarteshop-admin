(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SkuProductOptionValueXrefDetailController', SkuProductOptionValueXrefDetailController);

    SkuProductOptionValueXrefDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SkuProductOptionValueXref', 'ProductOptionValue'];

    function SkuProductOptionValueXrefDetailController($scope, $rootScope, $stateParams, previousState, entity, SkuProductOptionValueXref, ProductOptionValue) {
        var vm = this;

        vm.skuProductOptionValueXref = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:skuProductOptionValueXrefUpdate', function(event, result) {
            vm.skuProductOptionValueXref = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
