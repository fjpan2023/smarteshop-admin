(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('BrandDetailController', BrandDetailController);

    BrandDetailController.$inject = ['$scope','$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Brand','Attachment'];

    function BrandDetailController($scope,$rootScope, $stateParams, previousState, DataUtils, entity, Brand, Attachment) {
        var vm = this;

        vm.brand = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        Attachment.query({entityId:entity.id, entityName:'Brand'}, function(attachments){
        	if(attachments){
        		vm.image = attachments[0];
        	}
        	
        });
        

        var unsubscribe = $rootScope.$on('smarteshopApp:brandUpdate', function(event, result) {
            vm.brand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
