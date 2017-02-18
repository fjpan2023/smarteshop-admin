package com.smarteshop.test.temp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.smarteshop.domain.ProductOption;
import com.smarteshop.domain.ProductOptionValue;

public class TempTest {

  public static List<ProductOption>  buildContext(){
    // TODO Auto-generated method stub
    List<ProductOption> result = new LinkedList<ProductOption>();
    ProductOption op3= new ProductOption();
    ProductOption op1= buildProductOption();
    ProductOption op2= buildProductOption();

    result.add(op1);
    result.add(op2);
    result.add(op3);
    return result;

  }

  private static  ProductOption buildProductOption(){
    // TODO Auto-generated method stub
    ProductOption op= new ProductOption();
    op.setId(1L);
    op.setAttributeName("Size");
    ProductOptionValue opv1 = new ProductOptionValue();
    opv1.setAttributeValue("X");
    ProductOptionValue opv2 = new ProductOptionValue();
    opv2.setAttributeValue("XL");
    op.addProductOptionValue(opv1);
    op.addProductOptionValue(opv2);
    return op;
  }

  public static List<List<ProductOptionValue>>  generatePermutations(List<ProductOption> options, int currentTypeIndex, List<ProductOptionValue> currentPermutation){

    List<List<ProductOptionValue>> result = new ArrayList<List<ProductOptionValue>>();
    if (currentTypeIndex == options.size()) {
      result.add(currentPermutation);
      return result;
    }

    ProductOption currentOption = options.get(currentTypeIndex);
    Set<ProductOptionValue> allowedValues = currentOption.getProductOptionValues();
    if (CollectionUtils.isEmpty(allowedValues)) {
      result.addAll(generatePermutations(options,currentTypeIndex + 1, currentPermutation));
    }
    for (ProductOptionValue option : allowedValues) {
      List<ProductOptionValue> permutation = new ArrayList<ProductOptionValue>();
      permutation.addAll(currentPermutation);
      permutation.add(option);
      result.addAll(generatePermutations( options,currentTypeIndex + 1,permutation));
    }
    return result;
  }

  public static void main(String[] args) {
    List<ProductOption> context = TempTest.buildContext();
    List<List<ProductOptionValue>> allPermutations = TempTest.generatePermutations(new ArrayList(context),0, new ArrayList<ProductOptionValue>() );

    System.out.println(allPermutations.size());


  }

}
