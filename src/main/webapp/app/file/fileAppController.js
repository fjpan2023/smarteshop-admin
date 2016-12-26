(function() {
	'use strict';

	angular
	.module('smarteshopApp')
	.controller('fileController', fileController);

	fileController.$inject = ['$timeout', '$scope','$state', '$stateParams'];

	function fileController ($scope, $timeout) {
	    $scope.partialDownloadLink = 'http://localhost:8080/download?filename=';
	    $scope.filename = '';
	    $scope.uploadFile = function() {
	        $scope.processDropzone();
	    };

	    $scope.reset = function() {
	        $scope.resetDropzone();
	    };
	}

})();

