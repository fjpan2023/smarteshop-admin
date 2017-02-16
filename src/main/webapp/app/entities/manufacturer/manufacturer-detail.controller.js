(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ManufacturerDetailController', ManufacturerDetailController);

    ManufacturerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Manufacturer'];

    function ManufacturerDetailController($scope, $rootScope, $stateParams, previousState, entity, Manufacturer) {
        var vm = this;

        vm.manufacturer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:manufacturerUpdate', function(event, result) {
            vm.manufacturer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
