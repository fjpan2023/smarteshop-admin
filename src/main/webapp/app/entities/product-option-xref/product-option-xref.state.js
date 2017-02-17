(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('product-option-xref', {
            parent: 'entity',
            url: '/product-option-xref',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.productOptionXref.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-option-xref/product-option-xrefs.html',
                    controller: 'ProductOptionXrefController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productOptionXref');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('product-option-xref-detail', {
            parent: 'entity',
            url: '/product-option-xref/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.productOptionXref.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-option-xref/product-option-xref-detail.html',
                    controller: 'ProductOptionXrefDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productOptionXref');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProductOptionXref', function($stateParams, ProductOptionXref) {
                    return ProductOptionXref.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'product-option-xref',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('product-option-xref-detail.edit', {
            parent: 'product-option-xref-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-option-xref/product-option-xref-dialog.html',
                    controller: 'ProductOptionXrefDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductOptionXref', function(ProductOptionXref) {
                            return ProductOptionXref.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-option-xref.new', {
            parent: 'product-option-xref',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-option-xref/product-option-xref-dialog.html',
                    controller: 'ProductOptionXrefDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('product-option-xref', null, { reload: 'product-option-xref' });
                }, function() {
                    $state.go('product-option-xref');
                });
            }]
        })
        .state('product-option-xref.edit', {
            parent: 'product-option-xref',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-option-xref/product-option-xref-dialog.html',
                    controller: 'ProductOptionXrefDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductOptionXref', function(ProductOptionXref) {
                            return ProductOptionXref.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-option-xref', null, { reload: 'product-option-xref' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-option-xref.delete', {
            parent: 'product-option-xref',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-option-xref/product-option-xref-delete-dialog.html',
                    controller: 'ProductOptionXrefDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProductOptionXref', function(ProductOptionXref) {
                            return ProductOptionXref.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-option-xref', null, { reload: 'product-option-xref' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
