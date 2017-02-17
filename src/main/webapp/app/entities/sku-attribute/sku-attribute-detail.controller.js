(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SkuAttributeDetailController', SkuAttributeDetailController);

    SkuAttributeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SkuAttribute', 'Sku'];

    function SkuAttributeDetailController($scope, $rootScope, $stateParams, previousState, entity, SkuAttribute, Sku) {
        var vm = this;

        vm.skuAttribute = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:skuAttributeUpdate', function(event, result) {
            vm.skuAttribute = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
