(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('MediaDetailController', MediaDetailController);

    MediaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Media'];

    function MediaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Media) {
        var vm = this;

        vm.media = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('smarteshopApp:mediaUpdate', function(event, result) {
            vm.media = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
