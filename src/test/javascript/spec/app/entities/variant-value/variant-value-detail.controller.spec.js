'use strict';

describe('Controller Tests', function() {

    describe('VariantValue Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVariantValue, MockVariant;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVariantValue = jasmine.createSpy('MockVariantValue');
            MockVariant = jasmine.createSpy('MockVariant');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'VariantValue': MockVariantValue,
                'Variant': MockVariant
            };
            createController = function() {
                $injector.get('$controller')("VariantValueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smarteshopApp:variantValueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
