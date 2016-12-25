(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('variant-value', {
            parent: 'entity',
            url: '/variant-value?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.variantValue.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variant-value/variant-values.html',
                    controller: 'VariantValueController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('variantValue');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('variant-value-detail', {
            parent: 'entity',
            url: '/variant-value/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.variantValue.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variant-value/variant-value-detail.html',
                    controller: 'VariantValueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('variantValue');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VariantValue', function($stateParams, VariantValue) {
                    return VariantValue.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'variant-value',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('variant-value-detail.edit', {
            parent: 'variant-value-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant-value/variant-value-dialog.html',
                    controller: 'VariantValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VariantValue', function(VariantValue) {
                            return VariantValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variant-value.new', {
            parent: 'variant-value',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant-value/variant-value-dialog.html',
                    controller: 'VariantValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                code: null,
                                displayOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('variant-value', null, { reload: 'variant-value' });
                }, function() {
                    $state.go('variant-value');
                });
            }]
        })
        .state('variant-value.edit', {
            parent: 'variant-value',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant-value/variant-value-dialog.html',
                    controller: 'VariantValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VariantValue', function(VariantValue) {
                            return VariantValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variant-value', null, { reload: 'variant-value' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variant-value.delete', {
            parent: 'variant-value',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant-value/variant-value-delete-dialog.html',
                    controller: 'VariantValueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VariantValue', function(VariantValue) {
                            return VariantValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variant-value', null, { reload: 'variant-value' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
