package com.smarteshop.admin.catalog.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.smarteshop.domain.catalog.ProductOption;
import com.smarteshop.domain.catalog.ProductOptionValue;

public class CatalogServiceImpl implements CatalogService {
  private static final Log LOGGER= LogFactory.getLog(CatalogServiceImpl.class);

  @Override
  public Integer generateSkusFromProduct(Long productId) {
    return null;
//    Product product = ;//catalogService.findProductById(productId);
//
//    if (CollectionUtils.isEmpty(product.getProductOptions())) {
//      return -1;
//    }
//
//    List<List<ProductOptionValue>> allPermutations = null;//generatePermutations(0, new ArrayList<ProductOptionValue>(), product.getProductOptions());
//
//    // return -2 to indicate that one of the Product Options used in Sku generation has no Allowed Values
//    if (allPermutations == null) {
//      return -2;
//    }
//
//    LOGGER.info("Total number of permutations: " + allPermutations.size());
//    LOGGER.info(allPermutations);
//
//    //determine the permutations that I already have Skus for
//    List<List<ProductOptionValue>> previouslyGeneratedPermutations = new ArrayList<List<ProductOptionValue>>();
//    if (CollectionUtils.isNotEmpty(product.getAdditionalSkus())) {
//      for (Sku additionalSku : product.getAdditionalSkus()) {
//        if (CollectionUtils.isNotEmpty(additionalSku.getProductOptionValues())) {
//          previouslyGeneratedPermutations.add(additionalSku.getProductOptionValues());
//        }
//      }
//    }
//
//    List<List<ProductOptionValue>> permutationsToGenerate = new ArrayList<List<ProductOptionValue>>();
//    for (List<ProductOptionValue> permutation : allPermutations) {
//      boolean previouslyGenerated = false;
//      for (List<ProductOptionValue> generatedPermutation : previouslyGeneratedPermutations) {
//        if (isSamePermutation(permutation, generatedPermutation)) {
//          previouslyGenerated = true;
//          break;
//        }
//      }
//
//      if (!previouslyGenerated) {
//        permutationsToGenerate.add(permutation);
//      }
//    }
//
//    return 0;
  }

  public List<List<ProductOptionValue>> generatePermutations(int currentTypeIndex, List<ProductOptionValue> currentPermutation, List<ProductOption> options) {
    List<List<ProductOptionValue>> result = new ArrayList<List<ProductOptionValue>>();
    if (currentTypeIndex == options.size()) {
      result.add(currentPermutation);
      return result;
    }

    ProductOption currentOption = options.get(currentTypeIndex);
    Set<ProductOptionValue> allowedValues = currentOption.getProductOptionValues();

    // Check to make sure there is at least 1 Allowed Value, else prevent generation
    if (currentOption.getProductOptionValues().isEmpty()) {
      return null;
    }
    for (ProductOptionValue option : allowedValues) {
      List<ProductOptionValue> permutation = new ArrayList<ProductOptionValue>();
      permutation.addAll(currentPermutation);
      permutation.add(option);
      result.addAll(generatePermutations(currentTypeIndex + 1, permutation, options));
    }
    if (allowedValues.size() == 0) {
      // There are still product options left in our array to compute permutations, even though this ProductOption does not have any values associated.
      result.addAll(generatePermutations(currentTypeIndex + 1, currentPermutation, options));
    }

    return result;
  }
  @Override
  public Boolean cloneProduct(Long productId) {
    // TODO Auto-generated method stub
    return null;
  }

  protected boolean isSamePermutation(List<ProductOptionValue> perm1, List<ProductOptionValue> perm2) {
    if (perm1.size() == perm2.size()) {

      Collection<Long> perm1Ids = CollectionUtils.collect(perm1, new Transformer<ProductOptionValue,Long>() {
        @Override
        public Long transform(ProductOptionValue input) {
          return input.getId();
        }
      });

      Collection<Long> perm2Ids = CollectionUtils.collect(perm2, new Transformer<ProductOptionValue,Long>() {
        @Override
        public Long transform(ProductOptionValue input) {
          return input.getId();
        }
      });

      return perm1Ids.containsAll(perm2Ids);
    }
    return false;
  }



}