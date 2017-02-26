package com.smarteshop.test.temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.smarteshop.domain.catalog.ProductOption;
import com.smarteshop.domain.catalog.ProductOptionValue;


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

  public static  ProductOption buildProductOption(){
    // TODO Auto-generated method stub
    ProductOption op= new ProductOption();
    op.setId(1L);
    op.setAttributeName("Size");
    ProductOptionValue opv1 = new ProductOptionValue();
    opv1.setAttributeValue("X");
    opv1.setDisplayOrder(2L);
    ProductOptionValue opv2 = new ProductOptionValue();
    opv2.setAttributeValue("XL");
    opv2.setDisplayOrder(1L);
    ProductOptionValue opv3 = new ProductOptionValue();
    opv3.setAttributeValue("XLL");
    opv3.setDisplayOrder(3L);
    op.addProductOptionValue(opv1);
    op.addProductOptionValue(opv2);
    op.addProductOptionValue(opv3);
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
    ProductOption op = TempTest.buildProductOption();
    List<ProductOptionValue> vv = new ArrayList( op.getProductOptionValues());;
    Collections.sort(vv,new Comparator<ProductOptionValue>(){
      @Override
      public int compare(ProductOptionValue po1, ProductOptionValue po2) {
        return po1.getDisplayOrder().compareTo(po2.getDisplayOrder());
      }
    });
    for(int j=0; j<10000;j++){
      StringBuilder result =new StringBuilder("");
      for(int i=0; i<vv.size(); i++){
        result.append(" ; ").append(vv.get(i).getAttributeValue());
      }
      System.out.println(  result.substring(3));
    }
  }

}
