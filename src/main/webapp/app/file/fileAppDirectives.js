function dropzone() {

	return function(scope, element, attrs) {

		var config = {
				url: 'api/attachments',
				maxFilesize: 10,
				paramName: "uploadfile",
				maxThumbnailFilesize: 1,
				parallelUploads: 5,
				autoProcessQueue: true
		};

		var eventHandlers = {

				'sending' : function(file, xhr, formData) {
					// Will send the filesize along with the file as POST data.
					formData.append("filesize", file.size);
				},
				'addedfile': function(file) {
//					scope.file = file;
//					if (this.files[1]!=null) {
//					this.removeFile(this.files[0]);
//					}
//					scope.$apply(function() {
//					scope.fileAdded = true;
//					});
				},

				'success': function (file, response) {
					alert("success  ...");
				}
		};

		dropzone = new Dropzone(element[0], config);

		angular.forEach(eventHandlers, function(handler, event) {
			dropzone.on(event, handler);
		});

		scope.processDropzone = function() {
			dropzone.processQueue();
		};

		scope.resetDropzone = function() {
			dropzone.removeAllFiles();
		}
	}
}
angular.module('smarteshopApp').directive('dropzone', dropzone);