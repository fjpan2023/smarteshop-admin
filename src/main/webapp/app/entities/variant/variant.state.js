(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('variant', {
            parent: 'entity',
            url: '/variant?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.variant.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variant/variants.html',
                    controller: 'VariantController',
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
                    $translatePartialLoader.addPart('variant');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('variant-detail', {
            parent: 'entity',
            url: '/variant/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.variant.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variant/variant-detail.html',
                    controller: 'VariantDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('variant');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Variant', function($stateParams, Variant) {
                    return Variant.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'variant',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('variant-detail.edit', {
            parent: 'variant-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant/variant-dialog.html',
                    controller: 'VariantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Variant', function(Variant) {
                            return Variant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variant.new', {
            parent: 'variant',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant/variant-dialog.html',
                    controller: 'VariantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                displayOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('variant', null, { reload: 'variant' });
                }, function() {
                    $state.go('variant');
                });
            }]
        })
        .state('variant.edit', {
            parent: 'variant',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant/variant-dialog.html',
                    controller: 'VariantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Variant', function(Variant) {
                            return Variant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variant', null, { reload: 'variant' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variant.delete', {
            parent: 'variant',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant/variant-delete-dialog.html',
                    controller: 'VariantDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Variant', function(Variant) {
                            return Variant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variant', null, { reload: 'variant' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
