(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('AttachmentDetailController', AttachmentDetailController);

    AttachmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Attachment'];

    function AttachmentDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Attachment) {
        var vm = this;

        vm.attachment = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('smarteshopApp:attachmentUpdate', function(event, result) {
            vm.attachment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
