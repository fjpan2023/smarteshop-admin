(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductMediaController', ProductMediaController);

    ProductMediaController.$inject = ['$scope', '$state', '$uibModalInstance','DataUtils', 'Media', 'MediaSearch'];

    function ProductMediaController ($scope, $state, $uibModalInstance, DataUtils, Media, MediaSearch) {
        var vm = this;

        vm.media = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.save = save;

        loadAll();
    	vm.selectedMedia = [];

        function loadAll() {
            Media.query(function(result) {
                vm.media = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MediaSearch.query({query: vm.searchQuery}, function(result) {
                vm.media = result;
                vm.currentSearch = vm.searchQuery;
            });
        }
        
        function save () {
            vm.isSaving = true;
            alert(vm.selectedMedia);
//            if (vm.media.id !== null) {
//                Media.update(vm.media, onSaveSuccess, onSaveError);
//            } else {
//                Media.save(vm.media, onSaveSuccess, onSaveError);
//            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:productMediaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function clear() {
        	 $uibModalInstance.dismiss('cancel');
        } 
        vm.toggle = function toggle (item) {
			var idx = vm.selectedMedia.indexOf(item);
			if (idx > -1) {
				vm.selectedMedia.splice(idx, 1);
			}else {
				vm.selectedMedia.push(item);
			}
		};
      }
})();
