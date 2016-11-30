(function() {
	'use strict';

	angular
	.module('smarteshopApp')
	.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider
		.state('brand', {
			parent: 'entity',
			url: '/brand?page&sort&search',
			data: {
				authorities: ['ROLE_USER'],
				pageTitle: 'smarteshopApp.brand.home.title'
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/brand/brands.html',
					controller: 'BrandController',
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
					$translatePartialLoader.addPart('brand');
					$translatePartialLoader.addPart('statusEnum');
					$translatePartialLoader.addPart('global');
					return $translate.refresh();
				}]
			}
		})
		.state('brand-detail', {
			parent: 'entity',
			url: '/brand/{id}',
			data: {
				authorities: ['ROLE_USER'],
				pageTitle: 'smarteshopApp.brand.detail.title'
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/brand/brand-detail.html',
					controller: 'BrandDetailController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('brand');
					$translatePartialLoader.addPart('statusEnum');
					return $translate.refresh();
				}],
				entity: ['$stateParams', 'Brand', function($stateParams, Brand) {
					return Brand.get({id : $stateParams.id}).$promise;
				}],
				previousState: ["$state", function ($state) {
					var currentStateData = {
							name: $state.current.name || 'brand',
							params: $state.params,
							url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		})
		.state('brand-detail.edit', {
			parent: 'brand-detail',
			url: '/detail/edit',
			data: {
				authorities: ['ROLE_USER']
			},

			views: {
				'content@': {
					templateUrl: 'app/entities/brand/brand-dialog.html',
					controller: 'BrandDialogController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('brand');
					$translatePartialLoader.addPart('statusEnum');
					return $translate.refresh();
				}],
				entity: ['$stateParams', 'Brand', function($stateParams, Brand) {
					return Brand.get({id : $stateParams.id}).$promise;
				}],
				previousState: ["$state", function ($state) {
					var currentStateData = {
							name: $state.current.name || 'brand',
							params: $state.params,
							url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			},
		})
		.state('brand.new', {
			parent: 'brand',
			url: '/new',
			data: {
				authorities: ['ROLE_USER']
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/brand/brand-dialog.html',
					controller: 'BrandDialogController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				entity:function(){
					return {
						name: null,
						description: null,
						status: null,
						image: null,
						imageContentType: null,
						id: null
					}
				}
			}
		})
		.state('brand.edit', {
			parent: 'brand',
			url: '/{id}/edit',
			data: {
				authorities: ['ROLE_USER']
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/brand/brand-dialog.html',
					controller: 'BrandDialogController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('brand');
					$translatePartialLoader.addPart('statusEnum');
					return $translate.refresh();
				}],
				entity: ['$stateParams', 'Brand', function($stateParams, Brand) {
					return Brand.get({id : $stateParams.id}).$promise;
				}],
				previousState: ["$state", function ($state) {
					var currentStateData = {
							name: $state.current.name || 'brand',
							params: $state.params,
							url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			},
		})
		.state('brand.delete', {
			parent: 'brand',
			url: '/{id}/delete',
			data: {
				authorities: ['ROLE_USER']
			},
			onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
				$uibModal.open({
					templateUrl: 'app/entities/brand/brand-delete-dialog.html',
					controller: 'BrandDeleteController',
					controllerAs: 'vm',
					size: 'md',
					resolve: {
						entity: ['Brand', function(Brand) {
							return Brand.get({id : $stateParams.id}).$promise;
						}]
					}
				}).result.then(function() {
					$state.go('brand', null, { reload: 'brand' });
				}, function() {
					$state.go('^');
				});
			}]
		});
	}

})();
