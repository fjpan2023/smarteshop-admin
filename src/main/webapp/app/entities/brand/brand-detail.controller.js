(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('BrandDetailController', BrandDetailController);

    BrandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Brand'];

    function BrandDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Brand) {
        var vm = this;

        vm.brand = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('smarteshopApp:brandUpdate', function(event, result) {
            vm.brand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
