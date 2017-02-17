'use strict';

describe('Controller Tests', function() {

    describe('SkuProductOptionValueXref Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSkuProductOptionValueXref, MockProductOptionValue;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSkuProductOptionValueXref = jasmine.createSpy('MockSkuProductOptionValueXref');
            MockProductOptionValue = jasmine.createSpy('MockProductOptionValue');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SkuProductOptionValueXref': MockSkuProductOptionValueXref,
                'ProductOptionValue': MockProductOptionValue
            };
            createController = function() {
                $injector.get('$controller')("SkuProductOptionValueXrefDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smarteshopApp:skuProductOptionValueXrefUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
