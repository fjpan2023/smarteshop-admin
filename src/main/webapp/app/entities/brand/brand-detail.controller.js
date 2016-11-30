(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('BrandDetailController', BrandDetailController);

    BrandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Brand'];

    function BrandDetailController($scope, $rootScope, $stateParams, previousState, entity, Brand) {
        var vm = this;

        vm.brand = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:brandUpdate', function(event, result) {
            vm.brand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
